<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <link href="/resources/css/board.css" rel="stylesheet">
    
    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
    <script type="text/javascript" src="/resources/js/common.js"></script>
</head>
<body>
    <%@include file="../include/header.jsp"%>
    <%@include file="../include/search.jsp"%>
    <div class="container">
        <div class="row">
            <div class="col-lg-3">
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
            <div class="col-lg-9">
                <sec:authorize access="isAuthenticated()">
                    <a href="/board/write">글쓰기</a>
                </sec:authorize>

                <a href="/board">전체보기</a>


                <%--검색 폼--%>
                <form id="searchForm" action="/board" method="get">
                    <div class="row justify-content-md-right">
                        <div class="input-group input-group-sm mb-1 col-lg-5">
                            <div class="input-group-append">
                                <select name="type" class="form-control form-control-sm" id="exampleFormControlSelect1">
                                    <option value=""<c:out value="${pageMaker.cri.type == null ? 'selected' : ''}"/>>--</option>
                                    <option value="TC">제목+내용</option>
                                    <option value="W">작성자</option>
                                </select>
                            </div>
                            <input hidden="hidden" /> <%--엔터키 방지--%>
                            <input name="keyword" type="text" class="form-control" placeholder="검색어를 입력해주세요" aria-label="Recipient's username" aria-describedby="button-addon2">
                            <div class="input-group-append">
                                <button class="btn btn-outline-secondary" type="button" id="button-addon2">검색</button>
                            </div>
                        </div>
                    </div>

                    <input type="hidden" name="page" value="<c:out value="${pageMaker.cri.page}"/>"/>
                </form>

                <table class="table table-responsive table-sm table-hover">
                    <thead>
                        <tr>
                            <th style="width: 6%" scope="col">#</th>
                            <th style="width: 12%" scope="col">분류</th>
                            <th style="width: 45%" scope="col">글제목</th>
                            <th style="width: 10%" scope="col">가격</th>
                            <th style="width: 10%" scope="col">작성자</th>
                            <th style="width: 10%" scope="col">작성일</th>
                            <th style="width: 10%" scope="col">조회</th>
                        </tr>
                    </thead>

                    <tbody>
                    <!-- CONTENTS !-->
                    <c:forEach items="${boardList}" var="board">
                        <tr>
                            <th scope="row">
                                <c:out value="${board.bid}"/>
                            </th>
                            <td class="btype">
                                <c:out value="${board.btype}"/>
                            </td>
                            <td class="title">
                                <a class="move" href='<c:out value="${board.bid}"/>'>
                                    <c:out value="${board.title}"/>
                                </a>
                                <a>
                                    [<c:out value="${board.replyCnt}"/>]
                                </a>
                            </td>
                            <td class="price">
                                <fmt:formatNumber value="${board.price}" type="currency"/>
                            </td>
                            <td class="writer">
                                <c:out value="${board.user.nickname}"/>
                            </td>
                            <td class="time">
                                <javatime:format pattern="yy.MM.dd" value="${board.createdDate}"/>
                            </td>
                            <td class="viewCnt">
                                <c:out value="${board.viewCnt}"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div class="reply-footer">
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





                <form id="actionForm" action="/board" method="get">
                    <input type="hidden" name="page" value="${pageMaker.cri.page}">
                    <input type="hidden" name="type" value="${pageMaker.cri.type}">
                    <input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">
                </form>
            </div>
        </div>
    </div>

    <script>
        $(document).ready(function () {
            $(".move").on("click", function (e) {
                e.preventDefault();
                actionForm.append("<input type='hidden' name='bid' value='"+$(this).attr("href")+"'>");
                actionForm.attr("action", "/board/read");
                actionForm.submit();
            });

            var actionForm = $("#actionForm");
            $(".page-item a").on("click", function (e) {
                e.preventDefault();
                console.log("click");
                actionForm.find("input[name='page']").val($(this).attr("href"));
                actionForm.submit();
            });

            var searchForm = $("#searchForm");
            $("#searchForm button").on("click", function (e) {
                if (!searchForm.find("option:selected").val()){
                    alert("검색종류를 선택하세요.");
                    return false;
                }
                if (!searchForm.find("input[name='keyword']").val()){
                    alert("키워드를 입력하세요.");
                    return false;
                }
                searchForm.find("input[name='page']").val("1");
                e.preventDefault();
                searchForm.submit();
            });


        });
    </script>
</body>
</html>