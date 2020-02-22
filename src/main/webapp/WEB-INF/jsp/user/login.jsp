<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <title>LOGIN</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/login.css">


</head>
<body>
<h1>로그인</h1>
<hr>
<form action="/user/login" method="post">
    <div>
        이메일 <input type="text" name="username" placeholder="email">
    </div>
    <div>
        비밀번호 <input type="password" name="password">
    </div>


    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

    <button type="submit">로그인</button>
</form>
</body>
</html>