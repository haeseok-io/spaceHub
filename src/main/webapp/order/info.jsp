<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<style type="text/css">
	*{
		margin:0;
		padding:0;
	}
	p{
		float:left;
	}
	
	button{
		float: right;
	}
</style>

<script type="text/javascript">
	function requestPay() {
		  IMP.init('imp06672844');
		  
		  IMP.request_pay({
		    pg: "inicis",
		    pay_method: "card",
		    merchant_uid : 'merchant_'+new Date().getTime(),
		    name : "방",
			amount : 60000,
		    buyer_email : 'iamport@siot.do',
		    buyer_name : '구매자',
		    buyer_tel : '010-1234-5678',
		    buyer_addr : '서울특별시 강남구 삼성동',
		    buyer_postcode : '123-456'
		  }, function (rsp) {
			    console.log(rsp);
			    if (rsp.success) {
			      var msg = '결제가 완료되었습니다.';
			      msg += "고유ID", rsp.imp_uid;
				  msg += "상점 거래 ID : "+rsp.merchant_uid;
				  msg += "결제 금액 : "+rsp.paid_amount;
				  msg += "카드 승인 번호 : "+rsp.apply_num;
				  $.ajax({
						url : "/spaceHub/order?cmd=orderOk",
						type : "post",
						data : {
							//고객 번호,
							orderno : rsp.merchant_uid,
							amount : rsp.paid_amount,
							cardCondirmno : rsp.apply_num
						},
						success : function(result){
							if(result == "y"){
								location.href="/ordercomplete.jsp";
							}else{
								alert("DB 입력 실패");
								return false;
							}
						}
					});
			    } else {
			      var msg = '결제에 실패하였습니다.';
			      msg += '에러내용 : ' + rsp.error_msg;
			      alert(msg);
			    }
			  });
		}
	
	function couchange(el){
		let _this = $(el);
		let dcratio = _this.val();
		$("#coupon-price").text(dcratio);
		$("#discount-price").text("0");
	}
	
	
</script>
</head>
<body>
	<div class="container">
		<h2>예약 요청</h2>
		<form action="" method="post">
			<div class="card">
			  <h5 class="card-header">예약 정보</h5>
			  <div class="card-body">
			    <h5 class="card-title">예약 공간</h5>
			    <p class="card-text" id="sname">${vo.subject}</p>
			  </div>
			  <div class="card-body">
			    <h5 class="card-title">체크인</h5>
			    <p class="card-text">2023-11-27</p>
			    <button type="button" class="btn">수정</button>
			    <input type="hidden" name="cmd" value="orderOk" />
			  </div>
			   <div class="card-body">
			    <h5 class="card-title">체크아웃</h5>
			    <p class="card-text">2023-11-30</p>
			    <button type="button" class="btn">수정</button>
			  </div>
			   <div class="card-body">
			    <h5 class="card-title">게스트</h5>
			    <p class="card-text">3명</p>
			    <button type="button" class="btn">수정</button>
			  </div>
			   <div class="card-body">
			    <h5 class="card-title">쿠폰</h5>
			    <select class="form-select" onchange="couchange(this)" aria-label="Default select example">
				  <option selected value="0">적용 가능한 쿠폰</option>
			    <c:forEach var="cvo" items="${list}">
				  <option value="${cvo.dcratio}">${cvo.name}</option>
				</c:forEach>
				</select>
			  </div>
			  <div class="card-body">
			    <h9 class="card-title">쿠폰과 호스트의 할인은 중복 적용이 불가능합니다.</h9>
			  </div>
			  <br />
			</div>
			<br>
			<hr>
			<br/>
			<div class="card">
			  <h5 class="card-header">요금 세부 정보</h5>
			  <div class="card-body">
			    <p class="card-title" >${vo.price} X 3박</p>
			    <p class="card-text" style="float: right" id="space-price">${vo.price*3}</p>
			    <p class="card-text" style="float: right">₩ </p>
			  </div>
			  <div class="card-body">
			    <p class="card-title">서비스 수수료</p>
			    <p class="card-text" style="float: right" id="service-price">${(vo.price*3)*0.03}</p>
			    <p class="card-text" style="float: right">₩ </p>
			  </div>
			  <div class="card-body">
			    <p class="card-title">쿠폰 할인</p>
			    <p class="card-text" style="float: right"> %</p>
			    <p class="card-text" style="float: right" id="coupon-price">0</p>
			  </div>
			  <div class="card-body">
			    <p class="card-title" id="dis">할인</p>
			    <p class="card-text" style="float: right"> %</p>
			    <p class="card-text" style="float: right" id="discount-price">${dc}</p>
			  </div>
			  <ul class="list-group list-group-flush">
			    <li class="list-group-item">총 합계 (KRW) <p class="card-text" style="float: right" id="price" >₩ ${(vo.price*3)+(vo.price*3)*0.03}</p></li>
			  </ul>
			  <div class="card-body">
			    <p>해외에서 결제가 처리되기 때문에 카드 발행사에서 추가 수수료를 부과할 수 있습니다.</p>
			  </div>
			</div>
			<br>
			<hr>
			<div>
				<h5>환불 정책</h5><br />
				<p>체크인 날짜 5일전에 취소하면 환불을 받으실 수 있습니다.</p>
			</div>
			<br>
			<hr>
			<div>
				<h5>기본 규칙</h5><br />
				<p>훌륭한 게스트가 되기 위한 몇 가지 간단한 규칙을 지켜주실 것을 모든 게스트에게 당부드리고 있습니다.</p>
				<ul>
					<li>숙소 이용규칙을 준수하세요.</li>
					<li>호스트의 집도 자신의 집처럼 아껴주세요.</li>				
				</ul>
			</div>
			<br>
			<div class="d-grid gap-2">
			  <input type="button" value="결제하기" class="btn btn-outline-secondary" onclick="requestPay()"/>
			</div>
			<br />
			<br />
		</form>
	</div>
</body>
</html>