<%@ page language="java" contentType="text/html; charset=EUC-KR"
         pageEncoding="EUC-KR"%>
<%@ page import="jsp.member.model.MemberDAO" %>
<%@ page import="jsp.member.model.MemberBean" %>
<%@ page import="com.example.giveandtake.DTO.UserDTO" %>
<%@ page import="com.example.giveandtake.model.entity.User" %>
<html>
<head>
    <title>현재 유저정보 출력화면</title>

    <style type="text/css">
        table{
            margin-left:auto;
            margin-right:auto;
            border:3px solid skyblue;
        }

        td{
            border:1px solid skyblue
        }

        #title{
            background-color:skyblue
        }
    </style>

    <script type="text/javascript">

        function changeForm(val){
            if(val == "-1"){
                location.href="MainForm.jsp";
            }else if(val == "0"){
                location.href="MainForm.jsp?contentPage=member/view/ModifyFrom.jsp";
            }else if(val == "1"){
                location.href="MainForm.jsp?contentPage=member/view/DeleteForm.jsp";
            }
        }

    </script>

</head>
<body>



<br><br>
<b><font size="6" color="gray">내 정보</font></b>
<br><br><br>
<!-- 가져온 회원정보를 출력한다. -->
<table>
    <tr>
        <td id="title">아이디</td>
        <p>${userDTO.email}</p>
    </tr>

    <tr>
        <td id="title">비밀번호</td>
        <p>${userDTO.password}</p>
    </tr>


</table>

<br>
<input type="button" value="뒤로" onclick="changeForm(-1)">
<input type="button" value="회원정보 변경" onclick="changeForm(0)">
<input type="button" value="회원탈퇴" onclick="changeForm(1)">
</body>
</html>


출처: https://all-record.tistory.com/117 [세상의 모든 기록]