var boardService = (function () {
    function checkLike(bid, callback, error) {
        $.ajax({
            type: 'get',
            url: "/board/like/"+bid,

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
            url: "/board/like/"+bid,
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
        $.ajax({
            type: 'delete',
            url: "/board/like/"+bid,
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


    function getCategoryItems(id, callback, error) {
        $.ajax({
            type:'get',
            url: "/board/category/"+id,
            data: id,
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

    function getItemList(itemName, callback, error) {
        $.getJSON("/board/item/"+itemName, function (data) {
            if (callback){
                callback(data);
            }
        }).fail(function (xhr, status, err) {
            if (error){
                error(err);
            }
        });
    }


    return {
        checkLike : checkLike,
        addLike : addLike,
        deleteLike :deleteLike,
        getCategoryItems : getCategoryItems,
        getItemList: getItemList
    };
})();