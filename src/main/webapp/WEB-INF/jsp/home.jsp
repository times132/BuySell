<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>메인</title>
    <script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>

    <link href="/resources/css/home.css" rel="stylesheet">
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
        <div class="row justify-content-center pt-0 mb-5 mr-0 ml-0">
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

        <div class="row justify-content-center cartWrap pl-0 mr-0 ml-0">
            <div class="cartInner">
                <ul class="cartList pl-0 mb-0">
                    <div class="col-12">
                        <div class="row justify-content-center pl-0">
                            <div class="cart-row col-12 col-md-10 col-lg-6">
                                <li class="item cartItem0">
                                    <img src="/resources/image/home/whole.png" alt="whole"/>
                                    <p>전체보기</p>
                                </li>
                                <li class="item cartItem0">
                                    <img src="/resources/image/home/eatout.png" alt="외식"/>
                                    <p>외식</p>
                                </li>
                                <li class="item cartItem0">
                                    <img src="/resources/image/home/cafe.png" alt="카페"/>
                                    <p>카페</p>
                                </li>
                                <li class="item cartItem0">
                                    <img src="/resources/image/home/convenience.png" alt="편의점"/>
                                    <p>편의점</p>
                                </li>
                                <li class="item cartItem0">
                                    <img src="/resources/image/home/culture.png" alt="문화"/>
                                    <p>문화</p>
                                </li>
                            </div>
                            <div class="cart-row col-12 col-md-10 col-lg-6">
                                <li class="item cartItem0">
                                    <img src="/resources/image/home/mobile.png" alt="모바일"/>
                                    <p>모바일</p>
                                </li>
                                <li class="item cartItem0">
                                    <img src="/resources/image/home/concert.png" alt="콘서트"/>
                                    <p>콘서트</p>
                                </li>
                                <li class="item cartItem0">
                                    <img src="/resources/image/home/movie.png" alt="영화"/>
                                    <p>영화</p>
                                </li>
                                <li class="item cartItem0">
                                    <img src="/resources/image/home/musical.png" alt="뮤지컬"/>
                                    <p>뮤지컬</p>
                                </li>
                                <li class="item cartItem0">
                                    <img src="/resources/image/home/drama.png" alt="연극"/>
                                    <p>연극</p>
                                </li>
                            </div>
                        </div>
                    </div>

                    <div class="col-12">
                        <div class="row justify-content-center pl-0">
                            <div class="cart-row col-12 col-md-10 col-lg-6">
                                <li class="item cartItem0">
                                    <img src="/resources/image/home/appliance.png" alt="가전"/>
                                    <p>가전</p>
                                </li>
                                <li class="item cartItem0">
                                    <img src="/resources/image/home/furniture.png" alt="가구"/>
                                    <p>가구</p>
                                </li>
                                <li class="item cartItem0">
                                    <img src="/resources/image/home/food.png" alt="식품"/>
                                    <p>식품</p>
                                </li>
                                <li class="item cartItem0">
                                    <img src="/resources/image/home/cosmetic.png" alt="뷰티"/>
                                    <p>뷰티</p>
                                </li>
                                <li class="item cartItem0">
                                    <img src="/resources/image/home/clothes.png" alt="의류"/>
                                    <p>의류</p>
                                </li>

                            </div>
                            <div class="cart-row col-12 col-md-10 col-lg-6">
                                <li class="item cartItem0">
                                    <img src="/resources/image/home/kids.png" alt="아동"/>
                                    <p>아동</p>
                                </li>
                                <li class="item cartItem0">
                                    <img src="/resources/image/home/pet.png" alt="애견"/>
                                    <p>애견</p>
                                </li>
                                <li class="item cartItem0">
                                    <p class="blank">&nbsp</p>
                                </li>
                                <li class="item cartItem0">
                                    <p class="blank">&nbsp</p>
                                </li>
                                <li class="item cartItem0">
                                    <p class="blank">&nbsp</p>
                                </li>
                            </div>
                        </div>
                    </div>
                </ul>
            </div>
        </div>

        <div class="row justify-content-center pt-0 mr-0 ml-0">
            <div class="itemList">
                <table class="table table-sm table-hover mb-0">
                    <tbody class="item-body">

                    </tbody>
                </table>
            </div>


        </div>

        <form id="actionForm" action="/board" method="get">
        </form>
    </div>
    <script type="text/javascript" src="/resources/js/board.js"></script>
    <script type="text/javascript" src="/resources/js/common.js"></script>
    <script src="/resources/js/jquery.number.min.js"></script>
    <script>
        var actionForm = $("#actionForm");
        $(".price").number(true);
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

        var itemList = $(".itemList");
        var itemTable = $(".item-body");
        $(".item").on("click", function () {
            itemList.show();
            var itemName = $(this).children("img").attr("alt");
            str = "";
            boardService.getItemList(itemName, function (data) {
                for (var i = 0, len = data.length || 0; i < len; i++){

                    str += "<tr><th style='width: 6%'>" + data[i].bid + "</th>";
                    if (data[i].sellCheck){
                        str += "<td class='titleSold' style='width: 44%'><a class='move' href='" + data[i].bid + "'>" + data[i].title + "</a>" + "<span class='soldReplyCnt'>" + "\[" +data[i].replyCnt + "\]" + "</span><span class='sold'>완료</span></td>";
                    }else{
                        str += "<td class='title' style='width: 44%'><a class='move' href='" + data[i].bid + "'>" + data[i].title + "</a>" + "<span class='replyCnt'>" + "\[" +data[i].replyCnt + "\]" + "</span></td>";
                    }
                    str += "<td class='price' style='width: 10%'>" + "&#8361;" +data[i].price.toLocaleString();
                    str += "</td><td class='writer' style='width: 10%'>" + data[i].writer + "</td><td class='time' style='width: 8%' '>" + commonService.displayTime(data[i].createdDate);
                    str += "</td><td class='viewCnt' style='width: 5%'>" + data[i].viewCnt + "</td><td class='likeCnt' style='width: 5%'>" + data[i].likeCnt + "</td></tr>";
                }
                itemTable.html(str);
            });
        });

        $(document).on("click", ".move" ,function (e) {
            e.preventDefault();
            actionForm.append("<input type='hidden' name='bid' value='"+$(this).attr("href")+"'>");
            actionForm.attr("action", "/board/read");
            actionForm.submit();
        });
    </script>
</body>
</html>