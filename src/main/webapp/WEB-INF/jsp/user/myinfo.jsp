<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
<link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
<script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>


<!------ Include the above in your HEAD tag ---------->

<head>
    <title>내정보</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="/resources/js/mail.js"></script>
    <script type="text/javascript" src="/resources/js/user.js"></script>
</head>

<body>
<hr>
<sec:authentication property="principal.user" var="userinfo"/>
    <div class="container">
        <div class="row">
            <div class="col-sm-10"><h1>내정보</h1></div>
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

                <div class="panel panel-default">
                    <div class="panel-heading">Social Media</div>
                    <div class="panel-body">
                        <i class="fa fa-facebook fa-2x"></i> <i class="fa fa-github fa-2x"></i> <i class="fa fa-twitter fa-2x"></i> <i class="fa fa-pinterest fa-2x"></i> <i class="fa fa-google-plus fa-2x"></i>
                    </div>
                </div>

            </div><!--/col-3-->
            <div class="col-sm-9">
                <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab" href="#home">Home</a></li>
                    <li><a data-toggle="tab" href="#myboards">Board</a></li>
                    <li><a data-toggle="tab" href="#email">이메일 인증 및 계정 활성화</a></li>
                </ul>


                <div class="tab-content">
                    <div class="tab-pane active" id="home">
                        <hr>
                            <div class="form-group">

                                <div class="col-xs-6">
                                    <h4>NICKNAME</h4>
                                    <input type="text" class="form-control" name="username" value="${userinfo.username}" readonly="readonly" placeholder="NICKNAME" title="enter your first name if any.">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-6">
                                    <h4>NAME</h4>
                                    <input type="text" class="form-control" name="name" value="${userinfo.name}" readonly="readonly" placeholder="NAME" title="enter your last name if any.">
                                </div>
                            </div>

                            <div class="form-group">

                                <div class="col-xs-6">
                                    <h4>EMAIL</h4>
                                    <input type="text" class="form-control" name="email" value="${userinfo.email}" readonly="readonly" placeholder="enter phone" title="enter your phone number if any.">
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-xs-6">
                                    <h4>PHONE NUMBER</h4>
                                    <input type="text" class="form-control" name="phone" value="${userinfo.phone}" readonly="readonly" placeholder="enter mobile number" title="enter your mobile number if any.">
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-xs-12">
                                    <br>
                                    <input class="btn btn-primary btn-sm" type="button" value="회원정보수정" onClick="self.location='/user/modifyuser';">
                                    <input class="btn btn-danger btn-sm" type="button" value="회원탈퇴" onClick="self.location='/user/password';">
                                    <input class="btn btn-primary btn-sm" type="button" value="홈으로 이동" onClick="self.location='/';">
                                </div>
                            </div>
                        </form>

                        <hr>

                    </div><!--/tab-pane-->
                    <div class="tab-pane" id="myboards">

                        <h2></h2>

                        <hr>
                        <form class="form" action="##" method="post">


                        </form>

                    </div><!--/tab-pane-->
                    <div class="tab-pane" id="email">
                        <h6>이메일 인증 (이메일인증을 받으시면 보다 나은 서비스를 이용할 수 있습니다.)</h6>
                        <br> <br>
                            <label for="email">이메일</label>
                            <input class="form-control" type="text" id="e_mail" name="email" placeholder="EMAIL" required>
                            <span><input class="btn btn-primary btn-sm" type="button" id="auth" value="이메일 인증받기"/></span>
                    </div>

                </div><!--/tab-pane-->
            </div><!--/tab-content-->

        </div><!--/col-9-->
    </div><!--/row-->
<script>
    $(document).ready(function() {
        var profileImage = "<c:out value="${userinfo.profileImage}"/>";

        var profile = $(".profile-image");
        if (profileImage === ""){
            profile.html("<img class='img-thumbnail' src='/resources/image/profile.png'/>")
        }else{
            profile.html("<img class='img-thumbnail' src='/display?fileName=${userinfo.id}/profile/${userinfo.profileImage}'/>")
        }
    });

    $('#auth').click(function(){

        var email = $('#e_mail').val();
        var param = "email" + "=" + $("#e_mail").val();
        console.log(email);
        var e_mail = {
                email : email
            };
            alert("이메일이 전송되었습니다. 이메일 인증번호를 확인해주세요");
            mailService.sendEmail(e_mail, function (result) {
                for(var i=0; i<2; i++) {
                    var code = prompt("인증번호를 입력해주세요");
                    if (code == result) {
                        userService.changeAct(param, function (result) {
                            alert(result);
                            return;
                        });
                        return;
                    } else {
                        alert("인증번호가 틀렸습니다. 인증번호를 다시 확인해주세요");
                    }
                }
                alert("인증번호가 3회이상 틀렸습니다. 이메일 전송을 다시시도해주세요");
                location.href = "/user/info"

            });
    });



</script>
</body>