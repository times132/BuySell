
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml" xmlns:sec="http://www.w3.org/1999/xhtml">
<head>
    <title>WELCOM TO GIVEANDTAKE</title>
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
<h1>WELCOM TO GIVEANDTAKE</h1>
<hr>
<a sec:authorize="isAnonymous()" th:href="@{/user/login}"> <button type="submit">로그인</button></a>
<a sec:authorize="isAuthenticated()" th:href="@{/user/logout}"><button type="submit">로그아웃</button></a>
<a sec:authorize="isAnonymous()" th:href="@{/user/signup}"><button type="submit">회원가입</button></a>
<a sec:authorize="hasRole('ROLE_MEMBER')" th:href="@{/user/info}"><button type="submit">내정보</button></a>
<a sec:authorize="hasRole('ROLE_ADMIN')" th:href="@{/admin}"><button type="submit">관리자</button></a>
</body>

