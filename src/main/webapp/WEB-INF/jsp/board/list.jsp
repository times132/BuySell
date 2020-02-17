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
</head>
<body>
<a href="/board/write">글쓰기</a>
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
                <a href='<c:out value="read/${board.bid}"/>'>
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
<form action="/board/search" method="get">
    <select name="type">
        <option value=""/>>--</option>
        <option value="W">작성자</option>
        <option value="TC">제목+내용</option>
    </select>

    <input name="keyword" type="text" placeholder="검색"/>

    <input type="hidden" name="page" value="<c:out value="${pageMaker.curPage}"/>"/>

    <button>검색하기</button>
</form>
    <ul class="pagination">
        <c:if test="${pageMaker.prev}">
            <li class="pagination_btn" <a class="prev" href="?page=${pageMaker.startPage - 1}">이전</a></li>
        </c:if>
        <c:forEach var="page" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
            <li class="pagination_btn"><a class="page" href="?page=${page}">${page}</a></li>
        </c:forEach>
        <c:if test="${pageMaker.next}">
            <li class="pagination_btn"><a class="next" href="?page=${pageMaker.endPage + 1}">다음</a></li>
        </c:if>
    </ul>

<%--    <form id="actionForm" action="/board/list" method="get">--%>
<%--        <input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">--%>
<%--        <input type="hidden" name="amount" value="${pageMaker.cri.amount}">--%>
<%--        <input type="hidden" name="type" value="${pageMaker.cri.type}">--%>
<%--        <input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">--%>
<%--    </form>--%>

<script>
    $(document).ready(function () {


        $(".pagination_button a").on("click", function (e) {
            e.preventDefault();
            console.log("click");
            actionForm.find("input[name='pageNum']").val($(this).attr("href"));
            actionForm.submit();
        });
    })
</script>
</body>
</html>
