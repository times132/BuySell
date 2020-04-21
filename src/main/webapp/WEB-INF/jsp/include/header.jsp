<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chatting room</title>
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <link href="/resources/css/header.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" type="text/css" rel="stylesheet">
    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
    <script type="text/javascript" src="/resources/js/chat.js"></script>

    <script>
        $(document).ready(function () {
            var token =  '${_csrf.token}';
            var header = '${_csrf.headerName}';

            $.ajaxSetup({
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                }
            });
        });
    </script>
</head>
<body>

<div id="navbar" class="container-fluid">
        <div class="row">
            <div class="col-sm-12">
                <ul class="navbar pull-right">
                    <li class="upper-links"><a class="links" href="/">HOME</a></li>
                    <li class="upper-links"><a class="links" href="/board">게시판</a></li>
                    <sec:authorize access="isAnonymous()">
                        <li class="upper-links"><a class="links" href="/user/login">로그인</a></li>
                        <li class="upper-links"><a class="links" href="/user/signup">회원가입</a></li>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">

                        <sec:authentication property="principal.user" var="userinfo"/>
                        <li class="upper-links"><a class="links" href="/chat/room">채팅</a></li>
                        <li class="upper-links" id="logout"><a  class="links" href="/user/logout">로그아웃</a></li>
                        <li class="upper-links dropdown"><a class="links" href="#"><c:out value="${userinfo.nickname}"/>님.</a>
                            <ul class="dropdown-menu">
                                <li class="profile-li"><a class="profile-links" href="/user">내정보</a></li>
                                <li class="profile-li"><a class="profile-links" href="/user/${userinfo.id}/boards">내 게시물</a></li>
                                <li class="profile-li"><a class="profile-links" href="/user/${userinfo.id}/likes">내 관심 목록</a></li>
                                <li class="profile-li"><a class="profile-links" href="/admin">어드민</a></li>
                            </ul>
                        </li>
                    </sec:authorize>
                </ul>
            </div>
        </div>
</div>
<script>
    $("#logout").click(function() {
        location.href="/user/logout";
        alert("로그아웃이 완료되었습니다.")
    });

    var search = $("#search");
    $("#search button").on("click", function (e) {
        if (!search.find("input[name='keyword']").val()){
            alert("키워드를 입력하세요.");
            return false;
        }
        e.preventDefault();
        search.submit();
    });
</script>
</body>