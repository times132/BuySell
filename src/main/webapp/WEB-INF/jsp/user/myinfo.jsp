<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>
<head>
    <title>내정보</title>

</head>
<body>

<h1>나의 정보</h1>
    <sec:authentication property="principal" var="userinfo"/>
    <label>이메일</label> <input type="text" name="email" value="${userinfo.email}" readonly="readonly"><br>

    <label>비밀번호</label> <input type="text" name="password" value="${userinfo.password}" readonly="readonly"><br>

    <label>핸드폰번호</label> <input type="text" name="phone" value="${userinfo.phone}" readonly="readonly"><br>

    <label>닉네임</label> <input type="text" name="nickname" value="${userinfo.nickname}" readonly="readonly"><br>

    <label>이름</label> <input type="text" name="username" value="${userinfo.username}" readonly="readonly"><br>

    <input type="button" value="회원정보수정" onClick="self.location='/user/modifyuser';">
    <input type="button" value="회원탈퇴" onClick="self.location='/user/password';">
    <input type="button" value="홈으로 이동" onClick="self.location='/';">
    <input type="hidden" name="id" value="${userinfo.id}">

</form>
</body>
</html>
