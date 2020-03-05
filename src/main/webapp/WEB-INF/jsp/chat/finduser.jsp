<%@ taglib prefix="v-on" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>회원 찾기</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

</head>
<body>
<h1>회원 찾기</h1>


<div class="container" id="sel">

    <div class="input-group">
        닉네임 : <input type="text" id="nickName" value="${txt}"/>
        <span><button type="button" @click="findAllUser">조회 </button></span> <br><br>


    </div>

    <ul class="list-group" style="overflow:auto; width:300px; height:400px;">
        <li class="list-group-item list-group-item-action" v-for="item in userLists" v-on:click="chooseUser(item.username)">
            {{item.username}}
        </li>
    </ul>

</div>
<script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
<script src="/webjars/axios/0.17.1/dist/axios.min.js"></script>
<script>
    var vm = new Vue({
        el: '#sel',
        data: {
            user_name : '',
            userLists: [
            ]

        },
        created() {

            this.findAllUser();

        },
        methods: {
            findAllUser: function() {
                axios.get('/chat/userList/').then(response => { this.userLists = response.data; });
                console.log("findAllUser");
            },
            chooseUser: function(username) {
                            opener.document.getElementById("parentText").value = username;
                            // 창을 닫음
                            window.close();
            }

        }

    });
</script>
</body>
</html>