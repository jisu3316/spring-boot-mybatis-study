<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Uno Kim">
    <title>Board</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
</head>

<body>

<!-- ======= Header ======= -->
<jsp:include page="../header.jsp" flush="true"/>
<!-- End Header -->


<div class="container">
    <header id="article-form-header" class="py-5 text-center">
        <h1>게시글 작성</h1>
    </header>

    <form id="board-form">
        <div class="row mb-3 justify-content-md-center">
            <label for="boardTitle" class="col-sm-2 col-lg-1 col-form-label text-sm-end">제목</label>
            <div class="col-sm-8 col-lg-9">
                <input type="text" class="form-control" id="boardTitle" name="boardTitle" value="" required>
            </div>
        </div>
        <div class="row mb-3 justify-content-md-center">
            <label for="boardContent" class="col-sm-2 col-lg-1 col-form-label text-sm-end">내용</label>
            <div class="col-sm-8 col-lg-9">
                <textarea class="form-control" id="boardContent" name="boardContent" rows="5" value="" required></textarea>
            </div>
        </div>
        <div class="row mb-4 justify-content-md-center">
            <label for="boardUserName" class="col-sm-2 col-lg-1 col-form-label text-sm-end">작성자</label>
            <div class="col-sm-8 col-lg-9">
                <input type="text" class="form-control" id="boardUserName" name="boardUserName" value="">
            </div>
        </div>
        <div class="row mb-4 justify-content-md-center">
            <label for="boardPassword" class="col-sm-2 col-lg-1 col-form-label text-sm-end">비밀번호</label>
            <div class="col-sm-8 col-lg-9">
                <input type="password" class="form-control" id="boardPassword" name="boardPassword" value="">
            </div>
        </div>
        <div class="row mb-5 justify-content-md-center">
            <div class="col-sm-10 d-grid gap-2 d-sm-flex justify-content-sm-end">
                <button type="button" class="btn btn-primary" id="submit-button" onclick="createBoard(${page})">저장</button>
                <button type="button" class="btn btn-secondary" id="cancel-button">취소</button>
            </div>
        </div>
    </form>
</div>

<!-- ======= Footer ======= -->
<jsp:include page="../footer.jsp" flush="true"/>
<!-- End Footer -->
</body>
<script type="text/javascript" src="/js/board/board.js"></script>
</html>