<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>spaceHub - 진행중인 예약</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

<style type="text/css">
	p, li{
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
		margin-right:130px;
		margin-top : 150px;
	}
	#can{
		text-align: center;
		display: block;
	}
	.gospace{
		margin-left:50px;
		margin-top : 80px;
	}
</style>
<script type="text/javascript">

	function delchk(){
			return confirm("취소하시겠습니까?");
	}
</script>
</head>
<body>
	<div class="container">
		<form action="">
		<h2>예약확인</h2>
		<div class="btn-group" style="float: right;">
		  <a href="#" class="btn btn-secondary active" aria-current="page">여행 예정</a>
		  <a href="/spaceHub/mypage/guest?cmd=spaceEnd" class="btn btn-secondary">지난 여행</a>
		  <a href="/spaceHub/mypage/guest?cmd=spaceCancel" class="btn btn-secondary">취소한 여행</a>
		</div>
		<div class="gospace">
			<a class="icon-link icon-link-hover link-success link-underline-success link-underline-opacity-25" href="/spaceHub/home">
		  새로운 여행지를 계획해보세요.
		  <svg class="bi" aria-hidden="true"><use xlink:href="#arrow-right"></use></svg>
		</a>
		</div>
		<div class="row row-cols-1 row-cols-md-4 g-4">
		<c:forEach var="vo" items="${list}">
 		 <div class="col">
			<div class="card h-100" style="width: 18rem;">
			  <a href="/spaceHub/space?cmd=detail&spaceno=${vo.spaceno}"><img src="${vo.path}" alt="..."></a>
			  <div class="card-body">
			    <h5 class="card-title">${vo.subject}</h5>
			  </div>
			  <div class="card-body">
			    <p class="card-text">체크인날짜</p>
			    <p class="card-text" id="re">${vo.checkin}</p>
			  </div>
			  <div class="card-body">
			    <p class="card-text">체크아웃날짜</p>
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
			  <div class="card-footer">
			   <a href="/spaceHub/order?cmd=cancel&reservno=${vo.reservno}" id="can" onclick="return delchk();" class="link-danger link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">예약 취소</a>
			  </div>
			</div>
			</div>
		</c:forEach>
			</div>
		</form>
	</div>
</body>
</html>