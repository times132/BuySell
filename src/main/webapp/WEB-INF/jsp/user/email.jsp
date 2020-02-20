<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
이메일 인증 (이메일을 인증 받아야 회원가입을 진행하실 수 있습니다. )
        <br> <br>
                        <form action="auth.do" method="post">
                                <br>
                                <div>
                                    이메일 : <input type="email" name="email"  placeholder="  이메일주소를 입력하세요. ">
                                </div>

                                <br> <br>
                                <button type="submit" name="submit">이메일 인증받기 (이메일 보내기)</button>


</form>
</center>

</body>
</html>
