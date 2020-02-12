<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form  action="/board/edit/${boardDto.bid}" method="post">
    <input type="hidden" name="bid" value="${boardDto.bid}"/>

    분류 : <input type="text" name="btype" value="${boardDto.btype}" readonly="readonly"> <br>
    제목 : <input type="text" name="title" value="${boardDto.title}"> <br>
    내용 : <input type="text" name="content" value="${boardDto.content}"> <br>
    작성자 : <input type="text" name="writer" value="${boardDto.writer}" readonly="readonly"> <br>
    가격 : <input type="text" name="price" value="${boardDto.price}"> <br>

    <input type="submit" value="등록">
</form>
</body>
</html>