<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chatting room</title>
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <link href="/resources/css/chat.css" rel="stylesheet">

    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/2.3.3-1/stomp.min.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
    <script type="text/javascript" src="/resources/js/chat.js"></script>
</head>
<body>
<div class="container">
    <h3 class=" text-center">GIVEANDTAKE</h3>
    <div class="chatting">
        <div class="inbox_all">
            <div class="inbox_people">
                <div class="headind_srch">
                    <div class="recent_heading">
                        <h4>Recent</h4>
                        <a class="gobackhome" href="/">홈으로 이동</a><br>
                    </div>
                    <div class="srch_bar">
                        <div class="stylish-input-group">
                            <input  type="text" class="search-bar" id="receiver" value="${nickName}" placeholder="닉네임(대화상대)을 입력해주세요"><br>
                            <span class="input-group-addon">
                            <button type="button" id="creating">채팅요청</button>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="inbox_chat">
                    <div class="chat_list">

                    </div>
                </div>
            </div>
            <div class="messages">
                <div>
                    <button class="delete" id='deleteBtn'  >채팅그만두기</button>
                </div>
                <div class="msg_history">


                </div>


                <div class="type_msg">
                    <div class="input_msg_write">
                        <input type="text" class="write_msg" placeholder="Type a message" id="message" />
                        <button class="msg_send_btn" type="button" id="sending"><i class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
                    </div>
                </div>
            </div>
        </div>




    </div>
</div>

</body>
<script>
    init();
    $('.messages').hide();
    var chatUL = $(".inbox_chat");
    var me = null;
    <sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="userinfo"/>;
    sender = '${userinfo.username}';
    </sec:authorize>
    function init() {
        // 채팅룸 출력
        chatService.findAllRoom(function (data) {
            var str = "";
            if (data == null || data.length == 0) {
                return;
            }
            console.log("data" + data);
            for (var i = 0, len = data.length || 0; i < len; i++) {
                str += "<div class='chat_list'>"
                str += "<ul>"
                str += "<li class='chat_li' data-rid='" + data[i].roomId + "'>";
                str += "<div class='chat_people'>"
                str += "<div class='chat_ib'>"
                str += ( sender === data[i].receiver ?
                    "<h5>"+ data[i].request +"<div class='msgCnt'>"+ data[i].rqMsgCount +"</div>"
                    +"<span class='chat_date'>"+ chatService.displayTime(data[i].msgDate)+"</span></h5>"
                    :"<h5>"+ data[i].receiver +"<div class='msgCnt'>"+ data[i].rcMsgCount + "</div>"
                    +"<span class='chat_date'>"+ chatService.displayTime(data[i].msgDate)+"</span></h5>");
                str += "<p>" + data[i].roomName+ "</p>";   //메시지 내용이 들어가면 좋을 것 같음
                str += "<button id='enterBtn' class='btn float-right' >입장</button>";
                str += "</div></div></div></li></ul></div>";

            }

            chatUL.html(str);


        });

    }
    var roomId='';

    $("#creating").click(function() {
        var inputRN = document.getElementById("room_name").value
        var inputreceiver = document.getElementById("receiver").value
        if (inputreceiver == "") {
            alert("대화상대를 입력하지 않았습니다.");
            return;
        }
        else {
            var room = {
                roomName : '대화 내용이 없습니다.',
                receiver : inputreceiver,
                request : sender,
                rcMsgCount : 0,
                rqMsgCount : 0,
                msgDate : new Date()

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


        alert("입장");
        if(sender != "") {
            location.href="/chat/room/enter/"+room_id;
        }

    });

</script>

<script>
        var sock = new SockJS("/ws-stomp");
        var ws = Stomp.over(sock);
        var roomId = "${roomId}";
        var reconnect = 0;
        var createdDate = new Date();
        var message = '';
        var messages = [];

        //최초시작시 세팅

        var messageDIV = $(".msg_history");
        init2();
        function init2() {
            // 채팅룸 출력
            console.log("######################################"+roomId);
            if(roomId==""){
                return;
            }
            chatService.findAllMessages(roomId, function (data) {
                init();
                $('.messages').show();
                var str = "";
                if (data == null || data.length == 0) {
                    return;

                }
                for (var i = 0, len = data.length || 0; i < len; i++) {
                    str += ( sender === data[i].sender ?
                        "<div class='outgoing_msg'>\n" +
                        "<div class='sent_msg'>\n" +
                        "<strong id='sender' class='primary-font'>" + data[i].sender + "</strong>" +
                        "<p>"+data[i].message+"</p>\n"+
                        "<span class='chat_date'>"+ chatService.displayTime(data[i].createdDate)+"   "+"</span></h5>"+
                        "</div></div>"

                        :"<strong id='sender' class='primary-font'>" + data[i].sender + "</strong>" +
                        "<div class='incoming_msg'>\n" +
                        "<div class='received_with_msg'>"+
                        "<p>"+data[i].message+"</p>\n"+
                        "<span class='chat_date'>"+ chatService.displayTime(data[i].createdDate)+"   "+"</span></h5>"+
                        "</div></div>");
                }

                messageDIV.html(str);

            });

        }

        $(document).on("click", "#sending", function () {

            this.message = document.getElementById("message").value;
            console.log("SEND MESSAGE" + this.message);
            ws.send("/pub/chat/message", {}, JSON.stringify({
                type: 'TALK',
                roomId: roomId,
                sender: sender,
                message: this.message,
                createdDate: this.createdDate
            }));
            this.message = '';
            document.getElementById("message").value = '';

            init();
            init2();
        });

        function recvMessage(recv) {
            this.messages.push({
                "type": recv.type,
                "sender": recv.sender,
                "message": recv.message,
                "createdDate": recv.createdDate
            })
            init();
            init2();
        }

        //삭제
        $(document).on("click", "#deleteBtn", function () {
            ws.send("/pub/chat/message", {}, JSON.stringify({
                type: 'QUIT',
                roomId: roomId,
                sender: sender,
                message: this.message,
                createdDate: this.createdDate
            }));

            alert("더이상 방에서 대화가 불가합니다.")
            location.href="/chat/room/stop/"+roomId;

        });

        function quitRoom() {
            location.href = "/chat/room";
        }

        //연결
        function connect() {
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
</html>