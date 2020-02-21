<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
         pageEncoding="EUC-KR"%>
<html>
<head>
    <title>내정보</title>

</head>
<body>

<h1>나의 정보</h1>

    <label>이메일</label> <input type="text" name="email" value="${userList.email }" readonly="readonly"><br>

    <label>비밀번호</label> <input type="text" name="password" value="${userList.password }" readonly="readonly"><br>

    <label>핸드폰번호</label> <input type="text" name="phone" value="${userList.phone}" readonly="readonly"><br>

    <label>닉네임</label> <input type="text" name="nickname" value="${userList.nickname}" readonly="readonly"><br>

    <label>이름</label> <input type="text" name="nickname" value="${userList.username}" readonly="readonly"><br>

<input type="button" value="회원정보수정" onClick="self.location='/user/modifyuser';">
<input type="button" value="회원탈퇴" onClick="self.location='/user/password';">

    <input type="hidden" name="id" value="${userList.id}">

</form>
</body>
</html>
