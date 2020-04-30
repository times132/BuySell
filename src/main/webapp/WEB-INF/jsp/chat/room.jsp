<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>
<head>
    <title>Chatting room</title>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" type="text/css" rel="stylesheet">
    <link href="/resources/css/chat.css" rel="stylesheet">
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">

    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
    <script src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/2.3.3-1/stomp.min.js"></script>
    <script>
        $(document).ready(function () {
            var token =  '${_csrf.token}';
            var header = '${_csrf.headerName}';

            $.ajaxSetup({
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                }
            });
        });
    </script>
</head>
<body>
<sec:authentication property="principal.user" var="userinfo"/>

<%@include file="../include/navbar.jsp"%>
<BR>
<div class="container">
    <div class="chatting">
        <div class="inbox-all">
            <div class="inbox-people">
                <div class="heading">
                    <div class="recent-heading">
                        <span><img class='img-thumbnail' src='/display?fileName=${userinfo.id}/profile/${userinfo.profileImage}' onerror="this.src = '/resources/image/profile.png'"/>
                        <br><h4>ME : ${userinfo.nickname}</h4></span>
                    </div>
                    <div class="search-bar">
                        <div class="stylish-input-group">
                            <input type="text" id="receiver"  placeholder="Search" >
                            <span class="input-group-addon">
                                    <button id=creating type="button"> <i class="fa fa-search" aria-hidden="true"></i> </button>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="inbox-chat">
                        <div class="chat-list">

                        </div>
                </div>
            </div>
            <div class="messages">
                <div class="row msgHeader">
                    <div class='col-md-12'>
                        <button id='deleteBtn' class='btn float-right'><img class='btn-img' src='/resources/image/delete.png'></button>
                        <button class='btn float-right' onClick="self.location='/chat/room';"><img class='btn-img' src='/resources/image/enter.png'></button>
                    </div>
                </div>

                <div class="msg-history">

                </div>


                <div class="type-msg">
                    <div class="input-msg-write">
                        <input type="text" class="write_msg" placeholder="Type a message" id="message" />
                        <button class="msg-send-btn" type="button" id="sending"><i class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
                    </div>
                </div>
            </div>
            </div>
        </div>
    </div>
<script type="text/javascript" src="/resources/js/chat.js"></script>
<script>
    init();
    $('.messages').hide();
    var chatUL = $(".chat-list");
    <sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="userinfo"/>;
    var sender = '${userinfo.user.nickname}';
    var senderId = '${userinfo.user.id}';
    </sec:authorize>
    function init() {
        // 채팅룸 출력
        chatService.findAllRoom(function (data) {
            var str = "";
            if (data == null || data.length == 0) {
                return;
            }
            for (var i = 0, len = data.length || 0; i < len; i++) {
                console.log(data[i])
                str += "<div class='chat' ><ul class= 'a'>"
                str += "<li class='chat-li'  data-rid='" + data[i].roomId + "'>";
                str += "<div class='chat-people' id='enterBtn' >"
                str += "<div class='chat-img'> "
                if(data[i].users.length == 1){
                    str += "<img src='/resources/image/profile.png'>" + "</div>";
                }
                else{
                    for (var a = 0, length = data[i].users.length || 0; a < length; a++) {

                    if (sender != data[i].users[a].user.nickname) {
                            str += "<img src='/display?fileName=" + data[i].users[a].user.id
                            +"/profile/s_" + data[i].users[a].user.profileImage
                            +"' onerror=\"this.src='/resources/image/profile.png'\"/>"
                            + "</div>"
                        str += "<div class='chat-ib'>"
                        str += (data[i].users[a].msgCount === 0 ? "<h5 id='enterBtn'>" + data[i].users[a].user.nickname+"</h5>"
                            :"<h5 id='enterBtn'>" + data[i].users[a].user.nickname+ "<span>"+"&nbsp"+ data[i].users[a].msgCount +"&nbsp"+"</span></h5>");
                        }
                    }}
                    str += (data[i].recentMsg.startsWith( '{"bid":' ) && data[i].recentMsg.endsWith( '}' ) ?
                        '<p>관심있는 상품을 보냈습니다.</p>' :"<p>" + data[i].recentMsg+ "</p>" );
                    str += "<div class='chat-date'>"+ chatService.displayTime(data[i].msgDate)+"</div></div>"
                    str += "</div></li></ul></div>";
            }
            chatUL.html(str);
        });

    }
    var roomId='';
    $("#creating").click(function() {
        var inputNick = document.getElementById("receiver").value
        if (inputNick == "") {
            alert("대화상대를 입력하지 않았습니다.");
            return;
        }
        else {
                    var nickname = {
                        nickname : inputNick
                    };

                    chatService.createRoom(nickname, function (result) {
                        if(result == "noNickname") {
                            alert("존재하는 닉네임이 없습니다.");
                            document.getElementById("receiver").value = "";
                            init();
                        }
                        else{
                            alert("채팅방으로 이동합니다.");
                            location.href = "/chat/room/enter/" + result;
                        }
                    });
        }
    });

    $(document).on("click", "#enterBtn", function(){
        var roomId = $(this).closest("li").data("rid");
        if(sender != "") {
            location.href="/chat/room/enter/"+roomId;
        }

    });

</script>

<script>
        var sock = new SockJS("/ws-stomp");
        var ws = Stomp.over(sock);
        var roomId = "${roomId}";
        var reconnect = 0;
        var message = '';
        //최초시작시 세팅
        var messageDIV = $(".msg-history");
        init2();
        function init2() {
            // 채팅룸 출력
            if(roomId==""){
                return;
            }
            chatService.findAllMessages(roomId, function (data) {
                init();
                console.log(data);
                $('.messages').show();
                var str = "";
                if (data == null || data.length == 0) {
                    return;

                }

                for (var i = 0, len = data.length || 0; i < len; i++) {

                    if (data[i].type == "BOARD"){  //게시판내용인 경우
                        const board = JSON.parse(data[i].message);
                        str+=(senderId === data[i].senderId ?
                            "<div class='outgoing-msg'><div class = 'sent-msg'>" :
                            "<div class='incoming-msg'><div class='received-with-msg'>");
                        str +=
                            "<div class='mb-3 card'>"+
                            "<div class='card-header'><h5 class='mb-0'>"+board.title+"</h5>"+
                            "<span class='float-right'>"+ board.createdDate.date.year +"."+  board.createdDate.date.month +"."+ board.createdDate.date.day +"</span></div>" +
                            "<div class='card-body'>"+
                            "<strong  class='float-right'>" + ""+ board.price + "원" + "</strong></span>"+
                            "<h6 class='card-text'>"+board.content + "..." +"</h6>"+
                            "<a  href='/board/read?bid="+ board.bid +"' id='showDetail' class='btn btn-primary float-right'>상세정보보기 >></a>"+
                            "</div>"+
                            "</div></div></div>";

                    }

                    else if(senderId == data[i].senderId) {
                       str += "<div class='outgoing-msg'>\n" +
                       "<div class='sent-msg'>\n" +
                       "<strong id='sender' class='primary-font'>" + data[i].sender + "</strong>" +
                        "<p>" + data[i].message + "</p>\n" +
                       "<span class='chat-date'>" + chatService.displayTime(data[i].createdDate)+ "</span></h5>" +
                       "</div></div>"
                   }
                   else{
                       str +=
                        "<div class='incoming-msg'>\n" +
                        "<div class='incoming-msg-img'>";

                       if("[알림]" == data[i].sender){
                           str += "<img src='/resources/image/info2.png'>"
                       }

                       else{
                           //사용자가 1명인 경우
                           if (data[i].chatRoom.users.length == 1){
                               str += "<img src='/resources/image/profile.png'>"
                           }
                           else {
                               for (var a = 0, length = data[i].chatRoom.users.length || 0; a < length; a++) {
                                   if (data[i].sender == data[i].chatRoom.users[a].user.nickname) {
                                       str += "<img src='/display?fileName=" + data[i].chatRoom.users[a].user.id +
                                           "/profile/s_" + data[i].chatRoom.users[a].user.profileImage +
                                           "' onerror=\"this.src = '/resources/image/profile.png'\"/>";
                                       break;
                                   }
                               }
                           }
                       }
                        str +=
                        "</div>"+
                        "<strong id='sender' class='primary-font'>" + data[i].sender + "</strong>" +
                        "<div class='received-with-msg'>"+
                        "<p>"+data[i].message+"</p>\n"+
                        "<span class='chat-date'>"+ chatService.displayTime(data[i].createdDate)+"   "+"</span></h5>"+
                        "</div></div>"
                   }
                }
                messageDIV.html(str);
            });

        }

        $(document).on("click", "#sending", function () {
            this.message = document.getElementById("message").value;
            ws.send("/app/chat/message", {}, JSON.stringify({
                type: 'TALK',
                roomId: roomId,
                sender: sender,
                senderId : senderId,
                message: this.message,

            }));
            this.message = '';
            document.getElementById("message").value = '';
            init();
            init2();
        });


        //삭제
        $(document).on("click", "#deleteBtn", function () {
            var check = confirm("채팅방을 삭제하시겠습니까? 삭제하면 더이상 대화가 불가합니다.");
            if(check) {
                ws.send("/app/chat/message", {}, JSON.stringify({
                    type: 'QUIT',
                    roomId: roomId,
                    sender: sender,
                    message: this.message,
                    createdDate: this.createdDate
                }));
                alert("삭제가 완료되었습니다.");
                init();
                location.href = "/chat/room/stop/" + roomId;
            }
        });


        //연결
        function connect() {
            // pub/sub event
            ws.connect({}, function (frame) {
                ws.subscribe("/queue/chat/room/" + roomId, function (message) {
                    init();
                    init2();
                });
            ws.subscribe("/user/queue/chat/room", function (message) {
                init();
                init2();
             });
            }
            , function (error) {
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