<%@ page import="com.example.giveandtake.model.entity.User" %>
<%@ page import="com.example.giveandtake.DTO.UserDTO" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
         pageEncoding="EUC-KR"%>
<html>
<head>
    <title>내정보</title>

</head>
<body>
<div id = "wrap">
    <p>
        <input type="button" value="홈으로 이동" onClick="self.location='/';">

        <%
            // 로그인 안되었을 경우 - 로그인, 회원가입 버튼을 보여준다.
            if(session.getAttribute("email")==null){
        %>
        로그인이 되어있지 않습니다.
        <input type="button" value="로그인하러가기" onClick="self.location='/user/login';">
        <input type="button" value="회원가입" onClick="self.location='/user/signup';">
        <input type="button" value="로그아웃" onClick="self.location='/user/logout';">
        <%
            // 로그인 되었을 경우 - 로그아웃, 내정보 버튼을 보여준다.
        }
            else{
        %>
        <input type="button" value="로그아웃" onClick="self.location='/user/logout';">
        <input type="button" value="상세정보" onClick="self.location='/user/userdetail';">
        <%    }    %>


    </p>
</div>
</body>
</html>
