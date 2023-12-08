<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<style>
	.btn-group{
		float : right;
	}
	.table{
		text-align: center;
	}
	#ta2, #ta3{
		display: none;
	}
</style>
<script>
	$(()=>{
		
		 $("#btnradio1").on("click",()=>{
			 window.location.href = '/spaceHub/mypage/guest?cmd=coupon';
		});
		
		$("#btnradio2").on("click",()=>{
			$("#ta1").hide();
			$("#ta2").show();
			$("#ta3").hide();
		});
		
		$("#btnradio3").on("click",()=>{
			$("#ta1").hide();
			$("#ta2").hide();
			$("#ta3").show();
		}); 
		
	});
</script>
</head>
<body>
	<div class="container">
		<h2>보유 쿠폰</h2>
		<form action="">
			<div class="btn-group" role="group" aria-label="Basic radio toggle button group">
			  <input type="radio" class="btn-check" name="btnradio" value="btn1" id="btnradio1" autocomplete="off" checked>
			  <label class="btn btn-outline-secondary" for="btnradio1">전체선택</label>
			
			  <input type="radio" class="btn-check" name="btnradio" value="btn2" id="btnradio2" autocomplete="off">
			  <label class="btn btn-outline-secondary" for="btnradio2">사용가능</label>
			
			  <input type="radio" class="btn-check" name="btnradio" value="btn3" id="btnradio3" autocomplete="off">
			  <label class="btn btn-outline-secondary" for="btnradio3">사용불가</label>
			</div>
			
			<table class="table">
			
			  <thead>
			    <tr>
			      <th>쿠폰번호</th>
			      <th>쿠폰종류</th>
			      <th>할인율</th>
			      <th>만료일</th>
			    </tr>
			  </thead>
			  
			  <tbody>
			  
			  <c:forEach var="vo" items="${list}">
			    <tr id="ta1">
				  	<td>${vo.couponno}</td>
				  	<td>${vo.name}</td>
				  	<td>${vo.dcratio}</td>
				  	<td>${vo.EDate}</td>
			    </tr>
			  </c:forEach>
			   
			  <c:forEach var="so" items="${slist}">
			    <tr id="ta2">
				  	<td>${so.couponno}</td>
				  	<td>${so.name}</td>
				  	<td>${so.dcratio}</td>
				  	<td>${so.EDate}</td>
			    </tr>
			   </c:forEach>
			   
			  <c:forEach var="eo" items="${elist}">
			    <tr id="ta3">
				  	<td>${eo.couponno}</td>
				  	<td>${eo.name}</td>
				  	<td>${eo.dcratio}</td>
				  	<td>${eo.EDate}</td>
			    </tr>
			   </c:forEach>
			   
			   </tbody>
			   
			</table>
		</form>
	</div>
</body>
</html>