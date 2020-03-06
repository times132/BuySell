<%@ taglib prefix="v-on" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Chatting room</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

</head>
<body>
<div class="container" id="app" >
    <div class="row">
        <div class="col-md-6">
            <h1>{{roomName}}</h1>
        </div>
        <div class="col-md-6 text-right">
            <a class="btn btn-info btn-sm" href="/chat/room">채팅방 리스트</a>
            <button class="btn btn-primary" type="button" @click="stopChat">채팅그만두기</button>
        </div>
    </div>
    <div id="scrollDiv" style="overflow:auto; width:800px; height:350px;">
        <ul class="list-group">
            <li class="list-group-item" v-for="(value,name) in messages">
            [{{displaytime(value.createdDate)}}]  {{value.sender}}-{{value.message}}
            </li>
        </ul>
    </div>
    <div class="input-group">
        <div class="input-group-prepend">
            <label class="input-group-text">메시지</label>
        </div>
        <input style="height: 50px; width:800px;" type="text" class="form-control" v-model="message" v-on:keypress.enter="sendMessage">
        <span class="input-group-append">
            <button class="btn btn-primary" type="button" @click="sendMessage">전송</button>
        </span>
    </div>
</div>

<!-- JavaScript -->
<script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
<script src="/webjars/axios/0.17.1/dist/axios.min.js"></script>
<script src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/2.3.3-1/stomp.min.js"></script>
<script>
    //alert(document.title);
    // websocket &amp; stomp initialize
    var objDiv = document.getElementById("scrollDiv");
    var sock = new SockJS("/ws-stomp");
    var ws = Stomp.over(sock);
    var reconnect = 0;
    // vue.js
    var vm = new Vue({
        el: '#app',
        data: {
            roomId: '',
            room: {},
            sender: '',
            createdDate: new Date(),
            message: '',
            messages: []
        },
        created() {
            this.roomId = localStorage.getItem('wschat.roomId');
            this.sender = localStorage.getItem('wschat.sender');
            this.roomName= localStorage.getItem('wschat.roomName');
            this.findRoom();
            this.findMessages();
            objDiv.scrollTop = objDiv.scrollHeight;
        },
        methods: {
            findRoom: function() {
                axios.get('/chat/room/'+this.roomId).then(response => { this.room = response.data; });
            },
            findMessages: function(){
                axios.get('/chat/messages/'+this.roomId).then(response => {
                    console.log(response.data);
                    this.messages = response.data; });
            },
            sendMessage: function() {
                ws.send("/pub/chat/message", {}, JSON.stringify({type:'TALK', roomId:this.roomId, sender:this.sender, message:this.message, createdDate:this.createdDate}));
                this.message = '';
                // 채팅창 스크롤 바닥 유지
                objDiv.scrollTop = objDiv.scrollHeight;

            },
            recvMessage: function(recv) {
                this.messages.push({"type":recv.type,"sender":recv.sender,"message":recv.message,"createdDate":recv.createdDate})
            },
            stopChat:function () {
                ws.send("/pub/chat/message", {}, JSON.stringify({type:'QUIT', roomId:this.roomId, sender:this.sender, message:this.message, createdDate:this.createdDate}));
                axios.get('/chat/room/stop/'+this.roomId)
                    .then(
                        response => {
                            alert("방에서 더이상대화가 불가합니다.");
                            this.room_name = '';
                            this.quitRoom();
                        }
                    )
                    .catch( response => { alert("채팅 그만두기에 실패하였습니다."); } );
            },
            quitRoom:function () {
                location.href="/chat/room";
            }
        }
    });

    function displaytime(timeValue) {
        var dateObj = new Date(timeValue);

        var mm = dateObj.getMonth() + 1;
        var dd = dateObj.getDate();

        var hh = dateObj.getHours();
        var mi = dateObj.getMinutes();

        return [(mm > 9 ? '' : '0') + mm, '월', (dd > 9 ? '' : '0') + dd + "일 " + (hh > 12 ? '오후' : '오전') + hh, ':', (mi > 9 ? '' : '0') + mi].join('');
    }

    function connect() {
        // pub/sub event
        ws.connect({}, function(frame) {
            ws.subscribe("/sub/chat/room/"+vm.$data.roomId, function(message) {
                var recv = JSON.parse(message.body);
                vm.recvMessage(recv);
            });
            // ws.send("/pub/chat/message", {}, JSON.stringify({type:'ENTER', roomId:vm.$data.roomId, sender:vm.$data.sender}));
        }, function(error) {
            if(reconnect++ <= 5) {
                setTimeout(function() {
                    console.log("connection reconnect");
                    sock = new SockJS("/ws-stomp");
                    ws = Stomp.over(sock);
                    connect();
                },10*1000);
            }
        });
    }
    connect();
</script>
</body>
</html>