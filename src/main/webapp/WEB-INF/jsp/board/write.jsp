<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="/webjars/jquery/dist/jquery.min.js"></script>
</head>
<body>
    <form id="writeForm" action="/board/write" method="post">
        <sec:authentication property="principal" var="userinfo"/>
        <div>
            <label>분류 : </label>
            <select name="btype">
                <option value="">====</option>
                <option value="커피">커피</option>
                <option value="외식">외식</option>
                <option value="상품권">상품권</option>
                <option value="기타">기타</option>
            </select>
        </div>

        제목 : <input type="text" name="title"> <br>
        내용 : <input type="text" name="content"> <br>
        작성자 : <input type="text" name="writer" value="${userinfo.username}" readonly="readonly"> <br>
        가격 : <input type="text" name="price"> <br>

        <button type="submit">등록</button>
    </form>

    <script>
        $(document).ready(function () {
            var writeForm = $("#writeForm");
            $("#writeForm button").on("click", function (e) {
                if (!writeForm.find("option:selected").val()){
                    alert("검색종류를 선택하세요.");
                    return false;
                }
                if (!writeForm.find("input[name='title']").val()){
                    alert("제목을 입력하세요");
                    return false;
                }
                if (!writeForm.find("input[name='content']").val()){
                    alert("내용을 입력하세요");
                    return false;
                }
                if (!writeForm.find("input[name='price']").val()){
                    alert("가격을 입력하세요");
                    return false;
                }

                e.preventDefault();
                writeForm.submit();
            });
        });
    </script>
</body>
</html>
