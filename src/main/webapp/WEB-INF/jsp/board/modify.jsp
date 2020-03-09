<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

    <form action="/board/modify" method="post">
        <input type="hidden" name="bid" value="${boardDto.bid}"/>
        <input type="hidden" name="viewCnt" value="${boardDto.viewCnt}"/>
        <input type="hidden" name="replyCnt" value="${boardDto.replyCnt}"/>
        <input type="hidden" name="page" value="<c:out value="${cri.page}"/>">
        <input type="hidden" name="type" value="<c:out value="${cri.type}"/>">
        <input type="hidden" name="keyword" value="<c:out value="${cri.keyword}"/>">

        분류 : <input type="text" name="btype" value="${boardDto.btype}" readonly="readonly"> <br>
        제목 : <input type="text" name="title" value="${boardDto.title}"> <br>
        내용 : <input type="text" name="content" value="${boardDto.content}"> <br>
        작성자 : <input type="text" name="writer" value="${boardDto.writer}" readonly="readonly"> <br>
        가격 : <input type="text" name="price" value="${boardDto.price}"> <br>
    </form>

    <div class="imageWrapper">
        <div class="originPicture">

        </div>
    </div>

    <div class="panel">
        <div>사진</div>
        <div class="panel-body">
            <div class="uploadDiv">
                <input type="file" name="uploadFile" multiple>
            </div>
            <div class="uploadResult">
                <ul>
                </ul>
            </div>
        </div>
    </div>

    <button data-oper="modify" type="submit">수정</button>
    <button data-oper="list" type="submit">목록</button>

    <script type="text/javascript" src="/resources/js/fileupload.js"></script>
    <script>
        $(document).ready(function(){
            var formObj = $("form");

            $("button").on("click", function(e){
                e.preventDefault();

                var operation = $(this).data("oper");

                if(operation === "list"){
                    formObj.attr("action", "/board").attr("method", "get");

                    var pageTag = $("input[name='page']").clone();
                    var typeTag = $("input[name='type']").clone();
                    var keywordTag = $("input[name='keyword']").clone();

                    formObj.empty();
                    formObj.append(pageTag);
                    formObj.append(typeTag);
                    formObj.append(keywordTag);
                }else if (operation === 'modify'){
                    var str = "";

                    $(".uploadResult ul li").each(function (i, obj) {
                        var jobj = $(obj);

                        str += "<input type='hidden' name='boardFileList[" + i + "].fid' value='" + jobj.data("fid") + "'>";
                        str += "<input type='hidden' name='boardFileList[" + i + "].fileName' value='" + jobj.data("filename") + "'>";
                        str += "<input type='hidden' name='boardFileList[" + i + "].uuid' value='" + jobj.data("uuid") + "'>";
                        str += "<input type='hidden' name='boardFileList[" + i + "].uploadPath' value='" + jobj.data("path") + "'>";
                        str += "<input type='hidden' name='boardFileList[" + i + "].image' value='" + jobj.data("type") + "'>";
                    });
                    formObj.append(str).submit();
                }
                formObj.submit();
            });

            var bidValue = "<c:out value="${boardDto.bid}"/>";

            $.getJSON("/board/getFileList", {bid: bidValue}, function (arr) {
                var str = "";

                $(arr).each(function (i, file) {
                    if (file.image){
                        var fileCallPath = encodeURIComponent(file.uploadPath + "/s_" + file.uuid + "_" + file.fileName);

                        str += "<li data-fid='" + file.fid + "' data-path='" + file.uploadPath + "' data-uuid='" + file.uuid + "' data-fileName='" + file.fileName + "' data-type='" + file.image + "'><div>";
                        str += "<span> " + file.fileName + "</span>";
                        str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='image'><i>X</i></button></br>";
                        str += "<img src='/display?fileName=" + fileCallPath + "'>";
                        str += "</div>";
                        str += "</li>"
                    }
                });
                $(".uploadResult ul").html(str);
            });

            $(".uploadResult").on("click", "button", function (e) {
                if (confirm("사진을 삭제하시겠습니까?")){
                    var targetLi = $(this).closest("li");
                    targetLi.remove();
                }
            });
        });
    </script>
</body>
</html>