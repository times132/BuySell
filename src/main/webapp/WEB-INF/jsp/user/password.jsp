<%@ page language="java" contentType="text/html; charset=EUC-KR"
         pageEncoding="EUC-KR"%>

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <title>비밀번호 확인창</title>

</head>
<body>
<h1>비밀번호 확인</h1>
<hr>
<form action="/user/password" method="post">

    <div>
        비밀번호 <input type="password" name="password" value="password">
    </div>



    <button type="submit">확인</button>
</form>
</body>
