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
</head>
<body>
	<div class="container">
		<form action="">
			<h2>예약 리스트</h2>
			<table class="table">
			  <thead class="table-light">
			    <tr>
			      <th scope="col">예약번호</th>
			      <th scope="col">체크인</th>
			      <th scope="col">체크아웃</th>
			      <th scope="col">이름</th>
			      <th scope="col">전화번호</th>
			      <th scope="col">가격</th>
			      <th scope="col">게스트</th>
			      <th scope="col">할인</th>
			      <th scope="col">방번호</th>
			      <th scope="col">예약상태</th>
			      <th scope="col">예약자번호</th>
			      <th scope="col">ip</th>
			      <th scope="col">예약날짜</th>
			      <th></th>
			    </tr>
			  </thead>
			<c:forEach var="vo" items="${list}">
			  <tbody>
			    <tr>
			      <td scope="row">${vo.reservno}</td>
			      <td>${vo.checkin}</td>
			      <td>${vo.checkout}</td>
			      <td>${vo.name}</td>
			      <td>${vo.phone}</td>
			      <td>${vo.price}</td>
			      <td>${vo.guest}</td>
			      <td>${vo.dcratio}</td>
			      <td>${vo.spaceno}</td>
			      <td>${vo.status}</td>
			      <td>${vo.memno}</td>
			      <td>${vo.ip}</td>
			      <td>${vo.regdate}</td>
			    </tr>
			  </tbody>
			</c:forEach>
			</table>
		</form>
	</div>
</body>
</html>