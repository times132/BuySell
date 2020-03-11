<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <link href="/resources/css/board.css" rel="stylesheet">

    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
    <script type="text/javascript" src="/resources/js/reply.js"></script>
    <script type="text/javascript" src="/resources/js/common.js"></script>
</head>
<body>
    <%@include file="../include/header.jsp"%>



    <div class="container">
        <!-- 수정, 삭제, 목록 버튼-->
        <div class="btnlist mb-1">
            <sec:authentication property="principal" var="userinfo"/>
            <sec:authorize access="isAuthenticated()">
                <c:if test="${userinfo.username eq boardDto.writer}">
                    <button class="btn btn-primary btn-sm" data-oper="modify">수정</button>
                    <button class="btn btn-danger btn-sm" data-oper="remove">삭제</button>
                </c:if>
            </sec:authorize>
            <button class="btn btn-dark btn-sm listbtn" type="button" data-oper="list">목록</button>
        </div>

        <!-- product info -->
        <div class="row">
            <div class="col">
                <div class="card mb-3">
                    <div class="row no-gutters">
                        <div class="col-auto">
                            <div id="carouselIndicators" class="carousel slide" data-interval="false">
                                <ol class="carousel-indicators">
                                    <!-- photo indicator -->
                                </ol>
                                <div class="carousel-inner" role="listbox">
                                    <!-- 사진 -->
                                </div>
                                <a class="carousel-control-prev" href="#carouselIndicators" role="button" data-slide="prev">
                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                    <span class="sr-only">Previous</span>
                                </a>
                                <a class="carousel-control-next" href="#carouselIndicators" role="button" data-slide="next">
                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </div>
                        </div>

                        <!-- 제품 기본 정보 -->
                        <div class="product_info col my-lg-2">
                            <div class="muted-info">
                                <span class="h6 btype text-muted"><small>분류 : <c:out value="${boardDto.btype}"/></small></span>
                                <span class="h6 createddate text-muted"><small><javatime:format pattern="yyyy.MM.dd hh:mm" value="${boardDto.createdDate}"/></small></span>
                            </div>

                            <h1 class="title"><c:out value="${boardDto.title}"/></h1>
                            <h4 class="price"><fmt:formatNumber value="${boardDto.price}"/>원</h4>

                            <div class="writer-dropdown">
                                <span class="h6">판매자 : </span>
                                <button type="button" class="writer btn btn-link btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <span class="writer h6"><c:out value="${boardDto.writer}"></c:out></span>
                                </button>
                                <div class="dropdown-menu dropdown-menu-right" role="menu" aria-labelledby="dropdownMenu">
                                    <a class="dropdown-item" href="#" id="board">게시글 보기</a>
                                    <a class="dropdown-item" href="#" id="chatting">1:1채팅</a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 제품 상세 내용 -->
                    <div class="card-body">
                        <p class="card-text">
                            <c:out value="${boardDto.content}"/>
                        </p>
                    </div>
                </div>

                <!-- 댓글 -->
                <div class="card">
                    <div class="card-header">
                        <p class="mb-0"><strong class="replycnt"></strong></p>
                    </div>
                    <div class="card-body">

                        <div class="reply-body">
                            <ul class="replyList">
                                <!-- 댓글 리스트 -->
                            </ul>
                        </div>

                        <div class="reply-footer">
                            <!-- 댓글 페이징 -->
                        </div>

                        <sec:authorize access="isAuthenticated()">
                            <div class="reply-add form-group">
                                <textarea name="reply" class="form-control rounded-0" rows="3" placeholder="내용"></textarea>

                                <button id="addReplyBtn" class="btn btn-lg btn-light">등록</button>
                            </div>
                        </sec:authorize>
                    </div>
                </div>
            </div>
        </div>




        <div class="imageWrapper">
            <div class="originPicture">

            </div>
        </div>

        <form id="operForm" action="/board/modify" method="get">
            <input type="hidden" id="bid" name="bid" value="<c:out value="${boardDto.bid}"/>">
            <input type="hidden" id="writer" name="writer" value="<c:out value="${boardDto.writer}"/>">
            <input type="hidden" name="page" value="<c:out value="${cri.page}"/>">
            <input type="hidden" name="type" value="<c:out value="${cri.type}"/>">
            <input type="hidden" name="keyword" value="<c:out value="${cri.keyword}"/>">
        </form>
    </div>

</body>
<script>
    $(document).ready(function () {
        console.log("<c:out value="${boardDto.createdDate}"/>")
        var operForm = $("#operForm");
        var username = "<c:out value="${boardDto.writer}"/>";

        // 닉네임 클릭 후 채팅 클릭 이벤트
        $("#chatting").on("click", function (e) {
            alert("채팅창으로 이동합니다.");
            location.href="/chat/rooom/"+username;
        })

        // 삭제, 수정, 목록 버튼 이벤트
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

        // 첨부 파일 가져오기
        $.getJSON("/board/getFileList", {bid: bidValue}, function (arr) {
            var str = "";
            var first = "";

            if (arr.length === 0){
                str += "<img class='d-block img-fluid' src='/resources/image/no-image.jpg'>";
                $(".carousel-control-prev").hide();
                $(".carousel-control-next").hide();

            }else{
                $(arr).each(function (i, file) {
                    if (file.image){
                        var fileCallPath = encodeURIComponent(file.uploadPath + "/s_" + file.uuid + "_" + file.fileName);
                        if (i === 0){ // 첫번째 요소에 active 부여
                            first += "<li class='active' data-target='#carouselIndicators' data-slide-to='" + i + "'></li>";
                            str += "<div class='carousel-item active' data-path='" + file.uploadPath + "' data-uuid='" + file.uuid + "' data-fileName='" + file.fileName + "' data-type='" + file.image + "'>";
                        }else{
                            first += "<li data-target='#carouselIndicators' data-slide-to='" + i + "'></li>";
                            str += "<div class='carousel-item' data-path='" + file.uploadPath + "' data-uuid='" + file.uuid + "' data-fileName='" + file.fileName + "' data-type='" + file.image + "'>";
                        }

                        str += "<img class='d-block img-fluid' src='/display?fileName=" + fileCallPath + "'>";
                        str += "</div>";
                    }
                });
            }

            $(".carousel-indicators").html(first);
            $(".carousel-inner").html(str);
        });

        // 썸네일 사진 클릭시 이벤트
        $(".carousel-inner").on("click", "div", function (e) {
            var liobj = $(this);
            console.log(liobj);
            var path = encodeURIComponent(liobj.data("path") + "/" + liobj.data("uuid") + "_" + liobj.data("filename"));

            showImage(path.replace(new RegExp(/\\/g),"/"));
        });

        // 원본 파일 화면에 표시
        function showImage(fileCallPath) {
            $(".imageWrapper").css("display", "flex").show();

            $(".originPicture")
                .html("<img src='/display?fileName=" + fileCallPath +"'>")
                .animate({width: "10%", height: "10%"}, 100);
        }

        // 원본 파일 클릭시 닫기
        $(".originPicture").on("click", function (e) {
            $(".originPicture").animate({width: "0%", height: "0%"});
            setTimeout(function () {
                $(".imageWrapper").hide();
            }, 100);
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

    // 댓글 목록 출력
    function showList(page) {
        replyService.getList({bid: bidValue, page: page || 1}, function (data) {
            var replyCntText = "댓글 " + data.totalElements;
            $(".replycnt").text(replyCntText);
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
                str += "<div class='reply-header'><strong id='replyer' class='primary-font'>" + data.content[i].replyer + "</strong>";
                str += " <small class='text-muted'>" + commonService.displayTime(data.content[i].createdDate) + "</small>";

                str += "</div>";
                str += (replyer === data.content[i].replyer ?
                    "<div class='reply-header-btn'><button id='modReplyBtn' class='btn btn-sm btn-link text-muted'>수정</button>" + "\|" +
                    "<button id='removeReplyBtn' class='btn btn-sm btn-link text-muted'>삭제</button></div>" :
                    '');
                str += "<p id='reply_" + data.content[i].rid + "'>" + data.content[i].reply + "</p>";
                str += "</li>";
            }

            replyUL.html(str);

            showReplyPage(data.totalElements, data.size); //21, 5
        });
    }

    var pageNum = 1;
    var replyPageFooter = $(".reply-footer");

    // 댓글 페이징 처리
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

        var str = "<ul class='pagination pagination-sm justify-content-center'>";

        if (prev){
            str += "<li class='page-item'><a class='page' href='" + (startPage - 1) + "'>이전</a></li>";
        }

        for (var i = startPage; i <= endPage; i++){
            var active = pageNum == i ? "active" : "";
            str += "<li class='page-item " + active + "'><a class='page' href='" + i + "'>" + i + "</a></li>";
        }

        if (next){
            str += "<li class='page-item'><a class='page' href='" + (endPage + 1) + "'>다음</a></li>";
        }

        str += "</ul></div>";

        replyPageFooter.html(str);
    }

    // 댓글 페이징 번호 클릭시
    replyPageFooter.on("click", "li a", function (e) {
        e.preventDefault();

        var targetPageNum = $(this).attr("href");

        pageNum = targetPageNum;
        showList(pageNum);
    });

    var replyadd = $(".reply-add");
    var inputReply = replyadd.find("textarea[name='reply']");
    var replyCopy = "";
    var replyerCopy = "";

    // 댓글 등록
    $("#addReplyBtn").on("click", function (e) {
        var reply = {
            reply: inputReply.val(),
            replyer: replyer,
            bid: bidValue
        };
        replyService.add(reply, function (result) {
            alert(result);
            inputReply.val('');
            showList(-1);
        });
    });

    // 댓글창 수정 버튼 클릭하면 정보를 읽어옴
    replyUL.on("click", "button", function () {
        var rid = $(this).closest("li").data("rid");

        replyService.get(rid, function (reply) {
            replyCopy = reply.reply;
            replyerCopy = reply.replyer;
        });
    });

    // 댓글 수정 클릭
    $(document).on("click", "#modReplyBtn", function(){
        var rid = $(this).closest("li").data("rid");
        $(this).parent(".reply-header-btn").html("<button id='modifycancel' class='btn btn-sm btn-link text-muted'>수정취소</button></div>");
        var str = "";

        str += "<div class='reply-modify form-group'><textarea class='form-control rounded-0' name='mod_reply'>"+replyCopy+"</textarea>";
        str += "<button id='modifyconfirm' class='btn btn-lg btn-light'>수정</button></div>"

        $("#reply_"+rid).html(str);
    });

    // 댓글 삭제
    $(document).on("click", "#removeReplyBtn", function(){
        var rid = $(this).closest("li").data("rid");
        var originalReplyer = replyerCopy;

        if (replyer != originalReplyer){
            alert("자신의 댓글만 삭제가 가능합니다");
            return;
        }

        replyService.remove(rid, originalReplyer, function (result) {
            alert(result);
            showList(pageNum);
        });
    });

    // 수정한 댓글 전송
    $(document).on("click", "#modifyconfirm", function () {
        var rid = $(this).closest("li").data("rid");
        var modReply = $("#reply_"+rid).find("textarea[name='mod_reply']");

        var reply = {
            reply: modReply.val(),
            rid: rid
        };

        replyService.update(reply, function (result) {
            alert(result);

            showList(pageNum);
        });
    });

    // 수정 취소
    $(document).on("click", "#modifycancel", function () {
        showList(pageNum);
    })
</script>
</html>
