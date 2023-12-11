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
	  td {
            width: 50px;
            height: 50px;
        }

        .Calendar { 
            text-align: center;
            margin: 0 auto; 
        }
</style>
</head>
<body>
	<div class="container">
		<form action="">
			<h2>달력</h2>
			<div class="btn-group">
			  <a href="#" class="btn btn-outline-secondary active" aria-current="page">달력</a>
			  <a href="/spaceHub/mypage/host?cmd=reservList&spaceno=${spaceno}" class="btn btn-outline-secondary">리스트</a>
			</div>
			<table class="Calendar">
				<thead>
					<tr>
						<td></td>
						<td colspan="5">
							<span></span>년
							<span></span>월
						</td>
						<td></td>
					</tr>
					<tr>
						<td>일</td>
						<td>월</td>
						<td>화</td>
						<td>수</td>
						<td>목</td>
						<td>금</td>
						<td>토</td>
					</tr>
				</thead>
				<tbody>
					
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>