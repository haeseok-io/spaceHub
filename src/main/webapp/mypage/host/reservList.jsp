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
		<form action="">
			<h2>리스트</h2>
			<div class="btn-group">
			  <a href="/spaceHub/mypage/host?cmd=reservCalender&spaceno=${spaceno}" class="btn btn-outline-secondary" aria-current="page">달력</a>
			  <a href="#" class="btn btn-outline-secondary active">리스트</a>
			</div>
			<table class="table">
			    <tr>
			      <th scope="col">예약번호</th>
			      <th scope="col">이름</th>
			      <th scope="col">체크인</th>
			      <th scope="col">체크아웃</th>
			      <th scope="col">전화번호</th>
			      <th scope="col">인원</th>
			      <th scope="col">가격</th>
			    </tr>
			<c:forEach var="vo" items="${list}">
			    <tr>
			      <td scope="row">${vo.reservno}</td>
			      <td>${vo.name}</td>
			      <td>${vo.checkin}</td>
			      <td>${vo.checkout}</td>
			      <td>${vo.phone}</td>
			      <td>${vo.guest}</td>
			      <td>${vo.price}</td>
			    </tr>
			    <tr>
			    </tr>
			</c:forEach>
			</table>
		</form>
	</div>
</body>
</html>