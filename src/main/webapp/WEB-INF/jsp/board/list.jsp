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
    </style>
</head>
<body>
<table>
    <thead>
    <tr>
        <th class="one wide">번호</th>
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
                <a href='<c:out value="${board.bid}"/>'>
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
</body>
</html>
