<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/common.jsp" />
	<title>spaceHub - 리스트</title>
	
	<link rel="stylesheet" href="https://uicdn.toast.com/calendar/latest/toastui-calendar.min.css" />
	
	<script src="https://uicdn.toast.com/calendar/latest/toastui-calendar.min.js"></script>
	<style>
	.btn-group{
		float:right;
	}
	.table{
		text-align: center;
	}
</style>
<jsp:include page="../../common/header.jsp" />
	<div class="main">
		<div class="inner">
			<div class="page-title">
				<div class="title-name">리스트</div>
				<div class="btn-group">
				  <a href="/spaceHub/mypage/host?cmd=reservCalender&spaceno=${spaceno}" class="btn btn-outline-secondary" aria-current="page">달력</a>
				  <a href="#" class="btn btn-outline-secondary active">리스트</a>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
		<form action="">
			<table class="table">
			    <tr onclick="yet()">
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
			</c:forEach>
			</table>
		</form>
	</div>
</body>
</html>