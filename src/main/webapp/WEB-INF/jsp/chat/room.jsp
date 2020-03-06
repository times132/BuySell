<%@ taglib prefix="v-on" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>채팅방</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
    <script type="text/javascript" src="/resources/js/chat.js"></script>
</head>
<body>
    <div class="row">
        <div class="col-md-12">
            <h3>대화 목록</h3>
        </div>
    </div>
    <a class="btn btn-info btn-sm" href="/">홈으로 이동</a><br><br>
        <input style="height: 20px; width:200px;" type="text" id="receiver" value="${nickName}" placeholder="닉네임(대화상대)을 입력해주세요"/>

        <input style="height: 20px; width:400px;"  type="text" id="room_name" name="room_name"  placeholder="방이름">
        <span>
            <button class="btn btn-primary" id="creating" type="button">채팅방 개설</button>
        </span>
<%--        채팅방목록--%>



        <ul class="chatList">

        </ul>


    </div>

</div>
<script>
    init();
    var room_name = document.getElementById("room_name").value;
    var chatUL = $(".chatList");
    function init() {
                // 채팅룸 출력
                chatService.findAllRoom(function (data) {
                    var str = "";
                    console.log("findfindfind");
                    if (data == null || data.length == 0) {
                        return;
                    }
            for (var i = 0, len = data.length || 0; i < len; i++) {
                str += "<li class='chatli' type='hidden' data-rid='" + data[i].roomId + "'>";
                str +=
                    "<button id='enterBtn' class='btn float-right' >입장</button>"

                str += "<span class='header'><strong id='roomName' class='primary-font'>" +   data[i].roomName + "</strong>";
                str += "<span>";

                str += "</li>";
            }

            chatUL.html(str);


        });

    }
    var roomId='';
    var roomName ='';
    var sender ='';

    $("#creating").click(function() {
        var inputRN = document.getElementById("room_name").value
        var inputreceiver = document.getElementById("receiver").value
        if ("" === inputRN) {
            alert("방 제목을 입력해 주십시요.");
            return;
        }
        else {
            if (inputreceiver == "") {
                alert("대화상대를 입력하지 않았습니다.");
                return;
            }
            var room = {
                roomName : inputRN,
                receiver : inputreceiver,
            };

            chatService.createRoom(room, function (result) {
                   alert(result);
                   document.getElementById("room_name").value="";
                   document.getElementById("receiver").value="";
                   init();
            });



        }
    });

    $(document).on("click", "#enterBtn", function(){
        var room_id = $(this).closest("li").data("rid");

                    <sec:authorize access="isAuthenticated()">
                    <sec:authentication property="principal" var="userinfo"/>;
                    var sender = '${userinfo.username}';

                    </sec:authorize>

              alert("입장");
             if(sender != "") {
                            localStorage.setItem('wschat.sender',sender);
                            localStorage.setItem('wschat.roomId',room_id);
                            location.href="/chat/room/enter/"+room_id;

             }

    });



</script>
</body>
</html>