var replyService = (function () {
    function add(reply, callback, error) {
        console.log(reply);

        $.ajax({
            type: 'post',
            url: "/replies/new",
            data: JSON.stringify(reply),
            contentType: "application/json; charset=utf-8",
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

    function remove(rid, replyer, callback, error) {
        $.ajax({
            type: "delete",
            url: "/replies/" + rid,
            data: JSON.stringify({rid: rid, replyer: replyer}),
            contentType: "application/json; charset=utf-8",
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

    function update(reply, callback, error) {
        $.ajax({
            type: "put",
            url: "/replies/" + reply.rid,
            data: JSON.stringify(reply),
            contentType: "application/json; charset=utf-8",
            success: function (result, status, xhr) {
                if (callback){
                    callback(result);
                }
            },
            error: function (xhr, status, er) {
                if (error){
                    error(er);
                }
            }
        });
    }

    function getList(param, callback, error) {
        var bid = param.bid;
        var page = param.page || 1;

        $.getJSON("/replies/pages/" + bid + "/" + page, function (data) {
            if (callback){

                callback(data);
            }
        }).fail(function (xhr, status, err) {
            if (error){
                error(err);
            }
        });
    }

    function get(rid, callback, error) {
        $.ajax({
            type: "get",
            url: "/replies/" + rid,
            async: false,
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
        add: add,
        getList: getList,
        get: get,
        update: update,
        remove: remove,
    };
})();