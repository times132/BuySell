<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <link href="/resources/css/admin.css" rel="stylesheet">
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css" id="bootstrap-css">
    <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.min.js"></script>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>관리자 페이지</title>
</head>
<body>

<div class="page-wrapper chiller-theme toggled">
    <a id="show-sidebar" class="btn btn-sm btn-dark" href="#">
        <i class="fas fa-bars"></i>
    </a>
    <nav id="sidebar" class="sidebar-wrapper">
        <div class="sidebar-content">
            <div class="sidebar-brand">
                <a href="/">GIVEANDTAKE</a>
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
                                    <a href="/admin/userinfo">모든 계정 정보 확인 및 관리
                                        회원 수 <span class="badge badge-pill badge-success">5</span>
                                    </a>
                                </li>
                                <li>
                                    <a href="/admin/userrole">계정 권한 삭제 및 추가</a>
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
            <h2>계정 권한 삭제 및 추가</h2>
            <hr>
            <div class="row">
                <div class="form-group col-md-12">
                    <div class="card card-default" id="info">
                        <div class="card-header">
                            관리자
                        </div>
                        <div class="card-body">
                            <div class="admin-body">
                            <%--  어드민리스트  --%>
                                <ul class="adminList">

                                </ul>
                            </div>

                            <div class="admin-footer">
<%--                                페이징--%>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="form-group col-md-12">
                    <div class="card card-default" >
                        <div class="card-header">
                            유저
                        </div>
                        <div class="card-body">
                            <div class="admin-body">
                                <%--  유저리스트  --%>
                                <ul class="userList">

                                </ul>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="form-group col-md-12">
                    <div class="card card-default" >
                        <div class="card-header">
                            게스트
                        </div>
                        <div class="card-body">
                            <div class="admin-body">
                                <%--  유저리스트  --%>
                                <ul class="guestList">

                                </ul>
                            </div>

                        </div>
                    </div>
                </div>

            <div class="form-group col-md-12">
                <div class="card card-default" >
                    <div class="card-header">
                        소셜
                    </div>
                    <div class="card-body">
                        <div class="admin-body">
                            <%--  소셜리스트  --%>
                            <ul class="socialList">

                            </ul>
                        </div>

                    </div>
                </div>
            </div>
        </div>

        </div>

    </main>
    <!-- page-content" -->
</div>
<!-- page-wrapper -->
<script type="text/javascript" src="/resources/js/admin.js"></script>
<script>
    $(document).ready(function () {
        var adminUL = $(".adminList");
        var userUL = $(".userList");
        var guestUL = $(".guestList");
        var socialUL = $(".socialList");
        // 댓글 목록 출력
        adminService.roleList(function (data) {
            var str = "";
            if (data == null || data.length == 0) {
                return;
            }
            for (var i = 0, len = data.length || 0; i < len; i++) {
                console.log("data"+JSON.stringify(data[i]));
                str += "<li class='roleli' data-id='" + data[i].id + "'>";
                str += "<strong class='primary-font'>" + data[i].user.nickname + "</strong>";
                str += "<strong class='primary-font'>" + data[i].user.username + "</strong>";
                switch (data[i].role){
                    case "ROLE_ADMIN" :
                        str += "<span class='badge badge-pill badge-warning notification'>" + data[i].role + "</span></strong>";
                        str += "</li>";
                        adminUL.html(str);
                        break;
                    case "ROLE_USER" :
                        str += "<span class='badge badge-pill badge-success notification'>" + data[i].role + "</span></strong>";
                        str += "</li>";
                        userUL.html(str);
                        break;
                    case "ROLE_GUEST" :
                        str += "<span class='badge badge-pill badge-primary notification'>" + data[i].role + "</span></strong>";
                        str += "</li>";
                        guestUL.html(str);
                        break;
                    default :
                        str += "<span class='badge badge-pill badge-dark notification'>" + data[i].role + "</span></strong>";
                        str += "</li>";
                        socialUL.html(str);
                        break;
                }
                str ="";
                console.log("str :"+ str);
            }

        });


    });

</script>
</body>
