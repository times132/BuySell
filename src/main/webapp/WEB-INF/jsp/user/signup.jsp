<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css" id="bootstrap-css">
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
    <script type="text/javascript" src="/resources/js/mail.js"></script>
</head>
<body>
<br>
<div class="container">
    <div class="card bg-light">
        <article class="card-body mx-auto" style= "width: 400px;">
            <p class="social">
                <a href="/oauth2/authorization/google" class="btn btn-block btn-google"> <i class="fab fa-google"></i>&ensp;Login via Google</a>
                <a href="/oauth2/authorization/kakao" class="btn btn-block btn-kakao"> <i class="fab fa-kickstarter-k"></i>&ensp;Login via KAKAO</a>
            </p>
            <p class="divider-text">
                <span class="bg-light">OR</span>
            </p>
            <form action="/user/signup" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fas fa-address-card"></i> </span>
                    </div>
                    <input type="text" class="form-control" id="username" name="username" placeholder="ID" required/>
                </div> <!-- form-group end.// -->
                <div class="alert alert-danger" id="username-check">이미 사용중인 ID입니다.</div><p>${valid_username}</p><br>
                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                    </div>
                    <input type="password" name="password" id="password" class="form-control" placeholder="PASSWORD"required />

                </div> <!-- form-group// -->
                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                    </div>
                    <input type="password"  id="confirmPW" class="form-control" placeholder="CONFIRM PASSWORD" required />​
                </div> <!-- form-group// -->
                <div class="alert alert-success" id="alert-success">비밀번호가 일치합니다.</div>
                <div class="alert alert-danger" id="alert-danger">비밀번호 확인이 필요합니다. 비밀번호가 일치하지 않습니다.</div>
                <p>${valid_password}</p>

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
                    <div class="input-group-append">
                        <span class="input-group-text" id="email-check">중복확인</span>
                        <span class="input-group-text" id="auth">메일 전송</span>
                    </div>
                    <p>${valid_email}</p>
                </div>
                <div class="form-group input-group authentication">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-envelope"></i> </span>
                    </div>
                    <input class="form-control" id="code" placeholder="6 CODE NUMBERS" required/>
                    <div class="input-group-append">
                        <span class="input-group-text" id="confirm">인증</span>
                    </div>
                </div>

                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-phone"></i> </span>
                    </div>
                    <input name=phone class="form-control" placeholder="PHONE NUMBER" type="text">
                </div> <!-- form-group// -->
                <div class="form-group input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fas fa-address-card"></i> </span>
                    </div>
                    <input type="text" class="form-control" id="nickname" name="nickname" placeholder="NICKNAME" required/>
                </div> <!-- form-group end.// -->
                <div class="alert alert-danger" id="nickname-check">이미 사용중인 닉네임입니다.</div><p>${valid_nickname}</p><br>


            <div class="form-group">
                    <button type="submit" name="submit" id="submit" class="btn btn-primary btn-block"> Create Account</button>
                </div> <!-- form-group// -->
                <p class="text-center">계정이 있으십니까? <a href="/user/login">Log In</a> </p>
            </form>
        </article>
    </div> <!-- card.// -->

</div>
<!--container end.//-->
<script>
    //이메일 중복확인
    $("#auth").hide();
    $("#email-check").on("click", function () {
        var email = $("#email").val();
        if(email == ""){
            alert('이메일을 입력해주세요');
            return;
        }
        userService.checkEmail(email, function (data) {
            if (data) {
                alert("이미 존재하는 이메일 입니다. 다른 이메일을 입력해주세요");
            }
            else {
                $("#auth").show();
                alert("사용 가능한 이메일 입니다. 메일 인증을 시도해 주세요");
                $("#email-check").hide();
                $("#auth").removeAttr("disabled");
            }
        });
    });

    // 닉네임 유효성 검사(0 = 중복 / 1 != 중복)
    $("#submit")
        .attr("disabled", true);
    // 아이디 유효성 검사(1 = 중복 / 0 != 중복)
    idck1 = 0;idck = 0;  pwck= 0; mailck =0;
    $("#nickname-check").hide();
    $("#username-check").hide();
    $("#nickname").keyup(function() {
        var nickname = $("#nickname").val();
        userService.checkNickname(nickname, function (data) {
            if (data) {
                $("#nickname-check").show();
                $("#submit").attr("disabled", true);
                idck1=0;
            } else {
                $("#nickname-check").hide();
                idck1 = 1;
            }
            if(idck==1 && idck1==1 && pwck == 1 && mailck == 1){
                $("#submit")
                    .removeAttr("disabled");
            }
        });
    });

    // 아이디 유효성 검사(1 = 중복 / 0 != 중복)

    $("#username").keyup(function() {
        var username = $("#username").val();
        $("#username-check").hide();
        userService.checkUsername(username, function (data) {
            if (data) {
                $("#username-check").show();
                $("#submit").attr("disabled", true);
                idck=0;
            }
            else {
                $("#username-check").hide();
                idck=1;
            }
            if(idck==1 && idck1==1 && pwck == 1 &&mailck ==1){
                $("#submit")
                    .removeAttr("disabled");
            }
        });
    });


        $("#alert-success").hide();
        $("#alert-danger").hide();
        $("input").keyup(function(){
            var password=$("#password").val();
            var confirmPW=$("#confirmPW").val();
            if(password != "" || confirmPW != ""){
                if(password == confirmPW){
                    $("#alert-success").show();
                    $("#alert-danger").hide();
                    pwck = 1;
                }
                else{
                    $("#alert-success").hide(); $("#alert-danger").show();
                    $("#submit").attr("disabled", "disabled");
                    pwck = 0;
                }
                if(idck==1 && idck1==1 && pwck == 1 && mailck ==1){
                    $("#submit")
                        .removeAttr("disabled");
                }
            }
        });

    //이메일 전송.
    $(".authentication").hide();
    $('#auth').click(function(){
        var email = $('#email').val();
        console.log(email);
        alert(email+"로 인증번호 6자리가 전송됩니다..인증번호를 확인해주세요");
        $(".authentication").show();
        mailService.sendEmail(email, function (result) {
            if (result != "true"){
                alert(result);
            }
            return;
        });
    });
    //인증번호 확인
    $('#confirm').click(function(){

        var email = $('#email').val();
        var code = $('#code').val();
        var checking = {
            codeKey : code,
            email : email
        };
        mailService.checkCode(checking, function (result) {
            if (result){
                alert("인증이 완료되었습니다.");
                mailck =1;
                return;
            }
            else {
                alert("인증번호가 틀렸습니다. 인증번호를 다시 확인해주세요");
                return;
            }
            if(idck==1 && idck1==1 && pwck == 1 && mailck ==1){
                $("#submit")
                    .removeAttr("disabled");
            }

        });

    });


</script>

</body>
</html>
