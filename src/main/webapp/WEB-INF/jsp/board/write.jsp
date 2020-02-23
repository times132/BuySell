<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form  action="/board/write" method="post">
    <sec:authentication property="principal" var="userinfo"/>
    분류 : <input type="text" name="btype"> <br>
    제목 : <input type="text" name="title"> <br>
    내용 : <input type="text" name="content"> <br>
    작성자 : <input type="text" name="writer" value="${userinfo.nickname}" readonly="readonly"> <br>
    가격 : <input type="text" name="price"> <br>

    <input type="submit" value="등록">
</form>
</body>
</html>
