var regex = new RegExp("(.*?)\.(JPG|jpg|jpeg|PNG|png|bmp)$");
var maxSize = 5242880; // 5MB
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

$("input[type='file']").change(function (e) {
    console.log("COUNT"+filecount)
    var formData = new FormData();

    var inputFile = $("input[name='uploadFile']");
    console.log("***INPUT FILE***"+JSON.stringify(inputFile));
    var files = inputFile[0].files;
    console.log("**FILES***"+JSON.stringify(files));
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





function showUploadResult(uploadResultArr) {
    if (!uploadResultArr || uploadResultArr.length == 0) {
        return;
    }

    var uploadUL = $(".uploadResult ul");

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