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
		float : right;
	}
	.table{
		text-align: center;
	}
</style>
<script>
</script>
</head>
<body>
	<div class="container">
		<h2>보유 쿠폰</h2>
		<form action="">
			<div class="btn-group" role="group" aria-label="Basic radio toggle button group">
			  <input type="radio" class="btn-check" name="btnradio" id="ra1" autocomplete="off" checked>
			  <label class="btn btn-outline-secondary" for="btnradio1">전체</label>
			
			  <input type="radio" class="btn-check" name="btnradio" id="ra2" autocomplete="off">
			  <label class="btn btn-outline-secondary" for="btnradio2">사용가능 쿠폰</label>
			
			  <input type="radio" class="btn-check" name="btnradio" id="ra3" autocomplete="off">
			  <label class="btn btn-outline-secondary" for="btnradio3">사용한 쿠폰</label>
			</div>
			<table class="table">
			  <thead>
			    <tr>
			      <th scope="col">쿠폰번호</th>
			      <th scope="col">쿠폰종류</th>
			      <th scope="col">할인율</th>
			      <th scope="col">만료일</th>
			    </tr>
			  </thead>
			  <tbody>
			  <c:forEach var="vo" items="${list}">
			    <tr>
			      <td>${vo.couponno}</td>
			      <td>${vo.name}</td>
			      <td>${vo.dcratio}</td>
			      <td>${vo.EDate}</td>
			    </tr>
			   </c:forEach>
			  </tbody>
			</table>
		</form>
	</div>
</body>
</html>
