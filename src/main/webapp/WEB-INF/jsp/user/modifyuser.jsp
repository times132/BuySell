<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>
<head>
    <script src="/webjars/jquery/dist/jquery.min.js"></script>
    <title>내정보</title>

</head>
<body>
<form  action="/user/modifyuser" method="post">
    <sec:authentication property="principal" var="userinfo"/>
    이메일 : <input type="text" name="email" value="${userinfo.email}" readonly="readonly"></p><br>

    패스워드   : <input type="text" name="password" value="${userinfo.password}"/><p>${valid_password}</p><br>

    이름     : <input type="text" name="name" value="${userinfo.name}"><p> ${valid_name}</p> <br>

    <label for="username">아이디</label>
    <input type="text" class="form-control" id="username" name="username" value="${userinfo.username}" required>
    <div class="check_font" id="username_check"></div><br>

    핸드폰번호  : <input type="text" name="phone" value="${userinfo.phone}" ><p> ${valid_phone}</p> <br>


    <input type="hidden" name="id" value="${userinfo.id}">
    <input type="hidden" name="authorities" value="${userinfo.authorities}">
    <div>
        <input type="submit" name="submit" id="submit" value="수정"/>
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
                    $("#submit").attr("disabled", false);
                    idck=1;
                }
                if(idck==1){
                    $("#submit")
                        .attr("disabled", false)
                }
            }, error : function() {
                console.log("실패");
            }
        });
    });
</script>
</body>

</html>