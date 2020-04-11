var boardService = (function () {
    function checkLike(bid, callback, error) {
        console.log("checkList");

        $.ajax({
            type: 'get',
            url: "/board/like/checkLike",
            data: bid,
            success: function (result, status, xhr) {
                if (callback){
                    callback(result);
                }
            },
            error: function (xhr, status, err) {
                if (error){
                    error(err);
                }
            }
        })
    }



    function addLike(bid, callback, error) {
        $.ajax({
            type:'post',
            url: "/board/like/addLike",
            data: bid,
            success: function (deleteResult, status, xhr) {
                if (callback){
                    callback(deleteResult);
                }
            },
            error: function (xhr, status, er) {
                if (error){
                    error(er);
                }
            }
        });
    }

    function deleteLike(bid, callback, error) {
        console.log(bid);
        $.ajax({
            type: 'delete',
            url: "/board/like/deleteLike",
            data: bid,
            success: function (result, status, xhr) {
                if (callback){
                    callback(result);
                }
            },
            error: function (xhr, status, err) {
                if (error){
                    error(err);
                }
            }
        })
    }

    return {
        checkLike : checkLike,
        addLike : addLike,
        deleteLike :deleteLike
    };
})();