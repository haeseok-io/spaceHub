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
	.table{
		text-align: center;
	}
</style>
</head>
<body>
	<div class="container">
		<form action="">
			<h3>내가 작성한 후기</h3>
			<table class="table">
			  <thead>
			    <tr>
			      <th scope="col" colspan="3">예약후기</th>
			    </tr>
			  </thead>
	<c:forEach var="vo" items="${list}">
			  <tbody>
			    <tr>
			      <th scope="row">${vo.reservno}</th>
			      <td>${vo.subject}</td>
			      <td style="text-align: right;">${vo.regdate}</td>
			    </tr>
			    <tr>
			    	<td colspan="3">${vo.contents}</td>
			    </tr>
			  </tbody>
		</c:forEach>
			</table>
		</form>
	</div>
</body>
</html>