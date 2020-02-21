<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>

<html>
<head>
    <title>내정보</title>

</head>
<body>
<form  action="/user/modifyuser" method="post">
    이메일 : <input type="text" name="email" value="${userList.email}" readonly="readonly"></p><br>

    패스워드   : <input type="text" name="password" value="${userList.password}"/><p>${valid_password}</p><br>

    닉네임     : <input type="text" name="nickname" value="${userList.nickname}"><p> ${valid_nickname}</p> <br>

    이름       : <input type="text" name="username" value="${userList.username}"> <br>

    핸드폰번호  : <input type="text" name="phone" value="${userList.phone}" ><p> ${valid_phone}</p> <br>


<input type="hidden" name="id" value="${userList.id}">

    <div>
        <button type="submit">수정하기</button>

    </div>

</form>
</body>
</html>
