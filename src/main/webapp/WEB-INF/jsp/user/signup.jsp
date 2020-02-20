<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>회원가입 페이지</title>
</head>
<body>
<h1>회원 가입</h1>
<hr>

<form  action="/user/signup" method="post">
    이메일 : <input type="text" name="email" value="${userDto.email}"/><p>${valid_email}</p><br>

    패스워드   : <input type="text" name="password" value="${userDto.password}"/><p>${valid_password}</p><br>

    닉네임     : <input type="text" name="nickname" value="${userDto.nickname}"><p> ${valid_nickname}</p> <br>

    이름       : <input type="text" name="username" value="${userDto.username}"> <br>

    핸드폰번호  : <input type="text" name="phone" value="${userDto.phone}" ><p> ${valid_phone}</p> <br>


    <div>
            <button type="submit">가입하기</button>

    </div>

</form>
</body>
</html>