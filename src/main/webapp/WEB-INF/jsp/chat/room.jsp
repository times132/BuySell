<%@ taglib prefix="v-on" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>채팅방</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <!-- CSS -->
    <%--    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">--%>
    <%--    <style>--%>
    <%--        [v-cloak] {--%>
    <%--            display: none;--%>
    <%--        }--%>
    <%--    </style>--%>
</head>
<body>
<div class="container" id="app" v-cloak>
    <div class="row">
        <div class="col-md-12">
            <h3>채팅방 리스트</h3>
        </div>
    </div>
    <div class="input-group">
        <span class="input-group-prepend">
            <label class="input-group-text">방제목</label>
        </span>
        <input type="text" class="form-control" v-model="room_name" v-on:v-on:keyup.enter="createRoom">
        <span class="input-group-append">
            <button class="btn btn-primary" type="button" @click="createRoom">채팅방 개설</button>
        </span>
    </div>
    <ul class="list-group">
        <li class="list-group-item list-group-item-action" v-for="item in chatrooms" v-bind:key="item.roomId" v-on:click="enterRoom(item.roomId, item.roomName)">
            {{item.roomName}}
        </li>
    </ul>
</div>
<!-- JavaScript -->
<script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
<script src="/webjars/axios/0.17.1/dist/axios.min.js"></script>
<script>
    var vm = new Vue({
        el: '#app',
        data: {
            room_name : '',
            chatrooms: [
            ],
            receiver:''

        },
        created() {
            this.findAllRoom();
        },
        methods: {
            findAllRoom: function() {
                axios.get('/chat/rooms').then(response => { this.chatrooms = response.data; });
            },
            createRoom: function() {
                if("" === this.room_name) {
                    alert("방 제목을 입력해 주십시요.");
                    return;
                } else {
                    var receiver = prompt('대화상대를 입력해 주세요. 존재하지 않는 닉네임일경우 채팅요청에 실패할 수 있으니 유의해주세요.');
                    var params = new URLSearchParams();
                    params.append("roomName",this.room_name);
                    params.append("receiver", receiver.toString())
                    console.log(receiver);
                    axios.post('/chat/room', params)
                        .then(
                            response => {
                                alert("'"+this.room_name+" '방  개설에 성공하였습니다."+receiver+"님과 즐거운 대화나누세요")
                                this.room_name = '';
                                this.findAllRoom();
                            }
                        )
                        .catch( response => { alert("채팅방 개설에 실패하였습니다."); } );
                }
            },
            enterRoom: function(roomId,roomName) {
                <sec:authorize access="isAuthenticated()">
                <sec:authentication property="principal" var="userinfo"/>;
                var sender = '${userinfo.username}';
                </sec:authorize>

                if(sender != "") {
                    localStorage.setItem('wschat.sender',sender);
                    localStorage.setItem('wschat.roomId',roomId);
                    localStorage.setItem('wschat.roomName',roomName);
                    location.href="/chat/room/enter/"+roomId;

                }

            }
        }
    });
</script>
</body>
</html>