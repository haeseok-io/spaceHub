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
	
	button, .btn-group{
		float: right;
	}
</style>

<script type="text/javascript">
	function requestPay() {
		  IMP.init('imp06672844');

			let price = $("#price").text();
			let space = $("#sname").text();
			const pricenum = Number(price);
			let memname = $("#memname").val();
			let email = $("#email").val();
			let post = $("#post").val();
			let addr = $("#addr").val();
			let spaceno = $("#spaceno").val();
			let checkin = $("#checkin").text();
			let checkout = $("#checkout").text();
			let guest = $("#guest").text();
			let phone = $("#phone").val();
			let cprice = $("#coupon-price").text();
			let dprice = $("#discount-price").text();
			let couponname = $("#couponselect option:selected").text();
			
		  
		  IMP.request_pay({
		    pg: "inicis",
		    pay_method: "card",
		    merchant_uid : 'merchant_'+new Date().getTime(),
		    name : space,
			amount : pricenum,
		    buyer_email : email,
		    buyer_name : memname,
		    buyer_tel : ' ',
		    buyer_addr : addr,
		    buyer_postcode : post
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
							cardCondirmno : rsp.apply_num,
							name : rsp.buyer_name,
							email : rsp.buyer_email,
							addr : rsp.buyer_addr,
							postcode : rsp.buyer_postcode,
							cardnum : rsp.card_number,
							spaceno : spaceno,
							checkin : checkin,
							checkout : checkout,
							guest : guest,
							phone : phone,
							cprice : cprice,
							dprice : dprice,
							couponname : couponname
							
						},
						success : function(result){
							if(result == "y"){
								alert("예약이 완료되었습니다.");
								window.location.href="/spaceHub/home";
							}else{
								alert("결제 실패");
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
		let a = $("#dcp").val();
		let cprice = ($("#cprice").val())*((100-dcratio)*0.01);
		let dprice = ($("#cprice").val())*((100-a)*0.01);
		if(dcratio != 0){
			$("#coupon-price").text(dcratio);
			$("#discount-price").text("0");
			$("#price").text(cprice);
		}else{
			$("#coupon-price").text(0);
			$("#discount-price").text(a);
			$("#price").text(dprice);
		}
	}
	
	$(()=>{
		$("#minus").on("click",()=>{
			let guest = $("#guest").text();
			let m = guest-1;
			if(guest > 1){
				$("#guest").text(m);
			}
		});
		
		$("#plus").on("click",()=>{
			let guest = $("#guest").text();
			let p = Number(guest)+1;
			let guestmax = $("#guestmax").val();
			if(guest < guestmax){
				$("#guest").text(p);
			}
		});
	});
	
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
			    <input type="hidden" name="memname" id="memname" value="${smvo.name}" />
			    <input type="hidden" name="email" id="email" value="${smvo.email}" />
			    <input type="hidden" name="post" id="post" value="${smvo.post}" />
			    <input type="hidden" name="addr" id="addr" value="${smvo.addr}" />
			    <input type="hidden" name="spaceno" id="spaceno" value="${vo.spaceno}" />
			    <input type="hidden" name="guestmax" id="guestmax" value="${sddvo.maxGuest}" />
			  </div>
			  <div class="card-body">
			    <h5 class="card-title">체크인</h5>
			    <p class="card-text" id="checkin">2023-11-27</p>
			    <button type="button" class="btn">수정</button>
			    <input type="hidden" name="cmd" value="orderOk" />
			  </div>
			   <div class="card-body">
			    <h5 class="card-title">체크아웃</h5>
			    <p class="card-text" id="checkout">2023-11-30</p>
			    <button type="button" class="btn">수정</button>
			  </div>
			   <div class="card-body">
			    <h5 class="card-title">게스트</h5>
			    <p class="card-text" id="guest">1</p>
			    <p class="card-text">명</p>
			    <div class="btn-group" role="group" aria-label="Basic example">
				  <button type="button" class="btn btn-outline-secondary" id="minus">-</button>
				  <button type="button" class="btn btn-outline-secondary" id="plus">+</button>
				</div>
			  </div>
			   <div class="card-body">
			    <h5 class="card-title">쿠폰</h5>
			    <select class="form-select" onchange="couchange(this)" id="couponselect" aria-label="Default select example">
				  <option selected value="0">적용 가능한 쿠폰</option>
			    <c:forEach var="cvo" items="${list}">
				  <option value="${cvo.dcratio}">${cvo.name}</option>
				</c:forEach>
				</select>
			  </div>
			  <div class="card-body">
			    <h9 class="card-title">쿠폰과 호스트의 할인은 중복 적용이 불가능합니다.</h9>
			  </div>
			   <div class="card-body">
			  <div class="mb-3">
				   <h5 class="card-title">전화번호 입력</h5>
				  <input type="text" class="form-control" id="phone" placeholder="phone">
				</div>
			  </div>
			</div>
			<br>
			<hr>
			<br/>
			<div class="card">
			  <h5 class="card-header">요금 세부 정보</h5>
			  <div class="card-body">
			    <p class="card-title" >${vo.price}</p>
			    <p class="card-title" >X</p>
			    <p class="card-title" >3</p>
			    <p class="card-title" >박</p>
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
			    <p class="card-title" id="dis">호스트의 할인</p>
			    <p class="card-text" style="float: right"> %</p>
			    <p class="card-text" style="float: right" id="discount-price">${dc}</p>
			    <input type="hidden" name="dcp" id="dcp" value="${dc}"/>
			    <input type="hidden" name="cprice" id="cprice" value="${((vo.price*3)+(vo.price*3)*0.03)}"/>
			  </div>
			  <ul class="list-group list-group-flush">
			    <li class="list-group-item">총 합계 (KRW) <p class="card-text" style="float: right" id="price" >${((vo.price*3)+(vo.price*3)*0.03)*((100-dc)*0.01)}</p><p class="card-text" style="float: right"> ₩</p></li>
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
				<span>훌륭한 게스트가 되기 위한 몇 가지 간단한 규칙을 지켜주실 것을 모든 게스트에게 당부드리고 있습니다.</span>
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
