<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
         pageEncoding="EUC-KR"%>
<html>
<head>
    <title>내정보</title>

</head>
<body>

<h1>나의 정보</h1>

    <label>이메일</label> <input type="text" name="email" value="${userList.email }"><br>

    <label>비밀번호</label> <input type="text" name="password" value="${userList.password }"><br>

    <label>핸드폰번호</label> <input type="text" name="phone" value="${userList.phone}"><br>

    <label>닉네임</label> <input type="text" name="nickname" value="${userList.nickname}"><br>

    <label>이름</label> <input type="text" name="nickname" value="${userList.username}"><br>

    <input type="submit" value="수정">

    <input type="hidden" name="id" value="${user.id }">

</form>
</body>
</html>
