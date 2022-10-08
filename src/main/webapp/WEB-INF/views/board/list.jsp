<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Board</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="JiSu Kim">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <!--===============================================================================================-->
    <link href="../css/search-bar.css" rel="stylesheet">
    <link href="../css/board/table-header.css" rel="stylesheet">

</head>

<body onload="changeType()">

<!-- ======= Header ======= -->
<jsp:include page="../header.jsp" flush="true"/>
<!-- End Header -->

<main class="container">
    <div class="row">
        <div class="card card-margin search-form">
            <div class="card-body p-0">
                <form id="search-form" action="/view/board" method="get">
                    <div class="row">
                        <div class="col-12">
                            <div class="row no-gutters">
                                <div id="appendDiv" class="col-lg-3 col-md-3 col-sm-12 p-0">
                                    <label for="search-type" hidden>검색 유형</label>
                                    <select class="form-control" id="search-type" name="searchType" onchange="changeType()">
                                        <option value="">선택</option>
                                        <option value="boardTitle" <c:if test="${search.searchType == 'boardTitle'}">selected="selected"</c:if> >제목</option>
                                        <option value="boardContent" <c:if test="${search.searchType == 'boardContent'}">selected="selected"</c:if> >내용</option>
                                        <option value="createAt" <c:if test="${search.searchType == 'createAt'}">selected="selected"</c:if> >작성일자</option>
                                        <option value="deleteYN" <c:if test="${search.searchType == 'deleteYN'}">selected="selected"</c:if> >삭제된 글</option>
                                    </select>
                                </div>
                                <div id="date1" class="col-lg-2 ">
                                </div>
                                <div id="date2" class="col-lg-2  ">
                                </div>
                                <div class="col-lg-8 col-md-6 col-sm-12 p-0">
                                    <label for="search-value" hidden>검색어</label>
                                    <input type="text" placeholder="검색어..." class="form-control" id="search-value" name="searchValue" value="${search.searchValue}">
                                </div>
                                <div class="col-lg-1 col-md-3 col-sm-12 p-0">
                                    <button type="button" class="btn btn-base" onclick="searchButton()">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                             viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                                             stroke-linecap="round" stroke-linejoin="round"
                                             class="feather feather-search">
                                            <circle cx="11" cy="11" r="8"></circle>
                                            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                                        </svg>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="row">
        <table class="table" id="board-table">
            <thead>
            <tr>
                <th class="board-id col-2"><a>글번호</a></th>
                <th class="title col-6"><a>제목</a></th>
                <th class="user-id"><a>작성자</a></th>
                <th class="created-at"><a>작성일</a></th>
            </tr>
            </thead>
            <tbody>
                <c:forEach var="board" items="${boards}">
                    <tr>
                        <td class="title"><a>${board.boardId}</a></td>
                        <td class="title"><a href="/view/board/${board.boardId}?page=${page}">${board.boardTitle}</a></td>
                        <td class="user-id">${board.boardUserName}</td>
                        <td class="created-at">
                            <fmt:parseDate value="${ board.createAt }" pattern="yyyy-MM-dd" var="parsedDateTime" type="both"/>
                            <fmt:formatDate pattern="yyyy.MM.dd" value="${ parsedDateTime }"/>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="row">
        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
            <a class="btn btn-primary me-md-2" role="button" id="write-article" href="/view/board/new?page=${page}">글쓰기</a>
        </div>
    </div>

    <div class="row">
        <nav id="pagination" ria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <li class="page-item"><a class="page-link <c:if test="${page -1 <=0}"> disabled</c:if>" href="/view/board?page=${page - 1}&searchType=${search.searchType}&searchValue=${search.searchValue}&startDate=${search.startDate}&endDate=${search.endDate}">Previous</a></li>
                <c:forEach var="pageNumber" items="${paginationBarNumbers}">
                    <li class="page-item"><a class="page-link <c:if test="${page == pageNumber + 1}"> disabled</c:if>" href="/view/board?page=${pageNumber + 1}&searchType=${search.searchType}&searchValue=${search.searchValue}&startDate=${search.startDate}&endDate=${search.endDate}">${pageNumber + 1}</a></li>
                </c:forEach>
                <li class="page-item"><a class="page-link <c:if test="${page >= total}"> disabled</c:if>" href="/view/board?page=${page + 1}&searchType=${search.searchType}&searchValue=${search.searchValue}&startDate=${search.startDate}&endDate=${search.endDate}">Next</a></li>
            </ul>
        </nav>
    </div>
</main>
<!-- End #main -->

<%--footer--%>
<jsp:include page="../footer.jsp" flush="true"/>
<%--end footer--%>

</body>
<script type="text/javascript" src="/js/board/board.js"></script>
</html>