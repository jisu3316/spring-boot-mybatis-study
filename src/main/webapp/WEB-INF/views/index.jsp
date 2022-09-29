<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Spring Boot Application with JSP</title>
</head>
<body>
Hello, Spring Boot Appa

<c:forEach var="list" items="${list}">
<c:forEach var="list2" items="${list.value}">
  ${list2.id}
  ${list2.completed}
  ${list2.todoName}
</c:forEach>
<%--  ${list.key}--%>
<%--  ${list.value}--%>
</c:forEach>

<div class="row">
  <nav id="pagination" ria-label="Page navigation">
    <ul class="pagination justify-content-center">
      <li class="page-item"><a class="page-link" href="#">Previous</a></li>
      <c:forEach var="pageNumber" items="${paginationBarNumbers}">
        <li class="page-item"><a class="page-link" href="#">${pageNumber + 1}</a></li>
      </c:forEach>
      <li class="page-item"><a class="page-link" href="#">Next</a></li>
    </ul>
  </nav>
</div>
</body>
</html>