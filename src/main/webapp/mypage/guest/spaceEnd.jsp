<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/common.jsp" />
	<title>spaceHub - 취소예약확인</title>
	
	<link rel="stylesheet" href="https://uicdn.toast.com/calendar/latest/toastui-calendar.min.css" />
	
	<script src="https://uicdn.toast.com/calendar/latest/toastui-calendar.min.js"></script>
	<style type="text/css">
	#cd{
		float:left;
	}
	
	#re{
		float: right;
	}
	
	a{
		text-decoration-line:none;
	}
	
	#hm{
		float:left;
	}
	
	#de{
		float: right;
	}
	img{
		width:287px;
		height:202px;
	}
	.col{
		margin-right:30px;
		margin-top : 80px;
		width: 300px;
	}
	#can{
		text-align: center;
		display: block;
	}
	.container{
		bottom:100px;
	}
</style>
<jsp:include page="../../common/header.jsp" />

	<div class="main">
		<div class="inner">
			<div class="page-title">
				<div class="title-name">취소 예약 확인</div>
				<div class="btn-group" style="float: right;">
				  <a href="/spaceHub/mypage/guest?cmd=spaceList" class="btn btn-secondary" >여행 예정</a>
				  <a href="#" class="btn btn-secondary active" aria-current="page">지난 여행</a>
				  <a href="/spaceHub/mypage/guest?cmd=spaceCancel" class="btn btn-secondary">취소한 여행</a>
				</div>
				<div class="container">
					<form action="">
					<div class="row row-cols-1 row-cols-md-4 g-4" style="width:1500px;">
					<c:forEach var="vo" items="${list}">
			 		 <div class="col">
						<div class="card h-100" style="width: 18rem;">
						  <a href="/spaceHub/space?cmd=detail&spaceno=${vo.spaceno}"><img src="${vo.path}" alt="..."></a>
						  <div class="card-body">
						    <h5 class="card-title">${vo.subject}</h5>
						  </div>
						  <div class="card-body">
						    <p class="card-text" id="cd">체크인날짜</p>
						    <p class="card-text" id="re">${vo.checkin}</p>
						  </div>
						  <div class="card-body">
						    <p class="card-text" id="cd">체크아웃날짜</p>
						    <p class="card-text" id="re">${vo.checkout}</p>
						  </div>
						  <ul class="list-group list-group-flush">
						    <li class="list-group-item">게스트 인원 <p class="card-text" id="re">${vo.guest}</p></li>
						    <li class="list-group-item">예약 번호 <p class="card-text" id="sreserv" style="float: right;">${vo.reservno}</p></li>
						    <li class="list-group-item">결제 가격 <p class="card-text" id="re">₩ ${vo.price}</p></li>
						  </ul>
						  <div class="card-footer">
						    <a href="/spaceHub/message" class="link-secondary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover" id="hm">호스트에게메세지</a>
						    <a href="/spaceHub/review?cmd=review&reservno=${vo.reservno}" class="link-secondary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover" id="de">후기쓰기</a>
						  </div>
						</div>
						</div>
					</c:forEach>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>