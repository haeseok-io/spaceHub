<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/common.jsp" />
	<title>spaceHub - 달력</title>
	
	<link rel="stylesheet" href="https://uicdn.toast.com/calendar/latest/toastui-calendar.min.css" />
	
	<script src="https://uicdn.toast.com/calendar/latest/toastui-calendar.min.js"></script>
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
<jsp:include page="../../common/header.jsp" />
	<div class="main">
		<div class="inner">
			<div class="page-title">
				<div class="title-name">달력</div>
				<div class="btn-group">
				  <a href="#" class="btn btn-outline-secondary active" aria-current="page">달력</a>
				  <a href="/spaceHub/mypage/host?cmd=reservList&spaceno=${spaceno}" class="btn btn-outline-secondary">리스트</a>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
		<form action="">
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