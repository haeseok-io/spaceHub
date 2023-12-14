<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../common/common.jsp" />
	<title>spaceHub - 예약하기</title>
	
	<style type="text/css">
		button, .btn-group{
			float: right;
		}
	</style>

	<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
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
			let phone = $("#phone").val();
			
		  IMP.request_pay({
		    pg: "inicis",
		    pay_method: "card",
		    merchant_uid : 'm_'+new Date().getTime(),
		    name : space,
			amount : 1,
		    buyer_email : email,
		    buyer_name : memname,
		    buyer_tel : ' ',
		    buyer_addr : addr,
		    buyer_postcode : post
		  }, function (rsp) {
			    /* console.log(rsp); */
			    if (rsp.success) {
			      var msg = '결제가 완료되었습니다.';
			      msg += "고유ID", rsp.imp_uid;
				  msg += "상점 거래 ID : "+rsp.merchant_uid;
				  msg += "결제 금액 : "+rsp.paid_amount;
				  msg += "카드 승인 번호 : "+rsp.apply_num;
				  
				  document.orderform.orderno.value = rsp.merchant_uid;
				  document.orderform.amount.value = rsp.paid_amount;
				  document.orderform.cardCondirmno.value = rsp.apply_num;
				  document.orderform.cardnum.value = rsp.card_number;
				  
				  alert("예약이 완료되었습니다.");
				  
				  document.orderform.submit();
				  
			    } else {
			      var msg = '결제에 실패하였습니다.';
			      msg += '에러내용 : ' + rsp.error_msg;
			      alert(msg);
			    }
			  });
		}
	
	function requestPay2() {

			let price = $("#price").text();
			let space = $("#sname").text();
			const pricenum = Number(price);
			let memname = $("#memname").val();
			let email = $("#email").val();
			let post = $("#post").val();
			let addr = $("#addr").val();
			let phone = $("#phone").val();
				  
				  document.orderform.orderno.value = '45788898';
				  document.orderform.amount.value = pricenum;
				  document.orderform.cardCondirmno.value = '787878985';
				  document.orderform.cardnum.value = '4984-4988-6154-4498';
				  
				  alert("예약이 완료되었습니다.");
				  
				  document.orderform.submit();
				  
			    }
	
	function couchange(el){
		let _this = $(el);
		let dcratio = _this.val();
		let couponname = $("#couponselect option:selected").text();
		let a = $("#dcp").val();
		let cprice = ($("#cprice").val())*((100-dcratio)*0.01);
		let dprice = ($("#cprice").val())*((100-a)*0.01);
		let usecredits = Number($("#usecredits").val());
		
		if(dcratio != 0){
			$("#coupon-price").text(dcratio);
			$("#discount-price").text("0");
			$("#couponprice").val(dcratio);
			$("#discountprice").val(0);
			$("#price").text(cprice-usecredits);
		}else{
			$("#coupon-price").text(0);
			$("#discount-price").text(a);
			$("#couponprice").val(0);
			$("#discountprice").val(a);
			$("#price").text(dprice-usecredits);
		}
		$("#couponname").val(couponname);
	}
	
	$(()=>{
		$("#minus").on("click",()=>{
			let guest = Number($("#guest").val());
			let m = guest-1;
			if(guest > 1){
				$("#guest").val(m);
			}
		});
		
		$("#plus").on("click",()=>{
			let guest = $("#guest").val();
			let p = Number(guest)+1;
			let guestmax = $("#guestmax").val();
			if(guest < guestmax){
				$("#guest").val(p);
			}
		});
		
		$("#usecreditsbtn").on("click",()=>{
			let creditsyet = Number($("#creditsyet").val());
			let usecredits = Number($("#usecredits").val());
			let crespan = Number($("#crespan").text());
			let price = Number($("#price").text());
			
			let dcratio = Number($("#coupon-price").text());
			let a = Number($("#discount-price").text());
			let cprice = ($("#cprice").val())*((100-dcratio)*0.01);
			let dprice = ($("#cprice").val())*((100-a)*0.01);
			let allprice = cprice+dprice-$("#cprice").val();
			
			
			if(usecredits <= creditsyet){
				let cre = creditsyet-usecredits;
				$("#crespan").text(cre);
				$("#credits-price").text(usecredits);
				$("#price").text(allprice-usecredits);
				$("#creditsprice").val(usecredits);
			}else if(usecredits > creditsyet){
				alert("크래딧이 부족합니다.");
			}else if(usecredits > price){
				let num = usecredits-price;
				$("#crespan").text(num);
				$("#price").text(0);
				$("#credits-price").text(price);
				$("#creditsprice").val(price);
			}else if(usecredits == 0){
				$("#price").text(allprice);
			}
		});
	});
	
</script>
<jsp:include page="../common/header.jsp" />

	<div class="main">
		<div class="inner">
			<h2>예약 요청</h2>
			<form action="/spaceHub/order" method="post" name="orderform">
			<div class="card">
			  <h5 class="card-header">예약 정보</h5>
			  <div class="card-body">
			    <h5 class="card-title">예약 공간</h5>
			    <p class="card-text" id="sname">${vo.subject}</p>
			    <input type="hidden" name="cmd" value="orderOk" />
			    <input type="hidden" name="name" id="memname" value="${smvo.name}" />
			    <input type="hidden" name="email" id="email" value="${smvo.email}" />
			    <input type="hidden" name="postcode" id="post" value="${smvo.post}" />
			    <input type="hidden" name="addr" id="addr" value="${smvo.addr}" />
			    <input type="hidden" name="spaceno" id="spaceno" value="${vo.spaceno}" />
			    <input type="hidden" name="guestmax" id="guestmax" value="${sddvo.maxGuest}" />
			    <input type="hidden" name="orderno" value="" />
			    <input type="hidden" name="amount" value="" />
			    <input type="hidden" name="cardCondirmno" value="" />
			    <input type="hidden" name="cardnum" value="" />
			  </div>
			  <div class="card-body">
			    <h5 class="card-title">체크인</h5>
			      <input type="text" readonly class="form-control-plaintext" name="checkin" id="checkin" value="${checkin}">
			      <button type="button" class="btn">수정</button>
			  </div>
			  <div class="card-body">
			    <h5 class="card-title">체크아웃</h5>
			      <input type="text" readonly class="form-control-plaintext" name="checkout" id="checkout" value="${checkout}">
			      <button type="button" class="btn">수정</button>
			  </div>
			  <div class="card-body">
			    <h5 class="card-title">게스트</h5>
			      <input type="text" readonly class="form-control-plaintext" name="guest" id="guest" value="${guest}">
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
			    <input type="hidden" name="couponname" id="couponname" value="" />
			  </div>
			  <div class="card-body">
			  	<div class="mb-3">
				   <h5 class="card-title">보유 크래딧</h5>
				  <div class="input-group">
				  	  <span class="input-group-text">₩</span>
					  <span class="input-group-text" id="crespan">${crvo.price}</span>
					  <input type="text" style="text-align:right;" aria-label="First name" id="usecredits" class="form-control">
					  <input type="hidden" name="creditsyet" id="creditsyet" value="${crvo.price}" />
					  <button class="btn btn-outline-secondary" id="usecreditsbtn" type="button">사용</button>
					</div>
				</div>
			  </div>
			   <div class="card-body">
			  	<div class="mb-3">
				   <h5 class="card-title">전화번호 입력</h5>
				  <input type="text" class="form-control" name="phone" id="phone" placeholder="phone-number">
				</div>
			  </div>
			</div>
			<br>
			<hr>
			<br/>
			<div class="card">
			  <h5 class="card-header">요금 세부 정보</h5>
			  <div class="card-body">
			    <p class="card-title" style="float: left">${vo.price}</p>
			    <p class="card-title" style="float: left;">X</p>
			    <p class="card-title" style="float: left;">${bark}</p>
			    <p class="card-title" style="float: left;">박</p>
			    <p class="card-text" style="float: right" id="space-price">${vo.price*bark}</p>
			    <p class="card-text" style="float: right">₩ </p>
			  </div>
			  <div class="card-body">
			    <p class="card-title">서비스 수수료</p>
			    <p class="card-text" style="float: right" id="service-price">${tax}</p>
			    <p class="card-text" style="float: right">₩ </p>
			  </div>
			  <div class="card-body">
			    <p class="card-title">쿠폰 할인</p>
			    <p class="card-text" style="float: right"> %</p>
			    <p class="card-text" style="float: right" id="coupon-price">0</p>

			    <input type="hidden" name="cprice" id="couponprice" value="0"/>
			    <input type="hidden" name="dprice" id="discountprice" value="0"/>
			  </div>
			  <div class="card-body">
			    <p class="card-title" id="dis">${sna}</p>
			    <p class="card-text" style="float: right"> %</p>
			    <p class="card-text" style="float: right" id="discount-price">${dc}</p>
			    <input type="hidden" name="dcp" id="dcp" value="${dc}"/>
			    <input type="hidden" name="cp" id="cprice" value="${((vo.price*bark)+(vo.price*bark)*0.01)}"/>
			  </div>
			   <div class="card-body">
			    <p class="card-title">사용한 크래딧</p>
			    <p class="card-text" style="float: right" id="credits-price">0</p>
			    <p class="card-text" style="float: right">₩ </p>
			    <input type="hidden" name="creditsPrice" id="creditsprice" value="0" />
			  </div>
			  <ul class="list-group list-group-flush">
			    <li class="list-group-item">총 합계 (KRW) <p class="card-text" style="float: right" id="price" >${endprice}</p><p class="card-text" style="float: right"> ₩</p></li>
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
			  <input type="button" value="결제하기(완료)" class="btn btn-outline-secondary" onclick="requestPay2()"/>
			</div>
			<br />
			<br />
		</form>
	</div>
	</div>
