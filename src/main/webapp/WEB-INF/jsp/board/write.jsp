<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" href="/resources/css/board.css">
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/editer/summernote-lite.css">

    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
    <script src="/resources/editer/summernote-lite.js"></script>
    <script src="/resources/editer/summernote-ko-KR.js"></script>
    <script>
        $(document).ready(function () {
            var token =  '${_csrf.token}';
            var header = '${_csrf.headerName}';

            $.ajaxSetup({
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                }
            });
        });
    </script>
</head>
<body>
    <%@include file="../include/navbar.jsp"%>
    <BR>
    <div class="container">
        <div class="row">
            <div class="col">
                <form id="writeForm" action="/board/write" method="post">
                    <input type="hidden" name="writer" value="${userinfo.nickname}"> <br>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <sec:authentication property="principal.user" var="userinfo"/>
                    <div class="form-group">
                        <select id="category" class="custom-select col-4">
                            <option value="">대분류</option>
                            <c:forEach items="${category}" var="category">
                                <option value="${category.id}"><c:out value="${category.name}"/></option>
                            </c:forEach>
                        </select>
                        <select id="items" class="custom-select col-4" name="category">
                            <option value="">소분류</option>
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

                    <textarea id="content" name="content" style="display: none"></textarea>

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
                        <label class="upload-div" for="input-img-icon">
                            <img class="upload-img" src="/resources/image/plus.png"/>
                        </label>
                        <input id="input-img-icon" class="input-image" type="file" name="uploadFile" onclick="this.value=null;" multiple>

                    </div>

                    <div class="file-body">
                        <div class="upload-result">
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

    <!-- js & jquery -->
    <script src="/resources/js/fileupload.js"></script>
    <script src="/resources/js/board.js"></script>
    <script>
        $(document).ready(function() {
            //여기 아래 부분
            $('#content').summernote({
                height: 300,                 // 에디터 높이
                minHeight: null,             // 최소 높이
                maxHeight: null,             // 최대 높이
                focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
                lang: "ko-KR",					// 한글 설정
                placeholder: '최대 250자까지 쓸 수 있습니다'	//placeholder 설정
            });
        });
    </script>
    <script>
        $(document).ready(function () {
            var writeForm = $("#writeForm");
            $(".btn-submit").on("click", function (e) {
                e.preventDefault();
                var str = "";

                if (!writeForm.find("#items option:selected").val()){
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

                $(".upload-result ul li").each(function (i, obj) {
                    var jobj = $(obj);
                    str += "<input type='hidden' name='boardFileList[" + i + "].fileName' value='" + jobj.data("filename") + "'>";
                    str += "<input type='hidden' name='boardFileList[" + i + "].uuid' value='" + jobj.data("uuid") + "'>";
                    str += "<input type='hidden' name='boardFileList[" + i + "].uploadPath' value='" + jobj.data("path") + "'>";
                    str += "<input type='hidden' name='boardFileList[" + i + "].image' value='" + jobj.data("type") + "'>";
                });
                writeForm.append(str).submit();
            });
        });

        var categoryDIV = $("#items");
        $("#category").on("change",function(){
            var id = $("#category option:selected").val();

            boardService.getCategoryItems(id, function (data) {
                var str = "<option value=''>---------------------------------------------------</option>";
                for (var i = 0, len = data.length || 0; i < len; i++) {
                    str += "<option value='"+data[i].itemName+"'>"+ data[i].itemName + "</option>"
                }
                categoryDIV.html(str);
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
