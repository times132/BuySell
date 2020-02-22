<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <link href="/resources/css/test.css" rel="stylesheet">

    <script src="/webjars/jquery/dist/jquery.min.js"></script>
    <script type="text/javascript" src="/resources/js/reply.js"></script>
</head>
<body>

    <div>
        <label>제목</label>
        <input name="bid" value="<c:out value="${boardDto.title}"/>" readonly="readonly"/>
    </div>
    <div>
        <label>분류</label>
        <input name="bid" value="<c:out value="${boardDto.btype}"/>" readonly="readonly"/>
    </div>
    <div>
        <label>작성일</label>
        <input name="createddate" value="<javatime:format pattern="yyyy-MM-dd" value="${boardDto.createdDate}"/>" readonly="readonly"/>
    </div>

    <div>
        <label>판매자</label>
        <input name="writer" value="<c:out value="${boardDto.writer}"/>" readonly="readonly"/>
    </div>
    <div>
        <label>내용</label>
        <input name="content" value="<c:out value="${boardDto.content}"/>" readonly="readonly"/>
    </div>
    <div>
        <label>가격</label>
        <input name="price" value="<c:out value="${boardDto.price}"/>" readonly="readonly"/>
    </div>

    <button data-oper="modify">수정</button>
    <button data-oper="remove">삭제</button>
    <button data-oper="list">목록</button>

    <div class="reply-add">
        <label>댓글</label><br/>
        <sec:authentication property="principal" var="pinfo"/>
        <input type="text" name="reply" placeholder="내용"/>
        <input type="text" name="replyer" value="<c:out value="${userList.nickname}"/>" readonly="readonly"/>

        <button id="addReplyBtn" class="btn float-right">등록</button>

    </div>

    <div class="reply-body">
        <ul class="replyList">

        </ul>
    </div>

    <div class="reply-footer">

    </div>

    <form id="operForm" action="/board/modify" method="get">
        <input type="hidden" id="bid" name="bid" value="<c:out value="${boardDto.bid}"/>">
        <input type="hidden" name="page" value="<c:out value="${cri.page}"/>">
        <input type="hidden" name="type" value="<c:out value="${cri.type}"/>">
        <input type="hidden" name="keyword" value="<c:out value="${cri.keyword}"/>">
    </form>
</body>

<script>
    $(document).ready(function () {

        var operForm = $("#operForm");

        $("button[data-oper='remove']").on("click", function (e) {
            operForm.attr("action", "/board/remove").attr("method", "post").submit();
        })

        $("button[data-oper='modify']").on("click", function (e) {
            operForm.attr("action", "/board/modify").submit();
        });

        $("button[data-oper='list']").on("click", function (e) {
            operForm.find("#bid").remove();
            operForm.attr("action", "/board");
            operForm.submit();
        });
    });
</script>
<script>
    var bidValue = "<c:out value="${boardDto.bid}"/>";
    var replyUL = $(".replyList");

    var replyer = null;
    <sec:authorize access="isAuthenticated()">
        <sec:authentication property="principal" var="userinfo"/>;
        replyer = '${userinfo.username}';
    </sec:authorize>

    showList(1);

    function showList(page) {

        replyService.getList({bid: bidValue, page: page || 1}, function (data) {
            // console.log(data);
            if (page == -1){
                pageNum = 1;
                showList(1);
                return;
            }

            var str = "";

            if (data == null || data.length == 0){
                return;
            }
            for (var i = 0, len = data.content.length || 0; i < len; i++){
                str += "<li class='replyli' data-rid='" + data.content[i].rid + "'>";
                str += "<div class='header'><strong id='replyer' class='primary-font'>" + data.content[i].replyer + "</strong>";
                str += "<small class='float-right text-muted'>" + replyService.displayTime(data.content[i].createdDate) + "</small></div>";
                str += "<p id='reply_" + data.content[i].rid + "'>" + data.content[i].reply + "</p>";
                str += "<div>";
                str += ('${userinfo.username}' == data.content[i].replyer ?
                    "<button id='modReplyBtn' class='btn float-right' >수정</button>" +
                    "<button id='removeReplyBtn' class='btn float-right'>삭제</button>" :
                    '');
                str += "</li>";
            }

            replyUL.html(str);

            showReplyPage(data.totalElements, data.size); //21, 5
        });
    }

    var pageNum = 1;
    var replyPageFooter = $(".reply-footer");

    function showReplyPage(replyCnt, size) { // size = 한 페이지에 보이는 댓글 수

        var endPage = Math.ceil(pageNum / 3.0) * 3; //현재 블럭 끝 번호
        var startPage = endPage - 2; // 현재 블럭 시작 번호

        var prev = startPage > 1;
        var next = false;

        if (endPage * size >= replyCnt){ // 현재 블럭 끝 번호 * 한 페이지에 보이는 댓글 수가 전체 댓글 수 보다 많을시
            endPage = Math.ceil(replyCnt / size); // 블럭 끝 번호를 재설정 21/5 = 5
        }
        
        if (endPage * size < replyCnt){
            next = true;
        }

        var str = "<ul class='pagination float-right'>";

        if (prev){
            str += "<li class='page-item'><a class='page-link' href='" + (startPage - 1) + "'>이전</a></li>";
        }

        for (var i = startPage; i <= endPage; i++){
            var active = pageNum == i ? "active" : "";
            str += "<li class='page-item " + active + "'><a class='page-link' href='" + i + "'>" + i + "</a></li>";
        }

        if (next){
            str += "<li class='page-item'><a class='page-link' href='" + (endPage + 1) + "'>다음</a></li>";
        }

        str += "</ul></div>";

        replyPageFooter.html(str);
    }

    replyPageFooter.on("click", "li a", function (e) {
        e.preventDefault();
        console.log("page click");

        var targetPageNum = $(this).attr("href");

        pageNum = targetPageNum;
        showList(pageNum);
    });

    var replyadd = $(".reply-add");
    var inputReply = replyadd.find("input[name='reply']");
    var inputReplyer = replyadd.find("input[name='replyer']").val(replyer);
    var reply1 = "";
    var replyer1 = "";

    $("#addReplyBtn").on("click", function (e) {

        var reply = {
            reply: inputReply.val(),
            replyer: inputReplyer.val(),
            bid: bidValue
        };

        replyService.add(reply, function (result) {
            alert(result);
            inputReply.val('');
            showList(-1);
        });
    });

    replyUL.on("click", "button", function () {
        var rid = $(this).closest("li").data("rid");

        replyService.get(rid, function (reply) {
            reply1 = reply.reply;
            replyer1 = reply.replyer;
        });
    });

    $(document).on("click", "#modReplyBtn", function(){
        var rid = $(this).closest("li").data("rid");

        var str = "";

        str += "<input type='text' name='reply' value='" + reply1 + "'/>";

        $("#reply_"+rid).html(str);
    });

    $(document).on("click", "#removeReplyBtn", function(){
        var rid = $(this).closest("li").data("rid");
        var originalReplyer = replyer1;

        if (replyer != originalReplyer){
            alert("자신의 댓글만 삭제가 가능합니다");
            return;
        }

        replyService.remove(rid, originalReplyer, function (result) {
            alert(result);
            showList(pageNum);
        });
    });



</script>
</html>
