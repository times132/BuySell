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
                <a href='<c:out value="/board/${board.bid}"/>'>
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
    <div>
        <input name="keyword" type="text" placeholder="검색"/>
    </div>

    <button>검색하기</button>
</form>
    <ul class="pagination">
        <c:if test="${pageMaker.prev}">
            <li><a class="prev" href="?page=${pageMaker.startPage - 1}">이전</a></li>
        </c:if>
        <c:forEach var="page" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
            <li><a class="page" href="?page=${page}">${page}</a></li>
        </c:forEach>
        <c:if test="${pageMaker.next}">
            <li><a class="next" href="?page=${pageMaker.endPage + 1}">다음</a></li>
        </c:if>
    </ul>

<div>

</div>
</body>
</html>
