<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<link href="/resources/css/header.css" rel="stylesheet">

<div id="navbar" class="container-fluid mb-3">
    <div class="row">
        <div class="col-sm-12">
            <ul class="navbar float-right">
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