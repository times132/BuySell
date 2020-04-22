<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="/resources/css/myinfo.css">

<script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
<script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>


<!------ Include the above in your HEAD tag ---------->

<head>
    <title>회원정보 찾기</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="/resources/js/user.js"></script>
</head>
<body>
<%@include file="../include/header.jsp"%>
<hr>
<div class="container">
    <div class="row">
        <div class="col-sm-10"><h1>회원정보 찾기</h1></div>
    </div>
    <div class="row">
        <div class="col-sm-3"><!--left col-->

            <div class="profile-image">

            </div></hr><br>


            <div class="panel panel-default">
                <div class="panel-heading">Website <i class="fa fa-link fa-1x"></i></div>
                <div class="panel-body"><a href="/">giveandtake.com</a></div>
            </div>



        </div><!--/col-3-->
        <div class="col-sm-9">
            <ul class="nav nav-tabs">
                <li class="nav-item"><a  class="nav-link active" data-toggle="tab" href="#ID">아이디 찾기</a></li>
                <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#PASSWORD">비밀번호 찾기</a></li>
            </ul>



            <!--------------------------------아이디찾기---------------------------------------------------->
            <div class="tab-content">
                <div class="tab-pane active" id="ID">
                    <hr>
                    <div class="row">
                        <div class="col-md-8 col-md-offset-4">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <div class="text-center">
                                        <h3><i class="fa fa-user fa-4x"></i></h3>
                                        <h3 class="text-center">아이디를 잊어버리셨습니까?</h3>
                                        <br>
                                        <p>이메일과 이름을 입력해주세요</p>
                                        <form id="actionForm" action="/user/findID" method="get">
                                        <div class="form-group">
                                            <div class="input-group">
                                                <input type="text" class="form-control" id="email" name="email" placeholder="이메일" required/>
                                            </div>
                                        </div>
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <input type="text" class="form-control" id="name" name="name" placeholder="이름" required/>
                                                </div>
                                            </div>
                                        <div class="form-group">
                                            <input id="findId" class="btn btn-lg btn-primary btn-block btnForget" value="아이디찾기" type="submit">
                                        </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id ="userList" class="col-md-4 col-md-offset-4">
                            <table class="table table-responsive table-sm table-hover">
                                <thead>
                                <tr>
                                    <th style="width: 10%" scope="col">ID</th>
                                </tr>
                                </thead>
                            <tbody>


                            <c:forEach items="${userList}" var="user">
                            <tr>
                                <th scope="row">
                                    <h6><c:out value="${user.username}"/>
                                    <c:set var="provider" value="${user.provider}"/>
                                    <c:if test = "${provider eq 'giveandtake'}">
                                        <span class="badge badge-pill badge-primary"><c:out value="${user.provider}"/></span></h6>
                                    </c:if>
                                    <c:if test = "${provider eq 'kakao'}">
                                        <span class="badge badge-pill badge-warning"><c:out value="${user.provider}"/></span></h6>
                                    </c:if>
                                    <c:if test = "${provider eq 'google'}">
                                        <span class="badge badge-pill badge-dark"><c:out value="${user.provider}"/></span></h6>
                                    </c:if>
                                </th>
                            </tr>
                            </c:forEach>
                            </tbody>
                            </table>

                        </div>
                    </div>

                </div>

                <!--------------------------------------비밀번호찾기---------------------------------------------------->
                <div class="tab-pane" id="PASSWORD">
                    <br>
                    <br>
                        <div class="row">
                            <div class="col-md-8 col-md-offset-4">
                                <div class="panel panel-default">
                                    <div class="panel-body">
                                        <div class="text-center">
                                            <h3><i class="fa fa-lock fa-4x"></i></h3>
                                            <h3 class="text-center">비밀번호를 잊어버리셨습니까?</h3>
                                            <br>
                                            <p>아이디를 입력해주세요</p>
                                            <p>고객님이 등록하신 이메일로 임시 비밀번호가 발송됩니다.</p>
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" id="username" placeholder="ID를 입력해주세요." required/>
                                                    </div>
                                                </div>
                                                <div class="alert alert-danger" id="username-check">존재하지 않는 아이디 입니다.</div>


                                                <input id="finding" class="btn btn-lg btn-primary btn-block btnForget" value="이메일 발송">

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                </div>


            </div><!--/tab-content-->

        </div><!--/col-9-->
    </div><!--/row-->
</div>
<script>
    idck = 0;
    $("#username-check").hide();
    $("#username").keyup(function() {
        var userName = $("#username").val();
        var username = {
            username :userName
        }
        $("#username-check").hide();
        userService.checkUsername(userName, function (data) {
            if (data == false) {
                $("#username-check").show();
                $("#finding").attr("disabled", true);
                idck = 0;
            }
            else {
                $("#username-check").hide();
                idck=1;
            }
            if(idck==1) {
                $("#finding")
                    .removeAttr("disabled")
                    .click(function() {
                        alert("고객님의 이메일을 확인해주세요. 임시비밀번호가 발송되었습니다.");
                        userService.findPW(username, function (result) {
                            if (result != "true"){
                                alert("고객님이 등록하신 계정의 이메일 정보가 부정확합니다.");
                                return;
                            }
                            else
                            {
                                location.href = "/user/login"
                                return;
                            }

                        });
                    });
            }
        });
    });



</script>
</body>
</html>