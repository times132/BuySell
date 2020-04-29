<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>게시판</title>

    <link rel="stylesheet" href="/resources/css/board.css">
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    
    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
</head>
<body>
    <%@include file="../include/navbar.jsp"%>

    <div class="board-body">
        <div class="row">
            <div class="panel-group">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h3 class="panel-title">Panel Title</h3>
                    </div>

                    <ul class="list-group">
                        <li class="list-group-item"><a href="#">기프티콘</a></li>
                        <li class="list-group-item"><a href="#">디지털/가전</a></li>
                        <li class="list-group-item"><a href="#">등등등</a></li>
                   </ul>
                </div>
            </div>
            <div class="col-lg-10 col-md-9">
                <a href="/board">전체보기</a>

                <table class="table table-sm table-hover">
                    <thead>
                        <tr>
                            <th style="width: 6%" scope="col">#</th>
                            <th style="width: 12%" scope="col">분류</th>
                            <th style="width: 44%" scope="col">글제목</th>
                            <th style="width: 10%" scope="col">가격</th>
                            <th style="width: 10%" scope="col">작성자</th>
                            <th style="width: 8%" scope="col">작성일</th>
                            <th style="width: 5%" scope="col">조회</th>
                            <th style="width: 5%" scope="col">추천</th>
                        </tr>
                    </thead>

                    <tbody>
                    <!-- CONTENTS !-->
                    <c:forEach items="${boardList}" var="board">
                        <tr>
                            <th>
                                <c:out value="${board.bid}"/>
                            </th>

                            <td class="btype">
                                <c:out value="${board.category}"/>
                            </td>

                            <c:if test="${board.sellCheck eq true}">
                                <td class="title-sold">
                                    <a class="move" href='<c:out value="${board.bid}"/>'>
                                        <c:out value="${board.title}"/>
                                    </a>
                                    <span class="sold-reply-cnt">
                                        [<c:out value="${board.replyCnt}"/>]
                                    </span>
                                    <span class="sold">
                                        완료
                                    </span>
                                </td>
                            </c:if>
                            <c:if test="${board.sellCheck eq false}">
                                <td class="title">
                                    <a class="move" href='<c:out value="${board.bid}"/>'>
                                        <c:out value="${board.title}"/>
                                    </a>
                                    <span class="reply-cnt">
                                        [<c:out value="${board.replyCnt}"/>]
                                    </span>
                                </td>
                            </c:if>

                            <td class="price">
                                <fmt:formatNumber value="${board.price}" type="currency"/>
                            </td>

                            <td class="writer">
                                <c:out value="${board.writer}"/>
                            </td>

                            <td class="time">
                                <javatime:format pattern="yy.MM.dd" value="${board.createdDate}"/>
                            </td>

                            <td class="view-cnt">
                                <c:out value="${board.viewCnt}"/>
                            </td>

                            <td class="like-cnt">
                                <c:out value="${board.likeCnt}"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <sec:authorize access="isAuthenticated()">
                    <a class="write-btn" href="/board/write"><img src="/resources/image/pencil.png"/> 글쓰기</a>
                </sec:authorize>

                <div class="list-footer">
                    <%-- 페이징 --%>
                    <ul class="pagination pagination-sm justify-content-center">
                        <c:if test="${pageMaker.prev}">
                            <li class="page-item"><a class="prev" href="${pageMaker.startPage - 1}">이전</a></li>
                        </c:if>
                        <c:forEach var="page" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                            <li class="page-item ${pageMaker.cri.page == page ? "active" : ""}"><a class="page" href="${page}">${page}</a></li>
                        </c:forEach>
                        <c:if test="${pageMaker.next}">
                            <li class="page-item"><a class="next" href="${pageMaker.endPage + 1}">다음</a></li>
                        </c:if>
                    </ul>


                </div>

                <%--검색 폼--%>
                <form id="searchForm" action="/board" method="get">
                    <div class="row justify-content-center">
                        <div class="input-group input-group-sm mb-1 col-lg-8">
                            <div class="input-group-append">
                                <select name="type" class="form-control form-control-sm" id="exampleFormControlSelect1">
                                    <option value=""<c:out value="${pageMaker.cri.type == null ? 'selected' : ''}"/>>--</option>
                                    <option value="TC"<c:out value="${pageMaker.cri.type eq 'TC' ? 'selected' : ''}"/>>제목+내용</option>
                                    <option value="W"<c:out value="${pageMaker.cri.type eq 'W' ? 'selected' : ''}"/>>작성자</option>
                                </select>
                            </div>

                            <input name="keyword" type="text" class="form-control" placeholder="검색어를 입력해주세요" value="<c:out value="${pageMaker.cri.keyword}"/>">
                            <div class="input-group-append">
                                <button class="btn btn-outline-secondary" type="button" id="button-addon2">검색</button>
                            </div>
                        </div>
                    </div>

                    <input type="hidden" name="page" value="<c:out value="${pageMaker.cri.page}"/>"/>
                </form>



                <form id="actionForm" action="/board" method="get">
                    <input type="hidden" name="page" value="${pageMaker.cri.page}">
                    <input type="hidden" name="type" value="${pageMaker.cri.type}">
                    <input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">
                </form>
            </div>
        </div>
    </div>

    <!-- js & jquery -->
    <script type="text/javascript" src="/resources/js/common.js"></script>
    <script>
        $(document).ready(function () {
            var actionForm = $("#actionForm");

            // 세부 페이지 이동
            $(".move").on("click", function (e) {
                e.preventDefault();
                actionForm.append("<input type='hidden' name='bid' value='"+$(this).attr("href")+"'>");
                actionForm.attr("action", "/board/read");
                actionForm.submit();
            });

            // 페이징 이동
            $(".page-item a").on("click", function (e) {
                e.preventDefault();
                console.log("click");
                actionForm.find("input[name='page']").val($(this).attr("href"));
                actionForm.submit();
            });


            $("#searchForm button").on("click", function (e) {
                return searchCheck();
            });

            $("input[name='keyword']").keydown(function (key) {
                if(key.keyCode == 13){
                    return searchCheck();
                }
            })
        });

        function searchCheck() {
            var searchForm = $("#searchForm");
            if (!searchForm.find("option:selected").val()){
                alert("검색종류를 선택하세요.");
                return false;
            }
            if (!searchForm.find("input[name='keyword']").val()){
                alert("키워드를 입력하세요.");
                return false;
            }
            searchForm.find("input[name='page']").val("1");
            searchForm.submit();
        }
    </script>
</body>
</html>