console.log("mail module****************************");

var mailService = (function () {
    function sendEmail(email, callback, error) {
        $.ajax({
            type: "get",
            url: '/user/auth?email='+ email,
            data: email,
            dataType: 'text',//데이타 타입
            async: true,
            success: function (result) {
                if (callback){
                    callback(result);
                }
            },
            error: function (xhr, status, err) {
                if (error){
                    error(err);
                    alert("실패");
                }
            }
        });
    }
    function checkCode(checking, callback, error) {
        $.ajax({
            type: "get",
            url: "/mail/checkCode",
            data: checking,
            async: true,
            success: function (result) {
                if (callback){
                    callback(result);
                }
            },
            error: function (xhr, status, err) {
                if (error){
                    error(err);
                    alert("실패");
                }
            }
        });
    }

    return {
        sendEmail : sendEmail,
        checkCode : checkCode
    };





})();