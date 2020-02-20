<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>이메일 인증번호 인증페이지</title>
</head>
<body>

 입력한 이메일로 받은 인증번호를 입력하세요. (인증번호가 맞아야 다음 단계로 넘어가실 수 있습니다.)<br> <br>
 <form action="/user/join_injeung.do${key}" method="post"> //받아온 인증코드를 컨트롤러로 넘겨서 일치하는지 확인 <br>
     <div>  인증번호 입력 : <input type="number" name="email_injeung" placeholder="  인증번호를 입력하세요. ">
     </div>
     <br> <br>

     <button type="submit" name="submit">인증번호 전송</button>

</form>



</body>
</html>
