console.log("user module****************************");

var userService = (function () {

    function checkUsername(username, callback, error) {
        console.log("CHECK ID");
        $.ajax({
            url : '/user/usernameCheck?username='+ username,
            type : 'get',
            async: true,
            success: function (data) {
                if (callback){
                    callback(data);
                }
            },
            error: function (xhr, status, err) {
                if (error){
                    error(err);
                }
            }
        });
    }

    function checkNickname(nickname, callback, error) {
        console.log("CHECK NICKNAME");
        $.ajax({
            url : '/user/nicknameCheck?nickname='+ nickname,
            type : 'get',
            async: true,
            success: function (data) {
                if (callback){
                    callback(data);
                }
            },
            error: function (xhr, status, err) {
                if (error){
                    error(err);
                }
            }
        });
    }
    function checkEmail(email, callback, error) {
        console.log("CHECK NICKNAME");
        $.ajax({
            url : '/user/emailCheck?email='+ email,
            type : 'get',
            async: true,
            success: function (data) {
                if (callback){
                    callback(data);
                }
            },
            error: function (xhr, status, err) {
                if (error){
                    error(err);
                }
            }
        });
    }

    function changePW(password, callback, error) {
        console.log("CHANGE PASSWORD");
        $.ajax({
            url: '/user/changePW',
            data: password,
            type : 'put',
            async: true,
            success: function (data) {
                if (callback){
                    callback(data);
                }
            },
            error: function (xhr, status, err) {
                if (error){
                    error(err);
                }
            }
        });
    }

    function findPW(username, callback, error) {
        console.log("FIND PASSWORD");
        $.ajax({
            type: "POST",
            url: "/user/findpw",
            data: username,
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

    return {
        checkNickname : checkNickname,
        checkUsername : checkUsername,
        changePW : changePW,
        findPW : findPW,
    };





})();