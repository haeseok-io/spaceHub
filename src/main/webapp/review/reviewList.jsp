<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>spaceHub - 리뷰 목록</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<style>
	table{
		text-align: center;
	}
	a{
		text-decoration:none;
	}
</style>

</head>
<body>
	<div class="container">
		<form action="">
			<h3>내가 작성한 후기</h3>
			<table class="table">
			    <tr>
			      <th>공간이름</th>
			      <th>제목</th>
			      <th>내용</th>
			      <th>별점</th>
			      <th>등록일</th>
			    </tr>
	<c:forEach var="vo" items="${list}">
			    <tr>
			      <td scope="row"><a href="/spaceHub/space?cmd=detail&spaceno=${vo.spaceno}" class="link-secondary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">${vo.spacesubject}</a></td>
			      <td>${vo.subject}</td>
			      <td>${vo.contents}</td>
			      <td>${vo.rating}</td>
			      <td>${vo.regdate}</td>
			    </tr>
		</c:forEach>
			</table>
		</form>
	</div>
</body>
</html>