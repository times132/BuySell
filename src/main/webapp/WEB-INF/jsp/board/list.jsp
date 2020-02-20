<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <style>
        table {
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
        }
        .pagination {
            list-style:none;
            margin:0;
            padding:0;
        }
        .pagination li {
            margin: 0 5px 0 0;
            padding: 0 0 0 0;
            border : 0;
            float: left;
        }
    </style>
    
    <script src="/webjars/jquery/dist/jquery.min.js"></script>
</head>
<body>
    <%@include file="../include/header.jsp"%>
    <a href="/board/write">글쓰기</a>

    <a href="/board">전체보기</a>

    <table>
        <thead>
        <tr>
            <th class="one wide">번호</th>
            <th class="one wide">분류</th>
            <th class="ten wide">글제목</th>
            <th class="two wide">작성자</th>
            <th class="three wide">작성일</th>
        </tr>
        </thead>

        <tbody>
        <!-- CONTENTS !-->
        <c:forEach items="${boardList}" var="board">
            <tr>
                <td>
                    <c:out value="${board.bid}"></c:out>
                </td>
                <td>
                    <c:out value="${board.btype}"></c:out>
                </td>
                <td>
                    <a class="move" href='<c:out value="${board.bid}"/>'>
                        <c:out value="${board.title}"></c:out>
                    </a>
                </td>
                <td>
                    <c:out value="${board.writer}"></c:out>
                </td>
                <td>
                    <javatime:format pattern="yyyy-MM-dd" value="${board.createdDate}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <%--검색 폼--%>
    <form id="searchForm" action="/board" method="get">
        <select name="type">
            <option value=""<c:out value="${pageMaker.cri.type == null ? 'selected' : ''}"/>>--</option>
            <option value="W">작성자</option>
            <option value="TC">제목+내용</option>
        </select>

        <input name="keyword" type="text" placeholder="검색"/>

        <input type="hidden" name="page" value="<c:out value="${pageMaker.cri.page}"/>"/>

        <button>검색하기</button>
    </form>
    <ul class="pagination">
        <c:if test="${pageMaker.prev}">
            <li class="pagination_btn"><a class="prev" href="${pageMaker.startPage - 1}">이전</a></li>
        </c:if>
        <c:forEach var="page" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
            <li class="pagination_btn "><a class="page" href="${page}">${page}</a></li>
        </c:forEach>
        <c:if test="${pageMaker.next}">
            <li class="pagination_btn"><a class="next" href="${pageMaker.endPage + 1}">다음</a></li>
        </c:if>
    </ul>

    <form id="actionForm" action="/board" method="get">
        <input type="hidden" name="page" value="${pageMaker.cri.page}">
        <input type="hidden" name="type" value="${pageMaker.cri.type}">
        <input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">
    </form>

    <script>
        $(document).ready(function () {

            $(".move").on("click", function (e) {
                e.preventDefault();
                actionForm.append("<input type='hidden' name='bid' value='"+$(this).attr("href")+"'>");
                actionForm.attr("action", "/board/read");
                actionForm.submit();
            });

            var actionForm = $("#actionForm");
            $(".pagination_btn a").on("click", function (e) {
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
            })
        })
    </script>
</body>
</html>