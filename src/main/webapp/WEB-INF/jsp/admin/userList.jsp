<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>관리자 페이지</title>

    <link href="/resources/css/admin.css" rel="stylesheet">
    <link href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">

    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
</head>
<body>

    <div class="page-wrapper chiller-theme toggled">
        <a id="show-sidebar" class="btn btn-sm btn-dark" href="#">
            <i class="fas fa-bars"></i>
        </a>
        <nav id="sidebar" class="sidebar-wrapper">
            <div class="sidebar-content">
                <div class="sidebar-brand">
                    <a href="/">BUYSELL</a>
                    <div id="close-sidebar">
                        <i class="fas fa-times"></i>
                    </div>
                </div>
                <sec:authentication property="principal.user" var="userinfo"/>
                <div class="sidebar-header">
                    <div class="user-pic">
                        <img class='img-thumbnail' src='/display?fileName=${userinfo.id}/profile/${userinfo.profileImage}' onerror="this.src = '/resources/image/profile.png'"/>
                    </div>
                    <div class="user-info">
                          <span class="user-name">
                            <strong>${userinfo.nickname}</strong>
                          </span>
                        <span class="user-role">Administrator</span>
                        </span>
                    </div>
                </div>
                <!-- sidebar-header  -->
                <div class="sidebar-search">
                    <div>
                        <div class="input-group">
                            <input type="text" class="form-control search-menu" placeholder="Search...">
                            <div class="input-group-append">
                                  <span class="input-group-text">
                                    <i class="fa fa-search" aria-hidden="true"></i>
                                  </span>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- sidebar-search  -->
                <div class="sidebar-menu">
                    <ul>
                        <li class="header-menu">
                            <span>General</span>
                        </li>
                        <li class="sidebar-dropdown">
                            <a href="#">
                                <i class="fa fa-tachometer-alt"></i>
                                <span>계정 관리</span>
                                <span class="badge badge-pill badge-warning">New</span>
                            </a>
                            <div class="sidebar-submenu">
                                <ul>
                                    <li>
                                        <a href="/admin/userlist">모든 계정 정보 확인 및 관리
                                            회원 수 <span class="badge badge-pill badge-success">5</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="/admin/role">계정 권한 삭제 및 추가</a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="sidebar-dropdown">
                            <a href="#">
                                <i class="fa fa-shopping-cart"></i>
                                <span>게시판 관리</span>
                                <span class="badge badge-pill badge-danger">3</span>
                            </a>
                            <div class="sidebar-submenu">
                                <ul>
                                    <li>
                                        <a href="#">Products

                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">Orders</a>
                                    </li>
                                    <li>
                                        <a href="#">Credit cart</a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="sidebar-dropdown">
                            <a href="#">
                                <i class="fa fa-chart-line"></i>
                                <span>Charts</span>
                            </a>
                            <div class="sidebar-submenu">
                                <ul>
                                    <li>
                                        <a href="#">회원 수</a>
                                    </li>
                                    <li>
                                        <a href="#">게시글 수</a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="header-menu">
                            <span>Extra</span>
                        </li>
                        <li>
                            <a href="#">
                                <i class="fa fa-book"></i>
                                <span>문의사항</span>
                                <span class="badge badge-pill badge-primary">Beta</span>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <i class="fa fa-calendar"></i>
                                <span>Calendar</span>
                            </a>
                        </li>
                    </ul>
                </div>
                <!-- sidebar-menu  -->
            </div>
            <!-- sidebar-content  -->
            <div class="sidebar-footer">
                <a href="#">
                    <i class="fa fa-bell"></i>
                    <span class="badge badge-pill badge-warning notification">3</span>
                </a>
                <a href="#">
                    <i class="fa fa-envelope"></i>
                    <span class="badge badge-pill badge-success notification">7</span>
                </a>
                <a href="#">
                    <i class="fa fa-cog"></i>
                    <span class="badge-sonar"></span>
                </a>
            </div>
        </nav>
        <!-- sidebar-wrapper  -->
        <main class="page-content">
            <div class="container-fluid">
                <h2>모든 계정 정보 확인 및 관리</h2>
                <hr>
                <div class="row">
                    <div class="form-group col-md-12">
                        <div class="card card-default" id="info">
                            <div class="card-header">
                                회원 목록
                            </div>
                            <div class="card-body">

                                <form id="searchForm" action="/admin/userinfo" method="get">
                                    <div class="row justify-content-md-center">
                                        <div class="input-group input-group-sm mb-3 col-lg-8">
                                            <div class="input-group-append">
                                                <select name="type" class="form-control form-control-sm" id="exampleFormControlSelect1">
                                                    <option value=""<c:out value="${pageMaker.cri.type == null ? 'selected' : ''}"/>>--</option>
                                                    <option value="E">이메일</option>
                                                    <option value="N">닉네임</option>
                                                </select>
                                            </div>
                                            <input hidden="hidden" /> <%--엔터키 방지--%>
                                            <input name="keyword" type="text" class="form-control" placeholder="검색어를 입력해주세요" aria-label="Recipient's username" aria-describedby="button-addon2">
                                            <div class="input-group-append">
                                                <button class="btn btn-outline-secondary"  type="button" id="button-addon2">검색</button>
                                            </div>
                                        </div>
                                    </div>

                                    <input type="hidden" name="page" value="<c:out value="${pageMaker.cri.page}"/>"/>
                                </form>
                                <table class="table table-responsive table-sm table-hover">
                                    <thead>
                                    <tr>
                                        <th style="width: 6%" scope="col">#</th>
                                        <th style="width: 20%" scope="col">아이디</th>
                                        <th style="width: 12%" scope="col">이름</th>
                                        <th style="width: 12%" scope="col">닉네임</th>
                                        <th style="width: 15%" scope="col">이메일</th>
                                        <th style="width: 15%" scope="col">핸드폰</th>
                                        <th style="width: 10%" scope="col">가입일자</th>
                                        <th style="width: 5%" scope="col">권한</th>
                                        <th style="width: 5%" scope="col"></th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <!-- CONTENTS !-->
                                    <c:forEach items="${userList}" var="user">
                                        <tr>
                                            <th scope="row">
                                                <c:out value="${user.id}"/>
                                            </th>
                                            <td class="userName">
                                                <c:out value="${user.username}"/>
                                            </td>
                                            <td class="name">
                                                <c:out value="${user.name}"/>
                                            </td>
                                            <td class="nickname">
                                                <c:out value="${user.nickname}"/>
                                            </td>
                                            <td class="email">
                                                <c:out value="${user.email}"/>
                                            </td>
                                            <td class="phone">
                                                <c:out value="${user.phone}"/>
                                            </td>
                                            <td class="time">
                                                <javatime:format pattern="yy.MM.dd" value="${user.createdDate}"/>
                                            </td>
                                            <td class="role">
                                                <c:forEach items="${user.roles}" var="role">
                                                    <span class='badge badge-pill badge-primary notification'>${role.role.name}</span>
                                                    <br>
                                                </c:forEach>
                                            </td>
                                            <td class="deleting">
                                                <button class='btn float-right'>
                                                    <a class="delete" href='<c:out value="${user.id}"/>'>
                                                        <img class='btn-img' src='/resources/image/delete.png'>
                                                    </a>
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>

                                <div class="reply-footer">
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


                                <form id="actionForm" action="/admin/userlist" method="get">
                                    <input type="hidden" name="page" value="${pageMaker.cri.page}">
                                    <input type="hidden" name="type" value="${pageMaker.cri.type}">
                                    <input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">
                                </form>

                            </div>
                        </div>
                    </div>

                </div>


            </div>

        </main>
        <!-- page-content" -->
    </div>

    <!-- js -->
    <script>
        $(document).ready(function () {
            $(".delete").on("click", function (e) {
                e.preventDefault();
                var check = confirm("선택한 회원의 탈퇴를 진행하시겠습니까?");
                if(check) {
                    actionForm.append("<input type='hidden' name='id' value='" + $(this).attr("href") + "'>");
                    actionForm.attr("action", "/admin/userlist/delete");
                    actionForm.submit();
                }
            });

            var actionForm = $("#actionForm");
            $(".page-item a").on("click", function (e){
                e.preventDefault();
                actionForm.find("input[name='page']").val($(this).attr("href"));
                actionForm.submit();
            });

            var searchForm = $("#searchForm");
            $("#searchForm button").on("click", function (e) {
                if (!searchForm.find("option:selected").val()){
                    alert("검색종류를 선택하세요.");
                    return false;
                }
                if (!searchForm.find("input[name='keyword']").val()){
                    alert("키워드를 입력하세요.");
                    return false;
                }
                searchForm.find("input[name='page']").val("1");
                e.preventDefault();
                searchForm.submit();
            });


        })
        $(function () {
            $('.navbar-toggle-sidebar').click(function () {
                $('.navbar-nav').toggleClass('slide-in');
                $('.side-body').toggleClass('body-slide-in');
                $('#search').removeClass('in').addClass('collapse').slideUp(200);
            });

            $('#search-trigger').click(function () {
                $('.navbar-nav').removeClass('slide-in');
                $('.side-body').removeClass('body-slide-in');
                $('.search-input').focus();
            });
        });
        $(".sidebar-dropdown > a").click(function() {
            $(".sidebar-submenu").slideUp(200);
            if (
                $(this)
                    .parent()
                    .hasClass("active")
            ) {
                $(".sidebar-dropdown").removeClass("active");
                $(this)
                    .parent()
                    .removeClass("active");
            } else {
                $(".sidebar-dropdown").removeClass("active");
                $(this)
                    .next(".sidebar-submenu")
                    .slideDown(200);
                $(this)
                    .parent()
                    .addClass("active");
            }
        });

        $("#close-sidebar").click(function() {
            $(".page-wrapper").removeClass("toggled");
        });
        $("#show-sidebar").click(function() {
            $(".page-wrapper").addClass("toggled");
        });
    </script>
</body>
</html>
