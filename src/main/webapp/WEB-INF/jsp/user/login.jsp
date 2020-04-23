<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <link href="/resources/css/login.css" rel="stylesheet">

    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>


    <!--Fontawesome CDN-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

</head>
<body>
<%@include file="../include/header.jsp"%>
<div class="container">
    <div class="d-flex justify-content-center h-100">
        <div class="card">
            <div class="card-header">
                <h3>LOGIN</h3>
                <div class="d-flex justify-content-end social_icon">
                    <span><a href="/oauth2/authorization/google"><img src="/resources/image/google.png"/></a></span>
                    <span><a href="/oauth2/authorization/kakao"><img src="/resources/image/kakao.png"/></a></span>
                </div>
            </div>
            <div class="card-body">
                <form action="/user/login" method="post">
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-user"></i></span>
                        </div>
                        <input type="text" name="username" class="form-control" placeholder="ID">

                    </div>
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-key"></i></span>
                        </div>
                        <input type="password" name="password" class="form-control" placeholder="PASSWORD">
                    </div>
                    <div class="row align-items-center remember">
                        <input type="checkbox">Remember
                    </div>
                    <div class="form-group">
                        <input type="submit" value="LOGIN" class="btn float-right" id="login_btn">
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                </form>
            </div>
            <div class="card-footer">
                <div class="d-flex justify-content-center links">
                    계정이 없으십니까?<a href="/user/signup">회원가입</a>
                </div>
                <div class="d-flex justify-content-center">
                    <a href="/user/findPW">비밀번호를 잊어버리셨습니까?</a>
                </div>
            </div>
        </div>
    </div>
</div>
<script>



</script>
</body>
</html>