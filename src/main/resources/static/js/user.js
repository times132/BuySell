var userService = (function () {

    function checkUsername(username, callback, error) {
        $.ajax({
            url : '/user/checkUsername?username='+ username,
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
        $.ajax({
            url : '/user/checkNickname?nickname='+ nickname,
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
        $.ajax({
            url : '/user/checkEmail?email='+ email,
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
        $.ajax({
            url: '/user/changePW',
            data: password,
            type : 'post',
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
        $.ajax({
            type: "POST",
            url: "/user/findUser/findPW",
            data: username,
            dataType: 'text',//데이타 타입
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
        checkEmail: checkEmail,
        changePW : changePW,
        findPW : findPW,
    };
})();