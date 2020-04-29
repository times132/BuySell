<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/board.css">
    <link rel="stylesheet" href="/resources/css/magnific.css">

    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
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

    <style>
        .mfp-with-zoom .mfp-container,
        .mfp-with-zoom.mfp-bg {
            opacity: 0;
            -webkit-backface-visibility: hidden;
            /* ideally, transition speed should match zoom duration */
            -webkit-transition: all 0.3s ease-out;
            -moz-transition: all 0.3s ease-out;
            -o-transition: all 0.3s ease-out;
            transition: all 0.3s ease-out;
        }

        .mfp-with-zoom.mfp-ready .mfp-container {
            opacity: 1;
        }
        .mfp-with-zoom.mfp-ready.mfp-bg {
            opacity: 0.8;
        }

        .mfp-with-zoom.mfp-removing .mfp-container,
        .mfp-with-zoom.mfp-removing.mfp-bg {
            opacity: 0;
        }
    </style>
</head>
<body>
    <%@include file="../include/navbar.jsp"%>
    <div class="container">
        <!-- 수정, 삭제, 목록 버튼-->
        <div class="btn-list mb-1">
            <sec:authentication property="principal" var="userinfo"/>
            <sec:authorize access="isAuthenticated()">
                <c:if test="${userinfo.user.nickname eq boardDto.user.nickname}">
                    <c:if test="${boardDto.sellCheck eq false}">
                        <button class="btn btn-success btn-sm" data-oper="sell">판매완료</button>
                        <button class="btn btn-primary btn-sm" data-oper="modify">수정</button>
                    </c:if>
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
                        <div class="product-info col my-lg-2">
                            <div class="muted-info">
                                <span class="h6 category text-muted"><small>분류 : <c:out value="${boardDto.category}"/></small></span>
                                <span class="h6 createdDate text-muted"><small><javatime:format pattern="yyyy.MM.dd hh:mm" value="${boardDto.createdDate}"/></small></span>
                            </div>

                            <h2><c:out value="${boardDto.title}"/></h2>
                            <h4 class="price"><fmt:formatNumber value="${boardDto.price}"/>원</h4>

                            <!-- 작성자 드랍다운 -->
                            <div class="writer-dropdown">
                                <div class='profile'>
                                    <img src='/display?fileName=${boardDto.user.id}/profile/s_${boardDto.user.profileImage}' onerror="this.src='/resources/image/profile.png'"/>
                                    <button type="button" class="writer btn btn-link btn-sm dropdown-toggle pro" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <span class="writer h6"><c:out value="${boardDto.writer}"></c:out></span>
                                    </button>
                                    <div class="dropdown-menu dropdown-menu-right" role="menu" aria-labelledby="dropdownMenu">
                                        <a class="dropdown-item" href="/user/${boardDto.user.id}/boards" id="board">게시글 보기</a>
                                        <a class="dropdown-item" href="#" id="chatting">1:1채팅</a>
                                    </div>
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
                        <p class="mb-0">&nbsp|&nbsp</p>
                        <p class="mb-0"><strong class="like">
                            <sec:authorize access="isAnonymous()">
                                <div>좋아요&nbsp</div>
                                <div class='like-btn'>
                                    <img id='likeImg' src='/resources/image/dislike.png'>&nbsp${boardDto.likeCnt}
                                </div>
                            </sec:authorize>
                        </strong></p>
                    </div>

                    <div class="card-body">
                        <div class="reply-body">
                            <ul class="reply-list">
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

        <!-- 사진 눌렀을때 확대한 사진 -->
        <div class="image-wrapper">
            <div class="origin-picture">

            </div>
        </div>

        <form id="operForm" action="/board/modify" method="get">
            <input type="hidden" id="bid" name="bid" value="<c:out value="${boardDto.bid}"/>">
            <input type="hidden" id="writer" name="writer" value="<c:out value="${boardDto.user.nickname}"/>">
            <input type="hidden" name="page" value="<c:out value="${cri.page}"/>">
            <input type="hidden" name="type" value="<c:out value="${cri.type}"/>">
            <input type="hidden" name="keyword" value="<c:out value="${cri.keyword}"/>">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
    </div>

    <!-- js & jquery -->
    <script src="/resources/js/chat.js"></script>
    <script src="/resources/js/reply.js"></script>
    <script src="/resources/js/board.js"></script>
    <script src="/resources/js/common.js"></script>
    <script src="/resources/js/magnific.min.js"></script>
    <script>
        $(document).ready(function () {
            // 사용자를 통해 세부페이지로 왔을 때 id값 저장
            var checkUser = commonService.getParameterByName("id");
            var operForm = $("#operForm");
            var nickName = "<c:out value="${boardDto.user.nickname}"/>";


            // 닉네임 클릭 후 채팅 클릭 이벤트
            $("#chatting").on("click", function (e) {
                var nickname = {
                    nickname : nickName
                };
                chatService.createRoom(nickname, function (result) {
                        alert("채팅방으로 이동합니다.");
                        location.href = "/chat/room/enter/" + result;
                });
            });

            // 삭제, 수정, 목록 버튼 이벤트
            $("button[data-oper='remove']").on("click", function (e) {
                operForm.attr("action", "/board/remove").attr("method", "post").submit();
            });
            $("button[data-oper='modify']").on("click", function (e) {
                operForm.attr("action", "/board/modify").submit();
            });
            $("button[data-oper='list']").on("click", function (e) {
                if (checkUser === ""){
                    operForm.find("#bid").remove();
                    operForm.find("#writer").remove();
                    operForm.attr("action", "/board");
                }
                else{
                    operForm.find("#bid").remove();
                    operForm.find("#writer").remove();
                    operForm.attr("action", "/user/"+checkUser);
                }

                operForm.submit();
            });
            $("button[data-oper='sell']").on("click", function (e) {
                if(confirm("판매 완료하면 수정이 불가합니다.")){
                    operForm.attr("action", "/board/sell").attr("method", "post").submit();
                }
            });

            // 첨부 파일 가져오기
            $.getJSON("/board/getFileList", {bid: bidValue}, function (arr) {
                var str = "";
                var first = "";

                if (arr.length === 0){
                    str += "<img class='d-block img-fluid' src='/resources/image/no-image.jpg'>";
                    $(".carousel-control-prev").hide();
                    $(".carousel-control-next").hide();

                }
                else{
                    $(arr).each(function (i, file) {
                        if (file.image){

                            var fileCallPath = encodeURIComponent(file.uploadPath + "/s_" + file.uuid + "_" + file.fileName);
                            if (i === 0){ // 첫번째 요소에 active 부여
                                first += "<li class='active' data-target='#carouselIndicators' data-slide-to='" + i + "'></li>";
                                str += "<a href='/display?fileName=" + fileCallPath + "' class='carousel-item active' data-path='" + "'>";
                            }
                            else{
                                first += "<li data-target='#carouselIndicators' data-slide-to='" + i + "'></li>";
                                str += "<a href= '/display?fileName=" + fileCallPath + "' class='carousel-item'>";
                            }

                            str += "<img class='d-block img-fluid' src='/display?fileName=" + fileCallPath + "'>";
                            str += "</a>";
                        }
                    });
                }

                $(".carousel-indicators").html(first);
                $(".carousel-inner").html(str);
            });

            // 썸네일 사진 클릭시
            $('.carousel-inner').magnificPopup({
                delegate: 'a',
                type: 'image',
                closeOnContentClick: false,
                closeBtnInside: false,
                mainClass: 'mfp-with-zoom mfp-img-mobile',
                image: {
                    verticalFit: true,
                },
                gallery: {
                    enabled: true
                },
            });
        });
    </script>

    <script>
        var bidValue = "<c:out value="${boardDto.bid}"/>";
        var replyUL = $(".reply-list");
        var likeUL = $(".like");
        var curUser = null;
        var likeCnt = "<c:out value="${boardDto.likeCnt}"/>";
        <sec:authorize access="isAuthenticated()">
        <sec:authentication property="principal" var="userinfo"/>;
        curUser = '${userinfo.user.nickname}';

        showLike(likeCnt);
        </sec:authorize>

        //관심유저 관련
        function showLike(likeCnt){
            boardService.checkLike(bidValue, function (data) {
                console.log(data)
                var str = "<div>좋아요&nbsp</div><div class='like-btn'>";
                //date가 있는 경우 (like인 경우)
                if (data){
                    str += "<img id = 'deleteLike' src='/resources/image/like.png'>";
                }
                //date가 없는 경우(like 안한 상태)
                else {
                    str += "<img id='addLike' src='/resources/image/dislike.png'>";
                }
                str += "&nbsp" + likeCnt + "</div>";
                likeUL.html(str);
            });
        }

        $(document).on("click", "#addLike", function () {
            boardService.addLike(bidValue, function (data) {// data : 관심유저개수
                showLike(data);
            });
        });

        $(document).on("click", "#deleteLike", function () {
            boardService.deleteLike(bidValue, function (data) {
                showLike(data);
            });
        });

        // 댓글 목록 출력
        showList(1);
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
                    console.log(data.content[i]);
                    str += "<li class='reply-li' data-rid='" + data.content[i].rid + "'>";
                    str += "<div class='reply-header'><img class='reply-profile' src='/display?fileName=" + data.content[i].user.id
                        + "/profile/s_" + data.content[i].user.profileImage
                        + "' onerror=\"this.src='/resources/image/profile.png'\"/>"
                    str += "<strong id='replyer' class='primary-font'>" + data.content[i].user.nickname + "</strong>";
                    str += " <small class='text-muted'>" + commonService.displayTime(data.content[i].createdDate) + "</small>";

                    str += "</div>";
                    str += (curUser === data.content[i].user.nickname ?
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
                replyerCopy = reply.user.nickname;
            });
        });

        // 댓글 수정 클릭
        $(document).on("click", "#modReplyBtn", function(){
            var rid = $(this).closest("li").data("rid");
            $(this).parent(".reply-header-btn").html("<button id='modifyCancel' class='btn btn-sm btn-link text-muted'>수정취소</button></div>");
            var str = "";

            str += "<div class='reply-modify form-group'><textarea class='form-control rounded-0' name='mod_reply'>"+replyCopy+"</textarea>";
            str += "<button id='modifyConfirm' class='btn btn-lg btn-light'>수정</button></div>"

            $("#reply_"+rid).html(str);
        });

        // 수정한 댓글 전송
        $(document).on("click", "#modifyConfirm", function () {
            var rid = $(this).closest("li").data("rid");
            var modReply = $("#reply_"+rid).find("textarea[name='mod_reply']");

            var reply = {
                reply: modReply.val(),
                rid: rid,
            };

            replyService.update(reply, function (result) {
                alert(result);

                showList(pageNum);
            });
        });

        // 수정 취소
        $(document).on("click", "#modifyCancel", function () {
            showList(pageNum);
        });

        // 댓글 삭제
        $(document).on("click", "#removeReplyBtn", function(){
            var rid = $(this).closest("li").data("rid");
            var originalReplyer = replyerCopy;

            if (curUser != originalReplyer){
                alert("자신의 댓글만 삭제가 가능합니다");
                return;
            }

            replyService.remove(rid, originalReplyer, function (result) {
                alert(result);
                showList(pageNum);
            });
        });
    </script>
</body>
</html>
