<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>GiveAndTake</title>

    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/board.css">

    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
</head>

<body>
    <%@include file="../include/navbar.jsp"%>
    <sec:authentication property="principal.user" var="userinfo"/>
    <div class="container">
        <div class="row">
            <div class="col-sm-10"><h1>내 관심목록</h1></div>
        </div>
        <div class="row">
            <div class="col-sm-3"><!--left col-->

                <div class="profile-image">

                </div></hr><br>


                <div class="panel panel-default">
                    <div class="panel-heading">Website <i class="fa fa-link fa-1x"></i></div>
                    <div class="panel-body"><a href="/">giveandtake.com</a></div>
                </div>


                <ul class="list-group">
                    <li class="list-group-item text-muted">Activity <i class="fa fa-dashboard fa-1x"></i></li>
                    <li class="list-group-item text-right"><span class="pull-left"><strong>Shares</strong></span> 125</li>
                    <li class="list-group-item text-right"><span class="pull-left"><strong>Likes</strong></span> 13</li>
                    <li class="list-group-item text-right"><span class="pull-left"><strong>Posts</strong></span> 37</li>
                </ul>


            </div><!--/col-3-->

            <div class="col-9">
                <!--------------------------------------게시물---------------------------------------------------->
                <table class="table table-sm table-hover">
                    <thead>
                    <tr>
                        <th style="width: 6%" scope="col">#</th>
                        <th style="width: 12%" scope="col">분류</th>
                        <th style="width: 45%" scope="col">글제목</th>
                        <th style="width: 10%" scope="col">가격</th>
                        <th style="width: 10%" scope="col">작성자</th>
                        <th style="width: 10%" scope="col">작성일</th>
                        <th style="width: 10%" scope="col">조회</th>
                        <th style="width: 10%" scope="col">전송</th>
                    </tr>
                    </thead>

                    <tbody>
                    <!-- CONTENTS !-->
                    <c:forEach items="${likeList}" var="like">
                        <tr>
                            <th scope="row">
                                <c:out value="${like.board.bid}"/>
                            </th>

                            <td class="category">
                                <c:out value="${like.board.category}"/>
                            </td>

                            <c:if test="${like.board.sellCheck eq true}">
                                <td class="title-sold">
                                    <a class="move" href='<c:out value="${like.board.bid}"/>'>
                                        <c:out value="${like.board.title}"/>
                                    </a>
                                    <span class="sold-reply-cnt">
                                            [<c:out value="${like.board.replyCnt}"/>]
                                        </span>
                                    <span class="sold">
                                            완료
                                        </span>
                                </td>
                            </c:if>
                            <c:if test="${like.board.sellCheck eq false}">
                                <td class="title">
                                    <a class="move" href='<c:out value="${like.board.bid}"/>'>
                                        <c:out value="${like.board.title}"/>
                                    </a>
                                    <span class="replyCnt">
                                            [<c:out value="${like.board.replyCnt}"/>]
                                     </span>
                                </td>
                            </c:if>

                            <td class="price">
                                <fmt:formatNumber value="${like.board.price}" type="currency"/>
                            </td>

                            <td class="writer">
                                <c:out value="${like.board.writer}"/>
                            </td>

                            <td class="time">
                                <javatime:format pattern="yy.MM.dd" value="${like.board.createdDate}"/>
                            </td>

                            <td class="viewCnt">
                                <c:out value="${like.board.viewCnt}"/>
                            </td>
                            <td class="order">
                                <button class='btn float-right'>
                                    <a class="item" href='<c:out value="${like.board.bid}"/>'>
                                        <img class='btn-img' src='/resources/image/love.png'>
                                    </a>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div class="list-footer">
                    <%-- 페이징 --%>
                    <ul class="pagination pagination-sm justify-content-center">
                        <c:if test="${pageMaker.prev}">
                            <li class="page-item"><a class="prev" href="${pageMaker.startPage - 1}">이전</a></li>
                        </c:if>
                        <c:forEach var="page" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                            <li class="page-item ${pageMaker.cri.page == page ? "active" : ""}"><a class="page" href="${page}">${page}</a></li>
                        </c:forEach>
                        <c:if test="${pageMaker.next}">
                            <li class="page-item"><a class="next" href="${pageMaker.endPage + 1}">다음</a></li>
                        </c:if>
                    </ul>
                </div>

                <form id="actionForm" action="/user/${userinfo.id}/likes" method="get">
                    <input type="hidden" name="id" value="${userinfo.id}/likes">
                    <input type="hidden" name="page" value="${pageMaker.cri.page}">
                </form>
            </div><!--/col-9-->
        </div><!--/row-->
    </div>

    <!-- js -->
    <script type="text/javascript" src="/resources/js/mail.js"></script>
    <script type="text/javascript" src="/resources/js/user.js"></script>
    <script>
        $(document).ready(function() {
            var profileImage = "<c:out value="${userinfo.profileImage}"/>";
            var profile = $(".profile-image");
            if (profileImage === ""){
                profile.html("<img class='img-thumbnail' src='/resources/image/profile.png'/>")
            }
            else{
                profile.html("<img class='img-thumbnail' src='<spring:eval expression="@commonProperties['spring.prefixPath']"/>${userinfo.id}/profile/${userinfo.profileImage}'/>")
            }
            var actionForm = $("#actionForm");
            $(".move").on("click", function (e) {
                e.preventDefault();
                actionForm.append("<input type='hidden' name='bid' value='"+$(this).attr("href")+"'>");
                actionForm.attr("action", "/board/read");
                actionForm.submit();
            });

            $(".page-item a").on("click", function (e) {
                e.preventDefault();
                actionForm.find("input[name='page']").val($(this).attr("href"));
                actionForm.submit();
            });

            //주문하기 위한 메시지 전송
            var id = '${userinfo.id}';
            $(".item").on("click", function (e) {
                e.preventDefault();
                actionForm.append("<input type='hidden' name='bid' value='"+$(this).attr("href")+"'>");
                actionForm.append("<input type='hidden' name='userId' value='"+id+"'>");
                actionForm.attr("action", "/chat/message/orderItem");
                actionForm.submit();
            });
        });
    </script>
</body>
</html>