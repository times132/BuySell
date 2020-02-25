<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <script src="/webjars/jquery/dist/jquery.min.js"></script>
    <meta charset="UTF-8">
    <title>회원가입 페이지</title>
</head>
<body>
<h1>회원 가입</h1>
<hr>

<form  action="/user/signup" method="post">
    이메일 : <input type="text" name="email" value="${email}"/><p>${valid_email}</p><br>

    <label>비밀번호</label> <input type="password" name="password" id="pwd1" class="form-control" required />
    <label>재확인</label> <input type="password" name="reuserPwd" id="pwd2" class="form-control" required /> <br>​<p>${valid_password}</p>
    <div class="alert alert-success" id="alert-success">비밀번호가 일치합니다.</div>
    <div class="alert alert-danger" id="alert-danger">비밀번호확인이 필요합니다. 비밀번호가 일치하지 않습니다.</div><br>

    이름     : <input type="text" name="name" value="${userDto.name}"><p> ${valid_name}</p> <br>

        <label for="username">아이디</label>
        <input type="text" class="form-control" id="username" name="username" placeholder="ID" required>
        <div class="check_font" id="username_check"></div><br>

    핸드폰번호  : <input type="text" name="phone" value="${userDto.phone}" ><p> ${valid_phone}</p> <br>

    <div>
        <input type="submit" name="submit" id="submit" value="회원가입"/>
    </div>

</form>
<script>
    // 아이디 유효성 검사(1 = 중복 / 0 != 중복)
    idck = 0;
    $("#username").blur(function() {
        var username = $("#username").val();
        $.ajax({
            url : '${pageContext.request.contextPath}/user/usernameCheck?username='+ username,
            type : 'get',
            success : function(data) {
                console.log("1 = 중복o / 0 = 중복x : "+ data);
                if (data == 1) {
                    //아이디가 존제할 경우 빨깡으로 , 아니면 파랑으로 처리하는 디자인
                    $("#username_check").text("사용중인 아이디입니다. 다른 아이디를 입력해주세요");
                    $("#username_check").css("color", "red");
                    $("#submit").attr("disabled", true);
                } else {
                    $("#username_check").text("사용가능한 아이디입니다.");
                    $("#username_check").css("color", "blue");
                    $("#submit").attr("disabled", true);
                    idck=1;
                }

                if(idck==1){
                    $("#submit")
                        .attr("disabled", false)
                        // .click(function() { alert("회원가입이 완료되었습니다."); })
                }

            }, error : function() {
                console.log("실패");

            }

        });


    });

    $(function(){
        $("#alert-success").hide();
        $("#alert-danger").hide();
        $("input").keyup(function(){ var pwd1=$("#pwd1").val(); var pwd2=$("#pwd2").val();
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

