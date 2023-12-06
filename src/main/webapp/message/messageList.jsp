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
	a{
		text-decoration-line:none;
	}
	.table table-hover{
		text-align: center;
	}
</style>
</head>
<body>
	<div class="container">
		<form action="">
			<h3>메세지</h3>
			<table class="table table-hover">
			<thead>
			    <tr>
			      <th scope="col" colspan="3">대화중인 메세지</th>
			    </tr>
			  </thead>
			<c:forEach var="vo" items="${list}">
			  <tbody>
			    <tr>
			      <th scope="row">${vo.name}</th>
			      <td><a href="/spaceHub/message?cmd=message&reservno=${vo.reservno}" class="link-secondary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">${vo.subject}</a></td>
			      <td style="text-align: right;">${vo.regdate}</td>
			    </tr>
			  </tbody>
			</c:forEach>
			</table>
		</form>
	</div>
</body>
</html>