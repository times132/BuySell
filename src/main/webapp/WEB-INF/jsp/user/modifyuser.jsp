<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
<script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
<script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
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
<sec:authentication property="principal" var="userinfo"/>
<div class="container">
    <div class="row">
        <div class="col-sm-10"><h1>내정보</h1></div>
        <div class="col-sm-2"><a href="/users" class="pull-right"><img title="profile image" class="img-circle img-responsive" src="http://www.gravatar.com/avatar/28fd20ccec6865e2d5f0e1f4446eb7bf?s=100"></a></div>
    </div>
    <div class="row">
        <div class="col-sm-3"><!--left col-->


            <div class="profile">
<%--                <img src="http://ssl.gstatic.com/accounts/ui/avatar_2x.png" class="avatar img-circle img-thumbnail" alt="avatar">--%>
                <img class="img-thumbnail" src="/display?fileName=${userinfo.id}/profile/${userinfo.profilePath}" onerror="this.src='/resources/image/profile.png';"/>
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
            </ul>


            <div class="tab-content">
                <div class="tab-pane active" id="home">
                    <hr>
                        <form class="form" action="/user/modifyuser" method="post">
                            <sec:authentication property="principal" var="userinfo"/>
                        <div class="form-group">

                            <div class="col-xs-6">
                                <label for="username"><H4>NICKNAME</H4></label>
                                <input type="text" class="form-control" id="username" name="username" value="${userinfo.username}" required>
                                <div class="check_font" id="username_check"></div><br>
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
                                <h4>EMAIL</h4>
                                <input type="text" class="form-control" name="email" value="${userinfo.email}" readonly="readonly" placeholder="enter phone" title="enter your phone number if any.">
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-xs-6">
                                <h4>PHONE NUMBER</h4>
                                <input type="text" class="form-control" name="phone" value="${userinfo.phone}"  placeholder="enter mobile number" title="enter your mobile number if any.">
                            </div>
                        </div>

                        <div class="form-group">

                            <div class="col-xs-6">
                                <h4>PASSWORD</h4>
                                <input type="password" class="form-control" name="password"  value="${userinfo.password}" placeholder="password" title="enter your password.">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-6">
                                <label for="password2"><h4>CONFIRM PASSWORD</h4></label>
                                <input type="password" class="form-control" name="password2" id="password2" placeholder="password2" title="enter your password2.">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-12">
                                <br>
                                <input type="hidden" name="id" value="${userinfo.id}">
                                <input type="hidden" name="authorities" value="${userinfo.authorities}">
                                <input class="btn btn-lg btn-success" type="submit" name="submit" id="submit" value="수정"/>
                                <input class="btn btn-lg" type="button" value="홈으로 이동" onClick="self.location='/';">
                            </div>
                        </div>
                    </form>

                    <hr>

                </div><!--/tab-pane-->
                <div class="tab-pane" id="myboards">


                </div><!--/tab-pane-->
                <div class="tab-pane" id="settings">

                </div>

            </div><!--/tab-pane-->
        </div><!--/tab-content-->

    </div><!--/col-9-->
</div><!--/row-->
<script>
    $(document).ready(function() {
        var profilepath = "<c:out value="${userinfo.profilePath}"/>";
        var userid = ${userinfo.id};
        var fileCallPath = encodeURIComponent(userid + "/profile/" + profilepath);

        // var str = "<img class='img-thumbnail' src='/display?fileName=" + fileCallPath + "' onerror='this.onerror=null; this.src='/resources/image/profile.png';'/>"
        //
        // $(".profile").html(str);

        $(".file-upload").on('change', function(){
            var formData = new FormData();
            var inputFile = $("input[name='uploadProfile']");
            var file = inputFile[0].files[0];

            formData.append("uploadProfile", file);

            $.ajax({
                type: "POST",
                url: "/user/uploadProfile",
                processData: false,
                contentType: false,
                data: formData,
                success: function (result) {
                    showUploadResult(result);
                },
                error: function (error) {
                    console.log("에러")
                }
            });
        });

        function showUploadResult(uploadResultArr) {
            if (!uploadResultArr || uploadResultArr.length == 0) {
                return;
            }

            var fileCallPath = encodeURIComponent(userid +"/profile/" + uploadResultArr);

            var str = "<img class='img-thumbnail' src='/display?fileName=" + fileCallPath + "'>";
            var str2 = "<input type='hidden' name='profilePath' value='" + uploadResultArr +"'/>"
            $("form").append(str2);
            $(".profile").html(str);
        }
    });


    // 아이디 유효성 검사(1 = 중복 / 0 != 중복)
    idck = 0;
    $("#username").blur(function() {
        var username = $("#username").val();
        $.ajax({
            url : '${pageContext.request.contextPath}/user/usernameCheck?username='+ username,
            type : 'get',
            success : function(data) {
                console.log("1 = 중복o / 0 = 중복x : "+ data);
                if (data == 1) {
                    //아이디가 존제할 경우 빨깡으로 , 아니면 파랑으로 처리하는 디자인
                    $("#username_check").text("사용중인 아이디입니다. 다른 아이디를 입력해주세요");
                    $("#username_check").css("color", "red");
                    $("#submit").attr("disabled", true);
                } else {
                    $("#username_check").text("사용가능한 아이디입니다.");
                    $("#username_check").css("color", "blue");
                    $("#submit").attr("disabled", false);
                    idck=1;
                }
                if(idck==1){
                    $("#submit")
                        .attr("disabled", false)
                }
            }, error : function() {
                console.log("실패");
            }
        });
    });
</script>
</body>