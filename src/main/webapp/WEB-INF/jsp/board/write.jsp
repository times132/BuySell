<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="/resources/css/board.css" rel="stylesheet">
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">

    <style>
        .input-price-text{
            display: flex;
            -ms-flex-align: center;
            align-items: center;
            padding: .375rem .75rem;
            margin-bottom: 0;
            font-size: 1rem;
            font-weight: 400;
            line-height: 1.5;
            color: #495057;
            text-align: center;
            white-space: nowrap;
            border: 1px solid #ced4da;
            background-color: whitesmoke;
            border-radius: .25rem;
        }
        .image-frame{
            border: 1px solid black;
            border-radius: .25rem;
            padding: .5rem;
        }
        .input-image{
            visibility: hidden;
            width: 0;
            height: 0;
        }
        .uploadimg{
            cursor: pointer;
        }
        .uploadResult{
            width: 100%;
            background-color: gray;
        }
        .uploadResult ul{
            display: flex;
            flex-flow: row;
            justify-content: center;
            align-items: center;
        }
        .uploadResult ul li{
            list-style: none;
            padding: 10px;
        }
        .uploadResult ul li img{
            width: 30%;
        }
    </style>

    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
</head>
<body>
    <%@include file="../include/header.jsp"%>

    <div class="container">
        <div class="row">
            <div class="col">
                <form id="writeForm" action="/board/write" method="post">
                    <sec:authentication property="principal" var="userinfo"/>
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
                            <input type="text" class="form-control" name="title" placeholder="제목">
                        </div>
                        <div class="form-group input-group col-3">
                            <div class="input-group-prepend">
                                <span class="input-price-text">&#8361;</span>
                            </div>
                            <input type="text" class="form-control" name="price" placeholder="가격">
                        </div>
                    </div>
                    <div class="form-group">
                        <textarea class="form-control" name="content" rows="10" placeholder="본문"></textarea>
                    </div>
                    <input type="hidden" name="writer" value="${userinfo.username}"> <br>
                </form>
            </div>

        </div>

        <!-- 사진 업로드 -->
        <div class="row">
            <div class="col">
                <div class="image-frame">
                    <div class="file-head"><p class="h5">사진</p></div>
                    <div class="file-body">
<%--                        <div class="uploadDiv">--%>
<%--                            <input type="file" name="uploadFile" multiple>--%>
<%--                        </div>--%>

                        <div class="uploadDiv">
                            <label for="input-img-icon">
                                <img class="uploadimg" src="/resources/image/plus.png"/>
                            </label>
                            <input id="input-img-icon" class="input-image" type="file" name="uploadFile" onclick="this.value=null;" multiple>
                        </div>

                        <div class="uploadResult">
                            <ul>

                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <button type="submit">등록</button>
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

            $(".uploadResult").on("click", "button", function (e) {
                console.log("onclick")
                filecount -= 1;
                var targetFile = $(this).data("file");
                var type = $(this).data("type");

                var targetLi = $(this).closest("li");
                console.log(targetFile);
                console.log(type);
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
