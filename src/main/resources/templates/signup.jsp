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
<form th:action="@{/user/signup}" method="post">
    <input type="text" name="email" placeholder="이메일 입력해주세요">
    <input type="password" name="password" placeholder="비밀번호">
    <input type="text" name="nickname" placeholder="사용할 닉네임을 입력하여 주세요">
    <input type="text" name="username" placeholder="이름을 입력하여 주세요">
    <input type="text" name="phone" placeholder="핸드폰번호를 입력하여 주세요">

    <button type="submit">가입하기</button>
</form>
</body>
</html>