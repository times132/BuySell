<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chatting room</title>
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <link href="/resources/css/header.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" type="text/css" rel="stylesheet">
    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
    <script type="text/javascript" src="/resources/js/chat.js"></script>

</head>
<body>
<br>
<div id="search-bar">
        <div class="row row1">
            <div class="cart col-lg-3">
                <h1><a href="/">BUYSELL</a></h1>
            </div>
            <div class="navbar-search col-lg-7">
                    <form id="search" action="/board" method="get">
                        <input name="keyword" class="navbar-input col-xs-11" type="" placeholder="Search for Products">
                        <button class="navbar-button col-xs-1">
                                <i width="30" height="30" class="fa fa-search" aria-hidden="true"></i>
                        </button>
                        <input name="type" type="hidden" value="TC">
                        <input name="page" type="hidden" value="1">
                    </form>
            </div>
        </div>
</div>
<script>
    $("#logout").click(function() {
        location.href="/user/logout";
    });

    var search = $("#search");
    $("#search button").on("click", function (e) {
        if (!search.find("input[name='keyword']").val()){
            alert("키워드를 입력하세요.");
            return false;
        }
        e.preventDefault();
        search.submit();
    });
</script>
</body>