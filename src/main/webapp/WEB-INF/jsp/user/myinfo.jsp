<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
<link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
<script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>


<!------ Include the above in your HEAD tag ---------->

<head>
    <title>Bootstrap Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

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

                                <div class="col-xs-6">
                                    <h4>PASSWORD</h4>
                                    <input type="password" class="form-control" name="password"  value="${userinfo.password}" placeholder="password" title="enter your password.">
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

                        <hr>

                    </div><!--/tab-pane-->
                    <div class="tab-pane" id="myboards">

                        <h2></h2>

                        <hr>
                        <form class="form" action="##" method="post">


                        </form>

                    </div><!--/tab-pane-->
                    <div class="tab-pane" id="settings">


                        <hr>
                        <form class="form" action="##" method="post" >

                        </form>
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
</script>
</body>