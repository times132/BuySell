console.log("ADMIN module****************************");

var adminService = (function () {

    function roleList(callback, error) {
        console.log("GET ROLE LIST");
        $.getJSON("/admin/roleList", function (data) {
            if (callback){
                callback(data);
            }
        })
            .fail(function (xhr, status, err) {
            if (error){
                error(err);
            }
        });

    }


    return {
        roleList : roleList
    };





})();