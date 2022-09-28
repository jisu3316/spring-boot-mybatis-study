<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Spring Boot Application with JSP</title>
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <script>
        function test() {
            let result = {"todoId": 5};
            $.ajax({
                url:"/news/" + 1,
                type: "PUT",
                dataType: "json",
                // data: result,
                contentType: "application/json",
                success: function (data) {
                    console.log(data);
                },
                error: function (request, status, error) {
                    alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
                    let err = JSON.parse(request.responseText);
                    console.log("에러: " + err.resultCode);
                    console.log("request.responseText: " + request.responseText);
                    if (err.resultCode === "POST_NOT_FOUND") {
                        alert("게시글이 없습니다");
                    }
                }
            })
        }

    </script>
</head>
<body>
Hello, Spring Boot App
<button onclick="test()"></button>
</body>
</html>