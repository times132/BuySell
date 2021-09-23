var adminService = (function () {

    function roleList(callback, error) {
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