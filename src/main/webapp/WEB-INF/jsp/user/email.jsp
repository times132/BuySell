<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
<h1> 이메일이 발송되었으니 인증코드를 확인해주세요. </h1>
<h1> 만약이메일이 오지 않았다면 회원가입을 다시 시도해 주세요.

</h1><form action="join_injeung${code}" method="post">


    <div>
        인증번호 입력 : <input type="text" name="email_injeung" placeholder="  인증번호를 입력하세요. ">
    </div>
    <br> <br>
    <div>
        <button type="submit">인증코드입력하기</button>

    </div>

<hr>
<input type="button" value="홈으로 이동" onClick="self.location='/';">
</form>
</body>
</html>