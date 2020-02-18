<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <script src="/webjars/jquery/dist/jquery.min.js"></script>
</head>
<body>
    <form action="/board/modify" method="post">
        <input type="hidden" name="bid" value="${boardDto.bid}"/>
        <input type="hidden" name="page" value="<c:out value="${cri.page}"/>">
        <input type="hidden" name="type" value="<c:out value="${cri.type}"/>">
        <input type="hidden" name="keyword" value="<c:out value="${cri.keyword}"/>">

        분류 : <input type="text" name="btype" value="${boardDto.btype}" readonly="readonly"> <br>
        제목 : <input type="text" name="title" value="${boardDto.title}"> <br>
        내용 : <input type="text" name="content" value="${boardDto.content}"> <br>
        작성자 : <input type="text" name="writer" value="${boardDto.writer}" readonly="readonly"> <br>
        가격 : <input type="text" name="price" value="${boardDto.price}"> <br>

        <button data-oper="modify" type="submit">수정</button>
        <button data-oper="list" type="submit">목록</button>
    </form>

    <script>
        $(document).ready(function(){
            var formObj = $("form");

            $("button").on("click", function(e){
                e.preventDefault();

                var operation = $(this).data("oper");

                if(operation === "list"){
                    formObj.attr("action", "/board").attr("method", "get");

                    var pageTag = $("input[name='page']").clone();
                    var typeTag = $("input[name='type']").clone();
                    var keywordTag = $("input[name='keyword']").clone();

                    formObj.empty();
                    formObj.append(pageTag);
                    formObj.append(typeTag);
                    formObj.append(keywordTag);
                }else if (operation === 'modify'){
                    formObj.submit();
                }
                formObj.submit();
            });
        });
    </script>
</body>
</html>