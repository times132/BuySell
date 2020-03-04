var regex = new RegExp("(.*?)\.(JPG|jpg|jpeg|png|bmp)$");
var maxSize = 5242880; // 5MB

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
    var formData = new FormData();
    var inputFile = $("input[name='uploadFile']");
    var files = inputFile[0].files;

    for (var i = 0; i < files.length; i++){
        if (!checkExtension(files[i].name, files[i].size)){
            return false;
        }
        formData.append("uploadFile", files[i]);
        console.log(formData.get("uploadFile"));
    }

    $.ajax({
        type: "POST",
        url: "/uploadFile",
        processData: false,
        contentType: false,
        data: formData,
        dataType: "json",
        success: function (result) {
            console.log(result);
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
                console.log(obj.fid);
                obj.fid = -1;
            }

            str += "<li data-fid='" + obj.fid + "' data-path='" + obj.uploadPath + "'" + " data-uuid='" + obj.uuid;
            str += "' data-fileName='" + obj.fileName + "' data-type='" + obj.image + "'><div>";
            str += "<span> " + obj.fileName + "</span>";
            str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='image'><i>x</i></button></br>";
            str += "<img src='/display?fileName=" + fileCallPath + "'>";
            str += "</div></li>";
        }else{
            return;
        }
    });

    uploadUL.append(str);
}