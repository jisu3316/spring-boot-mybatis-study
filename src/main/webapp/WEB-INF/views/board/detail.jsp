<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Uno Kim">
    <title>Board</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <link href="/css/board/board-content.css" rel="stylesheet">
</head>

<body>

<!-- ======= Header ======= -->
<jsp:include page="../header.jsp" flush="true"/>
<!-- End Header -->

<main id="article-main" class="container">
    <header id="article-header" class="py-5 text-center">
        <h1><strong>${board.boardTitle}</strong></h1>
    </header>

    <div class="row g-5">
        <section class="col-md-3 col-lg-4 order-md-last">
            <aside>
                <p><span id="boardUserName">작성자 : ${board.boardUserName}</span></p>
<%--                <p><a id="email" href="mailto:djkehh@gmail.com">jisu3316@naver.com</a></p>--%>
                <p><time id="createdAt" >작성일 :
                    <fmt:parseDate value="${ board.createAt }" pattern="yyyy-MM-dd" var="parsedDateTime" type="both" />
                    <fmt:formatDate pattern="yyyy-MM-dd" value="${ parsedDateTime }" /></time></p>
<%--                <p><span id="hashtag">#java</span></p>--%>
            </aside>
        </section>

        <article id="boardContent" class="col-md-9 col-lg-8">
            <h3><pre>${board.boardContent}</pre></h3>
        </article>
    </div>

    <div class="row g-5" id="article-buttons">
        <form id="delete-article-form">
            <div class="pb-5 d-grid gap-2 d-md-block">
                <a class="btn btn-success me-md-2" role="button" id="update-article" href="/view/board/${board.boardId}/form?page=${page}">수정</a>
                <button class="btn btn-danger me-md-2" type="button" onclick="deleteBoard(${board.boardId})">삭제</button>
            </div>
        </form>
    </div>

    <div class="row g-5">
        <section>
            <form class="row g-3" id="comment-form">
                <input type="hidden" id="boardId" value="${board.boardId}">
                <input type="hidden" class="article-id">
                <div>
                    <label for="userName">작성자</label>
                    <input type="text" id="userName" name="userName" value="">
                    <label for="userPassword">비밀번호</label>
                    <input type="password" id="userPassword" name="userPassword" value="">
                </div>
                <div class="col-md-9 col-lg-8">
                    <label for="comment" hidden>댓글</label>
                    <textarea class="form-control" id="comment" placeholder="댓글 쓰기.." rows="3" value="" name="comment" required></textarea>
                </div>
                <div class="col-md-3 col-lg-4">
                    <label for="comment-submit" hidden>댓글 쓰기</label>
                    <button class="btn btn-primary" id="comment-submit" type="button" onclick="postComment()">댓글 쓰기</button>
                </div>
            </form>

            <ul id="board-comments" class="row col-md-10 col-lg-8 pt-3">
                <c:forEach var="comment" items="${comments}" varStatus="status">
                    <li id="li${comment.commentId}">
                        <div class="row">
                            <c:choose>
                                <c:when test="${comment.step > 0}">
                                    <div class="col-md-10 col-lg-8">
                                        <strong>
                                        <c:forEach begin="1" end="${comment.step}" step="1">
                                            &rarr;
                                        </c:forEach>
                                                ${status.index + 1} </strong></br>
                                        <strong>
                                            <c:forEach begin="1" end="${comment.step}" step="1">
                                                &nbsp;&nbsp;&nbsp;
                                            </c:forEach>
                                            작성자 : ${comment.commentUserName}</strong>
                                        <p>
                                            <c:forEach begin="1" end="${comment.step}" step="1">
                                                &nbsp;&nbsp;&nbsp;
                                            </c:forEach>
                                            댓글 내용 : ${comment.comment}</p>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="col-md-10 col-lg-8">
                                        <strong>${status.index + 1}</strong></br>
                                        <strong>작성자 : ${comment.commentUserName}</strong>
                                        <p>댓글 내용 : ${comment.comment}</p>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <div class="col-md-3 col-lg-4 ">
                                <button type="button" class="btn btn-outline-primary" onclick="reCommentForm(${comment.commentId})">답글</button>
                                <button type="button" class="btn btn-outline-success" onclick="updateFormComment('${comment.commentUserName}', '${comment.comment}', ${comment.commentId})">수정</button>
                                <button type="button" class="btn btn-outline-danger" onclick="deleteComment(${comment.commentId}, ${board.boardId})">삭제</button>
                             </div>
                        </div>
                        <div id="updateDiv${comment.commentId}">
                        </div>
                    </li>
                </c:forEach>
            </ul>

        </section>
    </div>

<%--    <div class="row g-5">--%>
<%--        <nav id="pagination" aria-label="Page navigation">--%>
<%--            <ul class="pagination">--%>
<%--                <li class="page-item">--%>
<%--                    <a  class="page-link " href="/view/board/${board.boardId - 1}/page?page=${page}&previous=previous"  aria-label="Previous">--%>
<%--                        <span aria-hidden="true">&laquo; prev</span>--%>
<%--                    </a>--%>
<%--                </li>--%>
<%--                <li class="page-item">--%>
<%--                    <a class="page-link " href="/view/board/${board.boardId + 1}/page?page=${page}&next=next" aria-label="Next">--%>
<%--                        <span aria-hidden="true">next &raquo;</span>--%>
<%--                    </a>--%>
<%--                </li>--%>
<%--            </ul>--%>
<%--        </nav>--%>
<%--    </div>--%>
</main>

<!-- ======= Footer ======= -->
<jsp:include page="../footer.jsp" flush="true"/>
<!-- End Footer -->

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/board/board.js"></script>
<script type="text/javascript" src="/js/comment/comment.js"></script>
</body>
</html>