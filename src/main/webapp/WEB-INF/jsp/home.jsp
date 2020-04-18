<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>메인</title>
    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
    <link href="/resources/css/home.css" rel="stylesheet">
    <style>
        .has-search{
        }
        .has-search .form-control {
            padding-left: 2.375rem;
            border: #999 2px solid;

        }

        .has-search .form-control-feedback {
            position: absolute;
            z-index: 2;
            display: block;
            width: 2.375rem;
            height: 2.375rem;
            line-height: 2.375rem;
            text-align: center;
            pointer-events: none;
            color: #aaa;
        }
        .search-bar{
            /*padding-top: 5px;*/
            /*padding-left: 10px;*/
            /*padding-bottom: 0px;*/
            justify-content: center!important;
            display: -ms-flexbox;
            display: flex;
            -ms-flex-wrap: wrap;
            flex-wrap: wrap;
            padding: 20px;
            align-items: center;
            border-bottom: #5467c9 3px solid;
        }
        .search-bar h1{
            margin-bottom: 0;
            font-size: 3rem;
        }
        .carousel-indicators li{
            background-color: black!important;
        }
        .carousel-control-next-icon,
        .carousel-control-prev-icon {
            filter: invert(1);
        }
        .carousel{
            width:1080px;
            height :auto;
            max-height: 480px;
            margin-right: 1rem;
            display: flex;
            background: white;
        }
        .carousel-inner{
            display: flex;
            align-items: center;

        }
        .d-block{
            margin: 0 auto;
            overflow: hidden;
        }
        .carousel-item.active{
            overflow: hidden;
        }
        .cartWrap{
            list-style: none;
        }
        .cartList{
            text-align: center;
        }
        .cartList li{
            display: inline-block;
            list-style: none;
            border: black 2px solid;
            max-width: 104px;
            min-width: 96px;
            max-height: 104px;
            min-height: 96px;
            margin: 0 0;

        }
        .cartList li img{
            margin: 5px 0;
        }
        .cartList li p{
            font-size: .7rem;
            margin-bottom: 0;
        }
        @media screen and (min-width: 1000px) {
            .col-lg-6 {
                -ms-flex: 0 0 50%;
                flex: 0 0 50%;
                max-width: 50%;
            }
        }
        .cart-row{
            padding: 0 2px!important;
        }
    </style>
</head>
<body>
    <%@include file="../jsp/include/header.jsp"%>
    <div class="container-fluid pr-0 pl-0">
        <!-- 통합 검색창 -->
        <div class="search-bar">
            <div class="col-lg-3">
                <h1><a href="/">BUYSELL</a></h1>
            </div>
            <div class="col-lg-5">
                <form id="search" action="/board" method="get">
                    <input name="type" type="hidden" value="TC">
                    <input name="page" type="hidden" value="1">
                    <div class="has-search">
                        <span class="fa fa-search form-control-feedback"></span>
                        <input type="text" class="form-control" placeholder="Search">
                    </div>
                </form>
            </div>
        </div>

        <!-- 공지사항 인디케이터 -->
        <div class="row justify-content-center pt-0 mb-5">
            <div id="carousel-example-1z" class="carousel slide carousel-fade" data-ride="carousel">

                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-1z" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-1z" data-slide-to="1"></li>
                    <li data-target="#carousel-example-1z" data-slide-to="2"></li>
                </ol>

                <div class="carousel-inner" role="listbox">
                    <div class="carousel-item active">
                        <img class="d-block w-100" src="/resources/image/home/main1.jpg"
                             alt="First slide">
                    </div>

                    <div class="carousel-item">
                        <img class="d-block w-100" src="/resources/image/home/main2.jpg"
                             alt="Second slide">
                    </div>

                    <div class="carousel-item">
                        <img class="d-block w-100" src="/resources/image/home/main3.jpg"
                             alt="Third slide">
                    </div>
                </div>
                <!--/.Slides-->
                <!--Controls-->
                <a class="carousel-control-prev" href="#carousel-example-1z" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#carousel-example-1z" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
                <!--/.Controls-->
            </div>
        </div>

        <div class="row justify-content-center cartWrap pl-0">
            <div class="cartInner">
                <ul class="cartList pl-0">
                    <div class="col-12">
                        <div class="row justify-content-center pl-0">
                            <div class="cart-row col-12 col-md-10 col-lg-6">
                                <li class="cartItem0">
                                    <a>
                                        <img src="/resources/image/home/whole.png"/>
                                        <p>전체보기</p>
                                    </a>
                                </li>
                                <li class="cartItem1"></li>
                                <li class="cartItem2"></li>
                                <li class="cartItem3"></li>
                                <li class="cartItem4"></li>
                            </div>
                            <div class="cart-row col-12 col-md-10 col-lg-6">
                                <li class="cartItem5"></li>
                                <li class="cartItem6"></li>
                                <li class="cartItem7"></li>
                                <li class="cartItem8"></li>
                                <li class="cartItem9"></li>
                            </div>
                        </div>
                    </div>

                    <div class="col-12">
                        <div class="row justify-content-center pl-0">
                            <div class="cart-row col-12 col-md-10 col-lg-6 pr-0 pl-0">
                                <li class="cartItem10"></li>
                                <li class="cartItem11"></li>
                                <li class="cartItem12"></li>
                                <li class="cartItem13"></li>
                                <li class="cartItem14"></li>
                            </div>
                            <div class="cart-row col-12 col-md-10 col-lg-6 pr-0 pl-0">
                                <li class="cartItem15"></li>
                                <li class="cartItem16"></li>
                                <li class="cartItem17"></li>
                                <li class="cartItem18"></li>
                                <li class="cartItem19"></li>
                            </div>
                        </div>
                    </div>

                </ul>
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
        var cate = ${category[0].items[0].itemName};
        console.log(cate)
        // for (var i = 1; i < cate.length; i++){
        //     console.log(cate[i].items)
        // }
    </script>
</body>
</html>