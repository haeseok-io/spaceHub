<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/common.jsp" />
	<title>spaceHub - 쿠폰목록</title>
	
	<link rel="stylesheet" href="https://uicdn.toast.com/calendar/latest/toastui-calendar.min.css" />
	
	<script src="https://uicdn.toast.com/calendar/latest/toastui-calendar.min.js"></script>
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
<jsp:include page="../../common/header.jsp" />
	<div class="main">
		<div class="inner">
			<div class="page-title">
				<div class="title-name">보유 쿠폰</div>
				<div class="btn-group" role="group" aria-label="Basic radio toggle button group">
				  <input type="radio" class="btn-check" name="btnradio" value="btn1" id="btnradio1" autocomplete="off" checked>
				  <label class="btn btn-outline-secondary" for="btnradio1">전체보기</label>
				
				  <input type="radio" class="btn-check" name="btnradio" value="btn2" id="btnradio2" autocomplete="off">
				  <label class="btn btn-outline-secondary" for="btnradio2">사용가능쿠폰</label>
				
				  <input type="radio" class="btn-check" name="btnradio" value="btn3" id="btnradio3" autocomplete="off">
				  <label class="btn btn-outline-secondary" for="btnradio3">사용한쿠폰</label>
				</div>
				</div>
		</div>
	</div>
	<div class="container">
		<form action="">
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
				  	<td>${vo.dcratio} %</td>
				  	<td>${vo.EDate}</td>
			    </tr>
			  </c:forEach>
			   
			  <c:forEach var="so" items="${slist}">
			    <tr id="ta2">
				  	<td>${so.couponno}</td>
				  	<td>${so.name}</td>
				  	<td>${so.dcratio} %</td>
				  	<td>${so.EDate}</td>
			    </tr>
			   </c:forEach>
			   
			  <c:forEach var="eo" items="${elist}">
			    <tr id="ta3">
				  	<td>${eo.couponno}</td>
				  	<td>${eo.name}</td>
				  	<td>${eo.dcratio} %</td>
				  	<td>${eo.EDate}</td>
			    </tr>
			   </c:forEach>
			   
			   </tbody>
			   
			</table>
		</form>
	</div>
</body>
</html>
