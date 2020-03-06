console.log("chat module****************************");

var chatService = (function () {

    function findAllRoom(callback, error) {
        console.log("GET CHAT FIND ALL ROOM");

        $.ajax({
            type: "get",
            url: "/chat/rooms",
            async: true,
            success: function (result) {
                if (callback){
                    callback(result);
                }
            },
            error: function (xhr, status, err) {
                if (error){
                    error(err);
                }
            }
        });
    }

    function createRoom(room, callback, error) {
        // console.log("CREATE CHAT ROOM");
        console.log(room);
        $.ajax({
            type: "post",
            url: "/chat/room",
            data: room,
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
                }
            }
        });
    }

    function deleteRoom(roomId, callback, error) {
        console.log("****DELETE ROOM*********")
        $.ajax({
            type: "get",
            url: "/chat/room/stop/" + roomId,
            async: true,
            success: function (result) {
                if (callback) {
                    callback(result);
                }
            },
            error: function (xhr, status, err) {
                if (error) {
                    error(err);
                }
            }
        });
    }
    function get(roomId, callback, error) {
        console.log("룸아이디"+roomId)
        $.ajax({
            type: "get",
            url: "/chat/room/" + roomId,
            async: true,
            success: function (result) {
                if (callback){
                    callback(result);
                }
            },
            error: function (xhr, status, err) {
                if (error){
                    error(err);
                }
            }
        });
    }

    return {
        deleteRoom:deleteRoom,
        findAllRoom : findAllRoom,
        createRoom : createRoom,
        get:get
    };





})();