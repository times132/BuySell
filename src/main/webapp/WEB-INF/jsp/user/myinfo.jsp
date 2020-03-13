<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>
<head>
    <title>내정보</title>

    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
</head>
<body>

<h1>나의 정보</h1>
<sec:authentication property="principal" var="userinfo"/>
    <input type="file" name="uploadProfile"><br>
    <div class="uploadResult">

    </div>
    <label>이메일</label> <input type="text" name="email" value="${userinfo.email}" readonly="readonly"><br>

    <label>비밀번호</label> <input type="password" name="password" value="${userinfo.password}" readonly="readonly"><br>

    <label>이름</label> <input type="text" name="name" value="${userinfo.name}" readonly="readonly"><br>

    <label>ID</label> <input type="text" name="username" value="${userinfo.username}" readonly="readonly"><br>

    <label>핸드폰번호</label> <input type="text" name="phone" value="${userinfo.phone}" readonly="readonly"><br>

    <input type="button" value="회원정보수정" onClick="self.location='/user/modifyuser';">
    <input type="button" value="회원탈퇴" onClick="self.location='/user/password';">
    <input type="button" value="홈으로 이동" onClick="self.location='/';">
    <input type="hidden" name="id" value="${userinfo.id}">

    <script>
        var uploadUL = $(".uploadResult");
        <%--var profilepath = encodeURIComponent(${userinfo.profilePath});--%>
        <%--console.log(profilepath)--%>
        <%--uploadUL.html("<img src='/display?fileName='"+profilepath+"' onerror='this.src='/resources/image/user.png';'><br>")--%>

        $("input[type='file']").change(function (e) {
            var formData = new FormData();
            var inputFile = $("input[name='uploadProfile']");
            var files = inputFile[0].files;

            formData.append("uploadProfile", files[0]);

            $.ajax({
                type: "POST",
                url: "/user/uploadProfile",
                processData: false,
                contentType: false,
                data: formData,
                success: function (result) {
                    console.log("success")
                    UploadResult(result);
                },
                error: function (err) {
                    console.log("에러")
                }
            });
        });

        function UploadResult(uploadResult) {


            console.log(uploadResult)
            var fileCallPath = encodeURIComponent(uploadResult);
            console.log(fileCallPath);
            var str = "<img class='img-thumbnail' src='/display?fileName=" + fileCallPath + "'>";

            uploadUL.html(str);
        }
    </script>
</body>
</html>