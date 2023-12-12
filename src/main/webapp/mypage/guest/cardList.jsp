<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<style>
	.btn-group{
		float:right;
	}
	.table{
		text-align: center;
	}
</style>
</head>
<body>
	<div class="container">
		<form action="/spaceHub/mypage/guest">
			<h2>카드 리스트</h2>
			<c:forEach var="vo" items="${list}">
			<input type="hidden" name="cmd" value="cardDelete" />
			<input type="hidden" name="mcardno" value="${vo.mcardno}" />
			<table class="table">
			  <thead class="table-light">
			    <tr>
			      <th scope="col">카드번호</th>
			      <th scope="col">만료일</th>
			      <th scope="col">지역</th>
			      <th></th>
			    </tr>
			  </thead>
			  <tbody>
			    <tr>
			      <td scope="row">${vo.cardNum}</td>
			      <td>${vo.EDate}</td>
			      <td>${vo.loc}</td>
			      <td><button type="submit" class="btn btn-outline-secondary">카드 삭제</button></td>
			    </tr>
			  </tbody>
			</table>
			</c:forEach>
		</form>
	</div>
</body>
</html>