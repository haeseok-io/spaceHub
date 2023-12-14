<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/common.jsp" />
	<title>spaceHub - 내가 작성한 후기</title>
	
	<link rel="stylesheet" href="https://uicdn.toast.com/calendar/latest/toastui-calendar.min.css" />
	
	<script src="https://uicdn.toast.com/calendar/latest/toastui-calendar.min.js"></script>
	<style>
		table{
			text-align: center;
		}
		a{
			text-decoration:none;
		}
	</style>
<jsp:include page="../common/header.jsp" />
	<div class="main">
		<div class="inner">
			<div class="page-title">
				<div class="title-name">내가 작성한 후기</div>
			</div>
		</div>
	</div>
	<div class="container">
		<form action="">
			<table class="table">
			    <tr>
			      <th>공간이름</th>
			      <th>제목</th>
			      <th>내용</th>
			      <th>별점</th>
			      <th>등록일</th>
			    </tr>
	<c:forEach var="vo" items="${list}">
			    <tr>
			      <td scope="row"><a href="/spaceHub/space?cmd=detail&spaceno=${vo.spaceno}" class="link-secondary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">${vo.spacesubject}</a></td>
			      <td>${vo.subject}</td>
			      <td>${vo.contents}</td>
			      <td>${vo.rating}</td>
			      <td>${vo.regdate}</td>
			    </tr>
		</c:forEach>
			</table>
		</form>
	</div>
</body>
</html>