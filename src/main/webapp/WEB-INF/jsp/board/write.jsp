<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form  action="/board/write" method="post">

    분류 : <input type="text" name="btype"> <br>
    제목 : <input type="text" name="title"> <br>
    내용 : <input type="text" name="content"> <br>
    작성자 : <input type="text" name="writer" value="${userList.nickname}" readonly="readonly"> <br>
    가격 : <input type="text" name="price"> <br>

    <input type="submit" value="등록">
</form>
</body>
</html>
