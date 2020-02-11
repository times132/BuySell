<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <title>LOGIN</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/login.css">

    <!-- 링크 색상 없애기 -->
    <style type="text/css">
        a:link { color: black; text-decoration: none;}
        a:visited { color: black; text-decoration: none;}
        a:hover { color: black; text-decoration: underline;}
    </style>

    <!-- 구글 웹 폰트 -->
    <link href="https://fonts.googleapis.com/css?family=Nanum+Brush+Script" rel="stylesheet">
    <style>
        body
        {
            font-family: 'Nanum Brush Script', cursive;
        }
    </style>
    <!-- 웹 폰트 끝 -->
</head>
<body>
<h1>로그인</h1>
<hr>

<form action="/user/login" method="post">
    <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />

    <input type="text" name="username" placeholder="이메일 입력해주세요">
    <input type="password" name="password" placeholder="비밀번호">
    <button type="submit">로그인</button>
</form>
</body>
</html>