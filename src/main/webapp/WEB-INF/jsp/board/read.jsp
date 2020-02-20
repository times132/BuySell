<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <script src="/webjars/jquery/dist/jquery.min.js"></script>
</head>
<body>

    <div>
        <label>제목</label>
        <input name="bid" value="<c:out value="${boardDto.title}"/>" readonly="readonly"/>
    </div>
    <div>
        <label>분류</label>
        <input name="bid" value="<c:out value="${boardDto.btype}"/>" readonly="readonly"/>
    </div>
    <div>
        <label>작성일</label>
        <input name="createddate" value="<javatime:format pattern="yyyy-MM-dd" value="${boardDto.createdDate}"/>" readonly="readonly"/>
    </div>

    <div>
        <label>판매자</label>
        <input name="writer" value="<c:out value="${boardDto.writer}"/>" readonly="readonly"/>
    </div>
    <div>
        <label>내용</label>
        <input name="content" value="<c:out value="${boardDto.content}"/>" readonly="readonly"/>
    </div>
    <div>
        <label>가격</label>
        <input name="price" value="<c:out value="${boardDto.price}"/>" readonly="readonly"/>
    </div>

    <button data-oper="modify">수정</button>
    <button data-oper="remove">삭제</button>
    <button data-oper="list">목록</button>

    <div>
        <a>댓글 제목</a>
        <a>댓글 내용</a>
    </div>


    <form id="operForm" action="/board/modify" method="get">
        <input type="hidden" id="bid" name="bid" value="<c:out value="${boardDto.bid}"/>">
        <input type="hidden" name="page" value="<c:out value="${cri.page}"/>">
        <input type="hidden" name="type" value="<c:out value="${cri.type}"/>">
        <input type="hidden" name="keyword" value="<c:out value="${cri.keyword}"/>">
    </form>
</body>

<script>
    $(document).ready(function () {

        var operForm = $("#operForm");

        $("button[data-oper='remove']").on("click", function (e) {
            operForm.attr("action", "/board/remove").attr("method", "post").submit();
        })

        $("button[data-oper='modify']").on("click", function (e) {
            operForm.attr("action", "/board/modify").submit();
        });

        $("button[data-oper='list']").on("click", function (e) {
            operForm.find("#bid").remove();
            operForm.attr("action", "/board");
            operForm.submit();
        });
    });
</script>
</html>
