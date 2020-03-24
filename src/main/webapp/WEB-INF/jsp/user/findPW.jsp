<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
    <meta charset="UTF-8">
    <title>비밀번호 찾기 페이지</title>
</head>
<body>
<h1>비밀번호 찾기</h1>
<hr>

    <label for="email">가입할 때 입력하셨던 이메일을 입력해주세요</label>
    <input type="text" class="form-control" id="email" name="email" placeholder="ID" required>
    <div class="alert alert-danger" id="email_check">존재하는 이메일이 없습니다.</div><br>


    <div>
        <input type="submit" name="submit" id="submit" value="확인"/>
    </div>

</form>
<script type="text/javascript" src="/resources/js/user.js"></script>
<script>
    // 아이디 유효성 검사(1 = 중복 / 0 != 중복)
    idck = 0;
    $("#email").keyup(function() {
        var email = $("#email").val();
        var e_mail = {
            email : email
        };
        userService.checkEmail(email, function (data) {
            if (data == false) {
                $("#email_check").show();
                $("#submit").attr("disabled", true);
                idck=0;
            }
            else {
                $("#email_check").hide();
                idck= 1;
            }
            if(idck==1){
                $("#submit")
                    .removeAttr("disabled")
                    .click(function() {
                        alert("임시비밀번호가 고객님의 이메일로 전송되었습니다. 이메일을 확인해주세요");
                        userService.findPW(e_mail, function (result) {
                            alert(result);
                            location.href = "/user/login"
                    });

                });
            }
        });
    });
    $("#email_check").hide();


</script>

</body>
</html>