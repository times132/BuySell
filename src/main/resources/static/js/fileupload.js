var regex = new RegExp("(.*?)\.(JPG|jpg|jpeg|PNG|png|bmp)$");
var maxSize = 10485760; // 10MB
let filecount = 0;

function checkExtension(fileName, fileSize) {
    if (!regex.test(fileName)){
        alert("이미지만 업로드 가능합니다.");
        return false;
    }

    if (fileSize > maxSize){
        alert("파일 사이즈가 너무 큽니다.");
        return false;
    }

    return true;
}

// 게시물 사진 첨부
$("input[name='uploadFile']").change(function (e) {
    var formData = new FormData();
    var inputFile = $("input[name='uploadFile']");
    var files = inputFile[0].files;
    filecount += files.length;

    if (filecount > 10){
        alert("파일은 최대 10개까지 업로드 가능합니다.");
        filecount -= files.length;
        return false;
    }

    for (var i = 0; i < files.length; i++){
        if (!checkExtension(files[i].name, files[i].size)){
            return false;
        }

        formData.append("uploadFile", files[i]);
    }

    $.ajax({
        type: "POST",
        url: "/uploadFile",
        processData: false,
        contentType: false,
        data: formData,
        dataType: "json",
        success: function (result) {
            showUploadResult(result);
        }
    });
});

// 프로필 사진 첨부
$("input[name='uploadProfile']").on('change', function(){
    var formData = new FormData();
    var inputFile = $("input[name='uploadProfile']");
    var file = inputFile[0].files[0];

    if (!checkExtension(file.name, file.size)){
        return false;
    }

    formData.append("uploadProfile", file);

    $.ajax({
        type: "POST",
        url: "/uploadProfile",
        processData: false,
        contentType: false,
        data: formData,
        dataType: "json",
        success: function (result) {
            showUploadProfile(result);
        },
        error: function (error) {
            console.log("에러")
        }
    });
});

function showUploadProfile(uploadResult) {
    if (!uploadResult || uploadResult.length == 0) {
        return;
    }

    if (uploadResult.image){
        var fileCallPath = encodeURIComponent(uploadResult.uploadPath + "/" + uploadResult.fileName);
        var str = "<img class='img-thumbnail' src='/display?fileName=" + fileCallPath + "'>";

        $("#profileImage").val(uploadResult.fileName);
        $(".profile-image").html(str);
    }
}

function showUploadResult(uploadResultArr) {
    if (!uploadResultArr || uploadResultArr.length == 0) {
        return;
    }
    var uploadUL = $(".upload-result ul");
    var str = "";

    $(uploadResultArr).each(function (i, obj) {
        if (obj.image){
            var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);

            if (obj.fid === null){ // 새로추가된 파일은 fid가 없으므로 -1로 초기화
                obj.fid = -1;
            }

            str += "<li class='thum-image' data-fid='" + obj.fid + "' data-path='" + obj.uploadPath + "'" + " data-uuid='" + obj.uuid;
            str += "' data-fileName='" + obj.fileName + "' data-type='" + obj.image + "'>";
            str += "<img class='img-thumbnail' src='/display?fileName=" + fileCallPath + "'>";
            str += "<input class='del-image' type='button' data-file=\'" + fileCallPath + "\' data-type='image'/>";
            str += "</li>";
        }else{
            return;
        }
    });
    uploadUL.append(str);
}

$(".upload-result").on("click", ".del-image", function (e) {
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