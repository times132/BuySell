console.log("reply module");

var replyService = (function () {
    function add(reply, callback, error) {
        console.log("add reply");

        $.ajax({
            type: 'post',
            url: "/replies/new",
            data: JSON.stringify(reply),
            contentType: "application/json charset=utf-8",
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
        $.get("/replies/" + rid + ".json", function (result) {
            if (callback){
                callback(result);
            }
        }).fail(function (xhr, status, err) {
            if (error){
                error(err);
            }
        });
    }

    function displayTime(timeValue) {
        var today = new Date();

        var gap = today.getTime() - timeValue;

        var dateObj = new Date(timeValue);
        var str = "";

        if (gap < (1000 * 60 * 60 * 24)){
            var hh = dateObj.getHours();
            var mi = dateObj.getMinutes();
            var ss = dateObj.getSeconds();

            return [(hh > 9 ? '' : '0') + hh, ":", (mi > 9 ? '' : '0') + mi, ":", (ss > 9 ? '' : '0') + ss].join('');
        }else{
            var yy = dateObj.getFullYear();
            var mm = dateObj.getMonth() + 1;
            var dd = dateObj.getDate();

            return [yy, '/', (mm > 9 ? '' : '0') + mm, '/', (dd > 9 ? '' : '0') + dd].join('');
        }
    }

    return {
        add: add,
        getList: getList,
        get: get,
        displayTime: displayTime
    };
})();