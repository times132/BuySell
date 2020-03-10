<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>이메일 인증</title>
    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
    <script type="text/javascript" src="/resources/js/mail.js"></script>

</head>
<body>
이메일 인증 (이메일을 인증 받아야 회원가입을 진행하실 수 있습니다. )
<br> <br>
<div class="form-group">
    <label for="email">이메일</label>
    <input type="text" id="email" name="email" placeholder="ID" required>
    <div class="check_font" id="id_check"></div>
</div>

            <input type="button" name="frm" id="frm" value="이메일 인증받기"/>



<script>
    // 아이디 유효성 검사(1 = 중복 / 0 != 중복)
    idck = 0;
    $("#email").blur(function() {
        // id = "id_reg" / name = "userId"
        var email = $('#email').val();
        console.log(email);
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
                        $("#email").blur(
                            $('#frm')
                                .attr("disabled", false)
                                .click(function() {
                                    var e_mail = {
                                        email : email
                                    };
                                    alert("이메일이 전송되었습니다. 이메일 인증번호를 확인해주세요");
                                    mailService.sendEmail(e_mail, function (result) {


                                        for(var i=0; i<3; i++) {
                                            var code = prompt("인증번호를 입력해주세요");
                                            if (code == result) {
                                                alert("회원가입을 진행해주세요");
                                                location.href = "/user/email/"+email;
                                                return;
                                            } else {
                                                alert("인증번호가 틀렸습니다. 인증번호를 다시 확인해주세요");
                                            }
                                        }
                                        alert("인증번호가 3회이상 틀렸습니다. 이메일 전송을 다시시도해주세요");
                                        location.href = "/user/email"

                                    });
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
