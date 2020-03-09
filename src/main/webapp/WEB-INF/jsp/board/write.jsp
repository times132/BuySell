<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="/resources/css/test.css" rel="stylesheet">
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">

    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
</head>
<body>
    <%@include file="../include/header.jsp"%>

    <form id="writeForm" action="/board/write" method="post">
        <sec:authentication property="principal" var="userinfo"/>
        <div>
            <label>분류 : </label>
            <select name="btype">
                <option value="">====</option>
                <option value="커피">커피</option>
                <option value="외식">외식</option>
                <option value="상품권">상품권</option>
                <option value="기타">기타</option>
            </select>
        </div>

        제목 : <input type="text" name="title"> <br>
        내용 : <input type="text" name="content"> <br>
        작성자 : <input type="text" name="writer" value="${userinfo.username}" readonly="readonly"> <br>
        가격 : <input type="text" name="price"> <br>

        <button type="submit">등록</button>
    </form>

    <div>
        <div class="file-head">사진</div>
        <div class="file-body">
            <div class="uploadDiv">
                <input type="file" name="uploadFile" multiple>
            </div>
            <div class="uploadResult">
                <ul>

                </ul>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="/resources/js/fileupload.js"></script>
    <script>
        $(document).ready(function () {
            var writeForm = $("#writeForm");
            $("button[type='submit']").on("click", function (e) {
                e.preventDefault();
                var str = "";

                if (!writeForm.find("option:selected").val()){
                    alert("분류를 선택하세요.");
                    return false;
                }
                if (!writeForm.find("input[name='title']").val()){
                    alert("제목을 입력하세요");
                    return false;
                }
                if (!writeForm.find("input[name='content']").val()){
                    alert("내용을 입력하세요");
                    return false;
                }
                if (!writeForm.find("input[name='price']").val()){
                    alert("가격을 입력하세요");
                    return false;
                }

                $(".uploadResult ul li").each(function (i, obj) {
                    var jobj = $(obj);
                    console.log(jobj);
                    str += "<input type='hidden' name='boardFileList[" + i + "].fileName' value='" + jobj.data("filename") + "'>";
                    str += "<input type='hidden' name='boardFileList[" + i + "].uuid' value='" + jobj.data("uuid") + "'>";
                    str += "<input type='hidden' name='boardFileList[" + i + "].uploadPath' value='" + jobj.data("path") + "'>";
                    str += "<input type='hidden' name='boardFileList[" + i + "].image' value='" + jobj.data("type") + "'>";
                });
                writeForm.append(str).submit();
            });

            $(".uploadResult").on("click", "button", function (e) {
                console.log("delete file");
                var targetFile = $(this).data("file");
                var type = $(this).data("type");

                var targetLi = $(this).closest("li");

                $.ajax({
                    type: "post",
                    url: "/deleteFile",
                    data: {fileName: targetFile, type:type},
                    dataType: "text",
                    success: function (result) {
                        alert(result);
                        targetLi.remove();
                    }
                });
            });
        });
    </script>
</body>
</html>
