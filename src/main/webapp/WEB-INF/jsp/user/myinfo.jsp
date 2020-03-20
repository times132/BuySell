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


            </div><!--/col-3-->
            <div class="col-sm-9">
                <ul class="nav nav-tabs">
                    <li class="nav-item"><a  class="nav-link active" data-toggle="tab" href="#home">내정보</a></li>
                    <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#myboards">Board</a></li>
                    <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#email">이메일 인증</a></li>
                    <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#withdrawal">회원탈퇴</a></li>
                </ul>



                <!--------------------------------------내정보---------------------------------------------------->
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
                                    <input class="btn btn-primary btn-sm" type="button" value="홈으로 이동" onClick="self.location='/';">
                                </div>
                            </div>
                        </form>


                    </div>


                <!--------------------------------------게시판---------------------------------------------------->

                <div class="tab-pane" id="myboards">

                        <h2></h2>

                        <hr>
                        <form class="form" action="##" method="post">


                        </form>

                </div><!--/tab-pane-->
                <!--------------------------------------이메일인증---------------------------------------------------->
                <div class="tab-pane" id="email">
                        <sec:authorize access="hasRole('GUEST')">
                        <br>
                        <br>
                        <h6>이메일 인증 (이메일인증을 받으시면 보다 나은 서비스를 이용할 수 있습니다.)</h6>
                        <br> <br>
                            <label for="email">이메일</label>
                            <input class="form-control" type="text" id="e_mail" name="email" value="${userinfo.email}" readonly="readonly"  required><br>
                            <input class="btn btn-primary btn-sm" type="button" id="auth" value="이메일 인증받기"/>
                        </sec:authorize>


                        <sec:authorize access="hasAnyRole('ADMIN', 'USER')">
                        <br>
                        <br>
                            <h6>이메일 인증 완료</h6>
                            <input class="form-control" type="text"  value="${userinfo.email}" readonly="readonly" required><br>

                        </sec:authorize>
                </div>
                <!-------------------------------------- 회원탈퇴 ---------------------------------------------------->
                <div class="tab-pane" id="withdrawal">
                        <form action="/user/delete" method="post">

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

                            <button class="btn btn-danger btn-sm" id="submit" type="submit">회원탈퇴</button>
                        </form>
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
        console.log(email);
        var e_mail = {
                email : email
            };
            alert("이메일이 전송되었습니다. 이메일 인증번호를 확인해주세요");
            mailService.sendEmail(e_mail, function (result) {
                for(var i=0; i<2; i++) {
                    var code = prompt("인증번호를 입력해주세요");
                    if (code == result) {
                        userService.changeAct(e_mail, function (result) {
                            alert(result);
                            location.href = "/user/info";
                            return;
                        });
                        return;
                    } else {
                        alert("인증번호가 틀렸습니다. 인증번호를 다시 확인해주세요");
                    }
                }
                alert("인증번호가 3회이상 틀렸습니다. 이메일 전송을 다시시도해주세요");
                location.href = "/user/info";
            });
    });

    $(function(){
        $("#alert-success").hide();
        $("#alert-danger").hide();
        $("input").keyup(function(){
            var pwd1=$("#pwd1").val(); var pwd2=$("#pwd2").val();
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

</script>
</body>