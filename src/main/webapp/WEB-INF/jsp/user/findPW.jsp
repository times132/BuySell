<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <script src="/webjars/jquery/dist/jquery.min.js"></script>
    <meta charset="UTF-8">
    <title>비밀번호 찾기 페이지</title>
</head>
<body>
<h1>비밀번호 찾기</h1>
<hr>

<%--<form  action="/user/findpw" method="post">--%>

    <label for="email">가입할 때 입력하셨던 이메일을 입력해주세요</label>
    <input type="text" class="form-control" id="email" name="email" placeholder="ID" required>
    <div class="check_font" id="email_check"></div><br>


    <div>
        <input type="submit" name="submit" id="submit" value="확인"/>
    </div>

</form>
<script>
    // 아이디 유효성 검사(1 = 중복 / 0 != 중복)
    idck = 0;
    $("#email").blur(function() {
        var email = $("#email").val();
        $.ajax({
            url : '${pageContext.request.contextPath}/user/idCheck?email='+ email,
            type : 'get',
            success : function(data) {
                console.log("1 = 중복o / 0 = 중복x : "+ data);
                if (data == 1) {
                    //아이디가 존제할 경우 빨깡으로 , 아니면 파랑으로 처리하는 디자인
                    $("#email_check").text("사용 중인 이메일이 확인되었습니다.");
                    $("#email_check").css("color", "blue");
                    $("#submit").attr("disabled", false);
                    idck=1;
                } else {
                    $("#email_check").text("현재 존재하는 이메일이 없습니다.");
                    $("#email_check").css("color", "red");
                    $("#submit").attr("disabled", true);

                }

                if(idck==1){
                    $("#submit")
                        .attr("disabled", false)
                        .click(function() {
                            alert("임시비밀번호가 고객님의 이메일로 전송되었습니다. 이메일을 확인해주세요");
                            location.href="/user/findpw.do"+email;
                        })
                }

            }, error : function() {
                console.log("실패");

            }

        });


    });



</script>

</body>
</html>