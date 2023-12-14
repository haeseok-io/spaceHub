<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/common.jsp" />
	<title>spaceHub - 카드목록</title>
	
	<link rel="stylesheet" href="https://uicdn.toast.com/calendar/latest/toastui-calendar.min.css" />
	
	<script src="https://uicdn.toast.com/calendar/latest/toastui-calendar.min.js"></script>
	<style>
	.table{
		text-align: center;
	}
	</style>
<jsp:include page="../../common/header.jsp" />
	<div class="main">
		<div class="inner">
			<div class="page-title">
				<div class="title-name">카드 목록</div>
			</div>
		</div>
	</div>
	<div class="container">
		<form action="">
			<table class="table">
			  <thead class="table">
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