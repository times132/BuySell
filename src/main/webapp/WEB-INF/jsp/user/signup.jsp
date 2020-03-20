<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css"  id="bootstrap-css">
<script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/user.js"></script>
<script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
<script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.min.js"></script>
<link href="/resources/css/signup.css" rel="stylesheet">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>회원가입 페이지</title>

</head>
<body>

<div class="container">

    <div class="card bg-light">
        <article class="card-body mx-auto" style="max-width: 400px;">
            <h4 class="card-title mt-3 text-center">Create Account</h4>
            <p>
                <a href="#" class="btn btn-block btn-twitter"> <i class="fab fa-twitter"></i>   Login via Twitter</a>
                <a href="#" class="btn btn-block btn-facebook"> <i class="fab fa-facebook-f"></i>   Login via facebook</a>
            </p>
            <p class="divider-text">
                <span class="bg-light">OR</span>
            </p>
            <form  action="/user/signup" method="post">
                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                    </div>
                    <input name="name" class="form-control" placeholder="FULL NAME" type="text" value="${name}" required/>
                </div> <!-- form-group// -->
                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-envelope"></i> </span>
                    </div>
                    <input name="email"  class="form-control" id="email" placeholder="EMAIL ADDRESS" value="${email}" type="email" required/>
                </div> <!-- form-group// -->
                <div class="alert alert-danger" id="email_check">이미 사용중인 이메일 입니다.</div>
                <p>${valid_email}</p>
                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-phone"></i> </span>
                    </div>
                    <select class="custom-select" style="max-width: 120px;">
                        <option selected="">---</option>
                        <option value="1">+010</option>
                        <option value="2">*</option>
                    </select>
                    <input name=phone class="form-control" placeholder="PHONE NUMBER" type="text">
                </div> <!-- form-group// -->
                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fas fa-address-card"></i> </span>
                    </div>
                    <input type="text" class="form-control" id="username" name="username" placeholder="NICKNAME" required/>
                </div> <!-- form-group end.// -->
                <div class="alert alert-danger" id="username_check">이미 사용중인 닉네임입니다.</div><p>${valid_username}</p><br>
                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                    </div>
                    <input type="password" name="password" id="pwd1" class="form-control" placeholder="PASSWORD"required />

                </div> <!-- form-group// -->
                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                    </div>
                    <input type="password" name="reuserPwd" id="pwd2" class="form-control" placeholder="CONFIRM PASSWORD" required />​
                </div> <!-- form-group// -->
                <div class="alert alert-success" id="alert-success">비밀번호가 일치합니다.</div>
                <div class="alert alert-danger" id="alert-danger">비밀번호확인이 필요합니다. 비밀번호가 일치하지 않습니다.</div>
                <p>${valid_password}</p>

            <div class="form-group">
                    <button type="submit" name="submit" id="submit" class="btn btn-primary btn-block"> Create Account</button>
                </div> <!-- form-group// -->
                <p class="text-center">Have an account? <a href="/user/login">Log In</a> </p>
            </form>
        </article>
    </div> <!-- card.// -->

</div>
<!--container end.//-->
<script>
    // 아이디 유효성 검사(1 = 중복 / 0 != 중복)
    idck1 = 0;
    $("#submit")
        .attr("disabled", true);
    // 아이디 유효성 검사(1 = 중복 / 0 != 중복)
    idck1 = 0;
    $("#email").keyup(function() {
        var email = $("#email").val();

        userService.checkEmail(email, function (data) {
            if (data == 1) {
                $("#email_check").show();
                $("#submit").attr("disabled", true);
                idck1=0;
            } else {
                $("#email_check").hide();
                idck1 = 1;
            }
            if(idck==1 && idck1==1){
                $("#submit")
                    .removeAttr("disabled");
            }
        });
    });
    $("#email_check").hide();
    $("#username_check").hide();
    // 아이디 유효성 검사(1 = 중복 / 0 != 중복)
    idck = 0;
    $("#username").keyup(function() {
        var username = $("#username").val();
        $("#username_check").hide();
        userService.checkUsername(username, function (data) {
            if (data == 1) {
                $("#username_check").show();
                $("#submit").attr("disabled", true);
                idck=0;
            }
            else {
                $("#username_check").hide();
                idck=1;
            }
            if(idck==1 && idck1==1){
                $("#submit")
                    .removeAttr("disabled");
            }
        });
    });

    $(function(){
        $("#alert-success").hide();
        $("#alert-danger").hide();
        $("input").keyup(function(){ var pwd1=$("#pwd1").val();
        var pwd2=$("#pwd2").val();
            if(pwd1 != "" || pwd2 != ""){ if(pwd1 == pwd2){
                $("#alert-success").show();
                $("#alert-danger").hide();
                $("#submit").removeAttr("disabled"); }
            else{ $("#alert-success").hide(); $("#alert-danger").show();
                $("#submit").attr("disabled", "disabled");
            }
            }
        });
    });

</script>

</body>
</html>
