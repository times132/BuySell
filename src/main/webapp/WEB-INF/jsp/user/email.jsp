<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>이메일 인증</title>
    <script src="/webjars/jquery/dist/jquery.min.js"></script>


</head>
<body>
이메일 인증 (이메일을 인증 받아야 회원가입을 진행하실 수 있습니다. )
        <br> <br>
<%--        <form action="auth.do" method="post">--%>
<div class="form-group">
    <label for="email">이메일</label>
    <input type="text" class="form-control" id="email" name="email" placeholder="ID" required>
    <div class="check_font" id="id_check"></div>
</div>

            <input type="button" name="frm" id="frm" value="이메일 인증받기"/>



<script>
    // 아이디 유효성 검사(1 = 중복 / 0 != 중복)
    idck = 0;
    $("#email").blur(function() {
        // id = "id_reg" / name = "userId"
        var email = $('#email').val();
        consol.log(email);
        $.ajax({
            url : '${pageContext.request.contextPath}/user/idCheck?email='+ email,
            type : 'get',
            success : function(data) {
                console.log("1 = 중복o / 0 = 중복x : "+ data);
                if (data == 1) {
                    //아이디가 존제할 경우 빨깡으로 , 아니면 파랑으로 처리하는 디자인
                    $("#id_check").text("사용중인 아이디입니다 :p");
                    $("#id_check").css("color", "red");
                    $("#frm").attr("disabled", true);
                } else {
                    $("#id_check").text("사용가능한 아이디입니다.");
                    $("#id_check").css("color", "blue");
                    $("#frm").attr("disabled", true);
                    //아이디가 중복하지 않으면  idck = 1
                    idck = 1;
                }

                    if(idck==1){
                        $("#email").blur($('#frm')
                            .alert("이메일이 전송되었습니다")
                            .click(function() {
                            location.href="/user/auth.do"+email;
                        }));

                    }

            }, error : function() {
                console.log("실패");

            }

        });

    });



</script>


</body>
</html>
