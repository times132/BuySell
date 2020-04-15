<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="/resources/css/board.css" rel="stylesheet">
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">

    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
</head>
<body>
    <%@include file="../include/header.jsp"%>
    <BR>
    <div class="container">
        <div class="row">
            <div class="col">
                <form id="writeForm" action="/board/write" method="post">
                    <input type="hidden" name="writer" value="${userinfo.nickname}"> <br>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <sec:authentication property="principal.user" var="userinfo"/>
                    <div class="form-group">
                        <select class="custom-select col-4" name="btype">
                            <option value="">카테고리</option>
                            <option value="기프티콘">기프티콘</option>
                            <option value="디지털">디지털/가전</option>
                            <option value="생활">생활</option>
                            <option value="기타">기타</option>
                        </select>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-9">
                            <input type="text" class="form-control" name="title" placeholder="제목 (최대 30자)" maxlength="30">
                        </div>
                        <div class="form-group input-group col-3">
                            <div class="input-group-prepend">
                                <span class="input-price-text">&#8361;</span>
                            </div>
                            <input type="text" class="form-control" name="price" placeholder="가격" onkeyup="return this.value=this.value.replace(/[^0-9]/g,'');">
                        </div>
                    </div>
                    <div class="form-group">
                        <textarea class="form-control" name="content" rows="10" placeholder="본문 (최대 250자)" maxlength="250"></textarea>
                    </div>
                </form>
            </div>

        </div>

        <!-- 사진 업로드 -->
        <div class="row mb-3">
            <div class="col">
                <div class="image-frame">
                    <div class="file-head">
                        <span class="h5 font-weight-bold">사진</span>
                        <span class="h6 text-muted">(최대 10장 까지 가능하며 총 50MB까지 가능합니다.)</span>
                        <label class="uploadDiv" for="input-img-icon">
                            <img class="uploadimg" src="/resources/image/plus.png"/>
                        </label>
                        <input id="input-img-icon" class="input-image" type="file" name="uploadFile" onclick="this.value=null;" multiple>

                    </div>

                    <div class="file-body">
                        <div class="uploadResult">
                            <ul>

                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <span class="col"><a href="#" class="btn btn-outline-dark btn-submit"><i></i>작성하기</a></span>
            <a class="col" href="/board"><button class="btn btn-dark float-right" type="submit">목록</button></a>
        </div>

    </div>

    <script type="text/javascript" src="/resources/js/fileupload.js"></script>
    <script>
        $(document).ready(function () {
            var writeForm = $("#writeForm");
            $(".btn-submit").on("click", function (e) {
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
                if (!writeForm.find("textarea[name='content']").val()){
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


            $(".uploadResult").on("click", ".del-image", function (e) {
                filecount -= 1;
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

        $("body").on({
            mouseenter: function () {
                $(this).closest("li").css("border", "1px solid red");
            },
            mouseleave: function () {
                $(this).closest("li").css("border", "none");
            }
        }, ".del-image");
    </script>
</body>
</html>
