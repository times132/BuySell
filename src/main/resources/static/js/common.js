var commonService = (function () {
    function displayTime(timeValue) {

        var today = new Date();
        var dateObj = new Date(timeValue);
        var gap = today.getTime() - dateObj.getTime();
        var str = "";

        if (gap < (1000 * 60 * 60 * 24)){
            var hh = dateObj.getHours();
            var mi = dateObj.getMinutes();
            var ss = dateObj.getSeconds();

            return [(hh > 9 ? '' : '0') + hh, ':', (mi > 9 ? '' : '0') + mi, ':', (ss > 9 ? '' : '0') + ss].join('');
        }else{
            var yy = dateObj.getFullYear();
            var mm = dateObj.getMonth() + 1;
            var dd = dateObj.getDate();

            return [yy, '.', (mm > 9 ? '' : '0') + mm, '.', (dd > 9 ? '' : '0') + dd].join('');
        }
    }

    function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
            results = regex.exec(location.search);
        return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    }

    return{
        displayTime: displayTime,
        getParameterByName: getParameterByName
    }
})();

