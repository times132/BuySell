<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>

</head>
<body>
<h2><c:out value="${boardDto.title}"></c:out></h2>
<p>분류 : ${boardDto.btype}</p>
<p>작성일 : <javatime:format pattern="yyyy-MM-dd" value="${boardDto.createdDate}"/></p>

<p>판매자 : ${boardDto.writer}</p>
<p>내용 : ${boardDto.content}</p>
<p>가격 : ${boardDto.price}</p>

<div>
    <a href='<c:url value="/board/edit/${boardDto.bid}"/>'>
        <button data-oper="modifyBtn">수정</button>
    </a>

</div>
</body>
</html>
