<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml" xmlns:sec="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>메인</title>
</head>
<body>
<h1>WELCOM TO GIVEANDTAKE</h1>
<hr>
<input type="button" value="로그인" onClick="self.location='/user/login';">
<input type="button" value="게시판" onClick="self.location='/board';">
<input type="button" value="회원가입" onClick="self.location='/user/signup';">
<input type="button" value="내정보" onClick="self.location='/user/info';">
<input type="button" value="어드민" onClick="self.location='/admin';">

</body>
</html>