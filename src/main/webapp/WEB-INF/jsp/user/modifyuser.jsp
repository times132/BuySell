<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">

<script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
<script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
<!------ Include the above in your HEAD tag ---------->

<head>
    <title>회원정보수정</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <style>
        .profile-img{
            width: inherit;
            height: inherit;
        }
    </style>

</head>

<body>
<hr>
<sec:authentication property="principal.user" var="userinfo"/>
<c:set var="provider" value="${userinfo.provider}"/>
<div class="container">
    <div class="row">
        <div class="col-sm-10"><h1>내정보</h1></div>
        <div class="col-sm-2"><a href="/users" class="pull-right"><img title="profile image" class="img-circle img-responsive" src="http://www.gravatar.com/avatar/28fd20ccec6865e2d5f0e1f4446eb7bf?s=100"></a></div>
    </div>
    <div class="row">
        <div class="col-sm-3"><!--left col-->


            <div class="profile-image">
<%--                <img src="http://ssl.gstatic.com/accounts/ui/avatar_2x.png" class="avatar img-circle img-thumbnail" alt="avatar">--%>
<%--                <img class="img-thumbnail" src="/display?fileName=${userinfo.id}/profile/${userinfo.profileImage}" onerror="this.src='/resources/image/profile.png';"/>--%>
            </div>
            <h6>Upload a different photo...</h6>
            <input name="uploadProfile" type="file" class="text-center center-block file-upload"></hr><br>


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
        <div class="col-sm-9">
            <ul class="nav nav-tabs">
                <li class="nav-item"><a  class="nav-link active" data-toggle="tab" href="#home">Home</a></li>
                <li class="nav-item"><a  class="nav-link" data-toggle="tab" href="#changePW">비밀번호 변경</a></li>
            </ul>


            <div class="tab-content">
                <div class="tab-pane active" id="home">
                    <hr>
                        <form class="form" action="/user/modifyuser" method="post">
                        <div class="form-group">

                            <div class="col-xs-6">
                                <label for="username"><H4>ID</H4></label>
                                <input type="text" class="form-control" id="username" name="username" value="${userinfo.username}" readonly="readonly" required>
                                <p>${valid_username}</p>
                                <div class="alert alert-danger" id="username_check">사용할 수 없는 ID입니다.</div><br>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-6">
                                <h4>NAME</h4>
                                <input type="text" class="form-control" name="name" value="${userinfo.name}" placeholder="NAME" title="enter your last name if any.">
                            </div>
                        </div>

                            <div class="form-group">
                                <div class="col-xs-6">
                                    <h4>NICKNAME</h4>
                                    <input type="text" class="form-control" name="nickname" value="${userinfo.nickname}"  placeholder="enter phone" title="enter your phone number if any.">
                                </div>
                            </div>
                            <div class="alert alert-danger" id="nickname_check">사용할 수 없는 닉네임입니다.</div><br>
                        <div class="form-group">
                            <div class="col-xs-6">
                                <h4>EMAIL</h4>
                                <input type="text" class="form-control" name="email" value="${userinfo.email}" readonly="readonly" title="enter your email">
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-xs-6">
                                <h4>PHONE NUMBER</h4>
                                <input type="text" class="form-control" name="phone" value="${userinfo.phone}"  placeholder="enter mobile number" title="enter your mobile number if any.">
                            </div>
                        </div>
                         <c:if test = "${provider eq 'giveandtake'}">
                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <h4>PASSWORD</h4>
                                        <input type="password" class="form-control" name="password"  placeholder="password" title="enter your password.">
                                    </div>
                                </div>
                         </c:if>
                        <div class="form-group">
                            <div class="col-xs-12">
                                <br>
                                <input type="hidden" name="id" value="${userinfo.id}">
                                <input type="hidden" name="activation" value="${userinfo.activation}">
                                <input type="hidden" id="profileImage" name="profileImage" value="${userinfo.profileImage}">
                                <input type="hidden" name="provider" value="${userinfo.provider}">
                                <input class="btn btn-sm btn-primary" type="submit" id="modify"  value="수정"/>
                                <input class="btn btn-sm btn-primary" type="button" value="홈" onClick="self.location='/';">
                            </div>
                        </div>
                    </form>

                    <hr>

                </div><!--/tab-pane-->

                <div class="tab-pane" id="changePW">
                    <div class="form-group">
                        <div class="col-xs-6">
                            <h4>PASSWORD</h4>
                            <input type="password" class="form-control" id="password"  placeholder="password" title="enter your password.">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-6">
                            <h4>PASSWORD</h4>
                            <input type="password" class="form-control" name="password" id="pwd1" placeholder="password" title="enter your password.">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="pwd2"><h4>CONFIRM PASSWORD</h4></label>
                        <input type="password" class="form-control" name="password2" id="pwd2" placeholder="비밀번호 확인" title="enter your password2.">
                        <div class="alert alert-success" id="alert-success">비밀번호가 일치합니다.</div>
                        <div class="alert alert-danger" id="alert-danger">비밀번호확인이 필요합니다. 비밀번호가 일치하지 않습니다.</div>
                        <p>${valid_password}</p>
                    </div>
                    <input class="btn btn-sm btn-primary" type="submit" id=submit name="submit" value="변경"/>
                </div>

            </div><!--/tab-pane-->
        </div><!--/tab-content-->

    </div><!--/col-9-->
</div><!--/row-->
<script type="text/javascript" src="/resources/js/fileupload.js"></script>
<script type="text/javascript" src="/resources/js/user.js"></script>
<script>
    var authority = $("#role");
    console.log("###role"+ authority)
    $(document).ready(function() {
        var profileImage = "<c:out value="${userinfo.profileImage}"/>";

        var profile = $(".profile-image");
        if (profileImage === ""){
            profile.html("<img class='img-thumbnail' src='/resources/image/profile.png'/>")
        }else{
            profile.html("<img class='img-thumbnail' src='/display?fileName=${userinfo.id}/profile/${userinfo.profileImage}'/>")
        }

    });
    $("#nickname_check").hide();
    // 아이디 유효성 검사(1 = 중복 / 0 != 중복)
    idck = 0;
    $("#nickname").keyup(function() {
        var nickname = $("#nickname").val();
        $("#nickname_check").hide();
        userService.checkNickname(nickname, function (data) {
            if (data) {
                $("#username_check").show();
                $("#modify").attr("disabled", true);
                idck=0;
            }
            else {
                $("#username_check").hide();
                idck=1;
            }
            if(idck==1){
                $("#modify")
                    .removeAttr("disabled");
            }
        });
    });

    $(function(){
        $("#alert-success").hide();
        $("#alert-danger").hide();
        $("input").keyup(function(){
            var pwd1=$("#pwd1").val();
            var pwd2=$("#pwd2").val();
            if(pwd1 != "" || pwd2 != ""){
                if(pwd1 == pwd2){
                    $("#alert-success").show();
                    $("#alert-danger").hide();
                    $("#submit").removeAttr("disabled"); }
                else{
                    $("#alert-success").hide(); $("#alert-danger").show();
                    $("#submit").attr("disabled", "disabled");
                }
            }
        });
    });

    $("#submit").click(function(){
        var password = $("#password").val();
        var pwd1 = $("#pwd1").val();
        console.log("newPW:"+password);
        console.log("newPW:"+pwd1);
        var info = {
            password : password,
            newPW : pwd1
        };
        userService.changePW(info, function (result) {
            alert(result);
            return;
        });

    });
</script>
</body>