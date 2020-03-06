<%@ taglib prefix="v-on" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Chatting room</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

</head>
<body>
        <div class="col-md-6">
            <label id="title" value></label>
        </div>
        <div class="col-md-6 text-right">
            <a class="btn btn-info btn-sm" href="/chat/room">채팅방 리스트</a>
            <button id='deleteBtn' class='btn float-right'>채팅그만두기</button>
        </div>
        <div id="scrollDiv" style="overflow:auto; width:800px; height:350px;">
            <ul class="messageList" >

            </ul>
        </div>

        <div class="input-group">
            <div class="input-group-prepend">
                <label class="input-group-text">메시지</label>
            </div>
            <input style="height: 50px; width:600px;" type="text" class="form-control" id="message">
            <span class="input-group-append">
            <button class="btn btn-primary" type="button" id="sending">전송</button>

            </span>
    </div>


<!-- JavaScript -->
<script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/chat.js"></script>
<script src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/2.3.3-1/stomp.min.js"></script>
<script>


    var sock = new SockJS("/ws-stomp");
    var ws = Stomp.over(sock);
    var reconnect = 0;
    var roomId= localStorage.getItem('wschat.roomId');;
    var sender= localStorage.getItem('wschat.sender');;
    var createdDate = new Date();
    var message =  '';
    var messages = [];

//최초시작시 세팅
    init();
    var messageUL = $(".messageList");
    function init() {
        // 채팅룸 출력
        chatService.findAllMessages(roomId,function (data) {
            var str = "";
            console.log("FIND ALL MESSAGE---------------------------------");
            if (data == null || data.length == 0) {
                return;
            }
            for (var i = 0, len = data.length || 0; i < len; i++) {
                str += "<div class='header'><strong id='sender' class='primary-font'>" + data[i].sender + "</strong>";
                str += "<small class='float-right text-muted'>" + chatService.displayTime(data[i].createdDate) + "</small></div>";
                str += "<span class='header'>" +   data[i].message ;
                str += "<span>";
                str += "</div>"
            }

            messageUL.html(str);

        });

    }
    chatService.get(this.roomId, function (room){
        var roomArray = Object.values(room);
        room_name = roomArray[0].roomName;
        console.log(room_name);
    });


    $(document).on("click", "#sending", function(){

        this.message = document.getElementById("message").value;
        console.log("SEND MESSAGE"+this.message);
        ws.send("/pub/chat/message", {}, JSON.stringify({type:'TALK', roomId:roomId, sender:sender, message:this.message, createdDate:this.createdDate}));
        this.message = '';
        document.getElementById("message").value='';
        messageUL.scrollTop = messageUL.scrollHeight;
        init();
    });

    function recvMessage(recv) {
        this.messages.push({"type":recv.type,"sender":recv.sender,"message":recv.message,"createdDate":recv.createdDate})
        init();
    }

    //삭제
    $(document).on("click", "#deleteBtn", function(){
        ws.send("/pub/chat/message", {}, JSON.stringify({type:'QUIT', roomId:roomId, sender:sender, message:this.message, createdDate:this.createdDate}));
        init();
        chatService.deleteRoom(roomId, function (result) {
            alert("방에서 더이상대화가 불가합니다.");
            this.quitRoom();
        });

    });
    function  quitRoom() {
        location.href="/chat/room";
    }
    //연결
    function connect(){
        // pub/sub event
        ws.connect({}, function (frame) {
            ws.subscribe("/sub/chat/room/" + roomId, function (message) {
                var recv = JSON.parse(message.body);
                recvMessage(recv);
            });

        }, function (error) {
            if (reconnect++ <= 5) {
                setTimeout(function () {
                    console.log("connection reconnect");
                    sock = new SockJS("/ws-stomp");
                    ws = Stomp.over(sock);
                    connect();
                }, 10 * 1000);
            }
        });
    }
    connect();
</script>
</body>
</html>