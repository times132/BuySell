<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <script src="/webjars/jquery/dist/jquery.min.js"></script>
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

    <div>
        <label>댓글</label><br/>
        <sec:authentication property="principal" var="pinfo"/>
        <input type="text" name="reply" placeholder="내용"/>
        <input type="text" name="replyer" placeholder="작성자"/>
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
<script type="text/javascript" src="/resources/js/reply.js"></script>
<script>
    var bidValue = "<c:out value="${boardDto.bid}"/>";
    var replyUL = $(".replyList");

    showList(1);

    function showList(page) {
        replyService.getList({bid: bidValue, page: page || 1}, function (list) {
            if (page == -1){
                pageNum = Math.ceil(replyCnt/10.0);
                showList(pageNum);
                return;
            }

            var str = "";

            if (list == null || list.length == 0){
                return;
            }
            for (var i = 0, len = list.length || 0; i < len; i++){
                str += "<li data-rid='" + list[i].rid + "'>";
                str += "<div><div class='header'><strong class='primary-font'>" + list[i].replyer + "</strong>";
                str += "<small class='float-right text-muted'>" + replyService.displayTime(list[i].createdDate) + "</small></div>";
                str += "<p>" + list[i].reply + "</p></div></li>";
            }

            replyUL.html(str);

            showReplyPage(replyCnt);
        });
    }

    var pageNum = 1;
    var replyPageFooter = $(".reply-footer");

    function showReplyPage(replyCnt) {
        var endPage = Math.ceil(pageNum / 10.0) * 10; //현재 블럭 끝 번호
        var startPage = endPage - 9; // 현재 블럭 시작 번호

        var prev = startPage > 1;
        var next = false;

        if (endPage * 5 >= replyCnt){ // 현재 블럭 끝 번호 * 한 페이지에 보이는 댓글 수가 전체 댓글 수 보다 많을시
            endPage = Math.ceil(replyCnt / 10.0); // 블럭 끝 번호를 재설정
        }

        if (endPage * 5 < replyCnt){
            next = true;
        }

        var str = "<ul class='pagination float-right'>";

        if (prev){
            str += "<li class='page-item'><a class='page-link' href'" + (startPage - 1) + "'이전</a></li>";
        }

        for (var i = startPage; i <= endPage; i++){
            var active = pageNum == i ? "active" : "";
            str += "<li class='page-item " + active + "'><a class='page-link' href='" + i + "'>" + i + "</a></li>";
        }

        if (next){
            str += "<li class='page-item'><a class='page-link' href'" + (endPage + 1) + "'다음</a></li>";
        }

        str += "</ul></div>";

        console.log(str);

        replyPageFooter.html(str);
    }

    replyPageFooter.on("clock", function (e) {
        e.preventDefault();
        console.log("page click");

        var targetPageNum = $(this).attr("href");
        pageNum = targetPageNum;
        showList(pageNum);
    })
</script>
</html>
