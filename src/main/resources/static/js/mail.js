console.log("mail module****************************");

var mailService = (function () {


    function sendEmail(email, callback, error) {
        $.ajax({
            type: "post",
            url: "/user/auth",
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

    // function signup(email, callback, error) {
    //     $.ajax({
    //         type: "get",
    //         url: "/user/signup",
    //         data: email,
    //         dataType: 'text',//데이타 타입
    //         async: true,
    //         success: function (result) {
    //             if (callback){
    //                 callback(result);
    //             }
    //         },
    //         error: function (xhr, status, err) {
    //             if (error){
    //                 error(err);
    //                 alert("실패");
    //             }
    //         }
    //     });
    // }
    return {

        sendEmail : sendEmail,
    };





})();