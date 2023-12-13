<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>spaceHub - 등록 카드 목록</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
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
		<form action="">
			<h2>카드 리스트</h2>
			<table class="table">
			  <thead class="table-light">
			    <tr>
			      <th scope="col">카드번호</th>
			      <th scope="col">만료일</th>
			      <th scope="col">지역</th>
			      <th></th>
			    </tr>
			  </thead>
			<c:forEach var="vo" items="${list}" varStatus="status">
			  <tbody>
			    <tr>
			      <td scope="row" id="cardNum">${vo.cardNum}</td>
			      <td>${vo.EDate}</td>
			      <td>${vo.loc}</td>
			      <td><a href="/spaceHub/mypage/guest?cmd=cardDelete&mcardno=${vo.mcardno}" class="link-danger link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">삭제</a></td>
			    </tr>
			  </tbody>
			</c:forEach>
			</table>
		</form>
	</div>
</body>
</html>