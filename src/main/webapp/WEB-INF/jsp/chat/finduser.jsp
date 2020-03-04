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
<h1>Popup!!!!!!!!!!!!!!!!!!!</h1>

닉네임 : <input type="text" id="childText" value="${txt}"/>
<button id=submit type="button" name="button" onclick="sendToParent();">입력</button>

</form>


<script type="text/javascript">
    function sendToParent(){

                var txt = document.getElementById("childText").value;
                // opener 를 이용해 부모 window 객체에 접근할 수 있습니다.
                // 부모에게서 전달받은 값에 추가로 문자열을 더해서 다시 부모의 receiveFromChild 라는 id를 갖는
                // 태그요소에 value 값을 바꾸어 주는 작업입니다.
                window.opener.postMessage(txt, "http://localhost:8050/chat/room");
                opener.document.getElementById("parentText").value = txt;

                // 창을 닫음
                window.close();
    }

</script>


</body>
</html>