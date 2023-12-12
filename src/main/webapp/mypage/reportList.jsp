<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
</head>
<body>
	<form action="/space">
	<input type="hidden" name="cmd" value="reportList"/>
	<input type="hidden" name="memno" value="${member.memno }"/>
	<div class="container">
		<c:forEach var="likeList" items="${list }">
		<table class="table">
			<tr>
				<th>숙소명</th>
				<td>${likeList.spaceSubject }</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${likeList.subject }</td>
			</tr>
			<tr>
				<th>작성시간</th>
				<td>${likeList.regdate }</td>
			</tr>
			<tr>
				<th>작성내용</th>
				<td>${likeList.contents }</td>
			</tr>
		</table>
		</c:forEach>
	</div>
	</form>
</body>
</html>