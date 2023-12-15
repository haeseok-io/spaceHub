<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/common.jsp" />
	<title>상세</title>
	
	<link rel="stylesheet" href="/spaceHub/css/air-datepicker.css" />
	<style>
		.inner { width: 1120px; padding: 50px 0; }
		
		.detail-title {}
		.detail-title .title-name { font-family: 'Malgun Gothic', '맑은 고딕', sans-serif; font-weight: bold; font-size: 22px; }
		.detail-title .title-etc { font-size: 16px; color: #888; }
		.detail-wrap { margin-top: 30px; font-size: 14px; color: #666; }
		
		.detail-subject { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
		.detail-subject .subject-name { font-weight: bold; font-size: 26px; }
		.detail-subject .subject-etc { display: flex; }
		.detail-subject .subject-etc button { background: #fff; border: none; border-radius: 5px; padding: 5px 10px; font-size: 16px; color: #666; cursor: pointer; }
		.detail-subject .subject-etc button:hover { background: #e0e0e0; }
		
		.detail-images { display: flex; justify-content: space-between; }
		.detail-images .images-main { width: 55%; height: 500px; }
		.detail-images .images-main img { width: 100%; height: 100%; object-fit: cover; }
		.detail-images .images-sub { width: calc(45% - 10px); }
		.detail-images .images-sub .images-list { display: flex; flex-wrap: wrap; justify-content: space-between; width: 100%; }
		.detail-images .images-sub .images-list li { position: relative; width: calc(50% - 5px); height: 245px; margin-bottom: 10px; }
		.detail-images .images-sub .images-list li:nth-child(n+3) { margin-bottom: 0; }
		.detail-images .images-sub .images-list li img { width: 100%; height: 100%; object-fit: cover; }
		
		.detail-content { position: relative; }
		.detail-content .content-scroll { width: 65%; }
		.detail-content .content-scroll > div { padding: 30px 0 50px 0; border-top: 1px solid #e0e0e0; }
		.detail-content .content-scroll > div:first-child { border-top: 0; }
		
		.detail-content .content-scroll .detail-info {}
		.detail-content .content-scroll .detail-info .info-wrap { display: flex; margin-top: 10px; }
		.detail-content .content-scroll .detail-info .info-wrap .info-rating { display: flex; align-items: center; }
		.detail-content .content-scroll .detail-info .info-wrap .info-rating i { font-size: 16px; margin-right: 5px; }
		.detail-content .content-scroll .detail-info .info-wrap .info-rating span { font-weight: bold; font-size: 18px; }
		.detail-content .content-scroll .detail-info .info-wrap .info-review { font-weight: bold; font-size: 16px; }
		
		.detail-content .content-scroll .detail-text .detail-wrap { font-size: 16px; }
		
		.detail-content .content-scroll .detail-factory .factory-list { width: 80%; display: flex; flex-wrap: wrap; }
		.detail-content .content-scroll .detail-factory .factory-list li { width: 50%; font-size: 16px; font-weight: bold; line-height: 40px; }
		.detail-content .content-scroll .detail-factory .factory-list li i { font-size: 18px; margin-right: 5px; }
		
		.detail-content .content-scroll .detail-date {}
		.detail-content .content-scroll .detail-date .detail-wrap { display: flex; padding: 20px 0; }
		.detail-content .content-scroll .detail-date .detail-wrap .date-wrap { margin-right: 50px; }
		.detail-content .content-scroll .detail-date .detail-wrap .date-wrap .date-title { margin-bottom: 10px; font-weight: bold; font-size: 14px; text-align: center; }
		
		
		/* 우측 고정되는 영역 */
		.detail-content .content-fixed { position: absolute; top: 30px; right: 0; width: 350px; background: #fff; }
		.detail-content .content-fixed .detail-reservation { box-shadow: rgba(0, 0, 0, 0.12) 0px 6px 16px; border: 1px solid #ccc; border-radius: 10px; padding: 30px; }
		.detail-content .content-fixed .detail-reservation .reservation-field {}
		.detail-content .content-fixed .detail-reservation .reservation-field .reservation-wrap { margin-bottom: 10px; }
		.detail-content .content-fixed .detail-reservation .reservation-field .reservation-wrap .item-title { margin-bottom: 5px; font-weight: bold; font-size: 14px; }
		.detail-content .content-fixed .detail-reservation .reservation-field .reservation-wrap .item-data {}
		.detail-content .content-fixed .detail-reservation .reservation-field .reservation-wrap .item-data input[type='text'] { width: 100%; height: 40px; border: 1px solid #ccc; border-radius: 5px; padding-left: 10px; }
		.detail-content .content-fixed .detail-reservation .reservation-button { margin-top: 20px; }
		.detail-content .content-fixed .detail-reservation .reservation-button button { width: 100%; height: 50px; background: #E61B60; border: 0; border-radius: 10px; font-weight: bold; font-size: 16px; color: #fff; }
		
		.detail-content .content-fixed .detail-reservation .reservation-field .reservation-wrap.guest .item-data { display: flex; }
		.detail-content .content-fixed .detail-reservation .reservation-field .reservation-wrap.guest .item-data input[type='text'] { width: calc(100% - 80px); border-radius: 0; border-left: 0; border-right: 0; }
		.detail-content .content-fixed .detail-reservation .reservation-field .reservation-wrap.guest .item-data button { width: 40px; height: 40px; background: #e0e0e0; border: 1px solid #ccc; }
		
		/* 숙소 후기 */
		.detail-review { padding: 30px 0 50px 0; border-top: 1px solid #e0e0e0; }
		.detail-review .review-list { display: flex; justify-content: space-between; }
		.detail-review .review-list li { width: 48%; }
		.detail-review .review-list li .item-user { display: flex; }
		.detail-review .review-list li .item-user .user-img { width: 40px; height: 40px; border: 1px solid #666; border-radius: 100%; overflow: hidden; }
		.detail-review .review-list li .item-user .user-img img { width: 100%; height: 100%; object-fit: cover; }
		.detail-review .review-list li .item-user .user-info { margin-left: 15px; }
		.detail-review .review-list li .item-user .user-info .info-name { font-weight: bold; font-size: 16px; }
		.detail-review .review-list li .item-user .user-info .info-etc { font-size: 12px; }
		.detail-review .review-list li .item-user .user-info .info-etc .etc-rating { font-size: 12px; }
		.detail-review .review-list li .item-user .user-info .info-etc .etc-date {}
		.detail-review .review-list li .item-subject { margin-top: 20px; font-weight: bold; font-size: 18px; color: #333; }
		.detail-review .review-list li .item-contents { margin-top: 5px; color: #666; font-size: 16px; }
		
		/* 호스팅 지역 */
		.detail-loc { padding: 30px 0 50px 0; border-top: 1px solid #e0e0e0; }
		.detail-loc .loc-map { width: 100%; height: 500px; }
		
		/* 호스트 정보 */
		.detail-host { display: flex; align-items: flex-start; padding: 30px 0 50px 0; border-top: 1px solid #e0e0e0; }
		.detail-host .info-img { width: 130px; height: 130px; border: 2px solid #666; border-radius: 100%; margin-right: 30px; overflow: hidden; }
		.detail-host .info-img img { width: 100%; height: 100%; object-fit: cover; }
		
		.detail-host .detail-wrap { width: 500px; margin-top: 0; }
		.detail-host .detail-wrap .title-name { color: #333; }
		.detail-host .detail-wrap .host-message { margin-top: 20px; }
		.detail-host .detail-wrap .host-message textarea { width: 100%; height: 100px; border: 1px solid #ccc; border-radius: 10px; padding: 10px; resize: none; }
		.detail-host .detail-wrap .host-button { margin-top: 10px; }
		.detail-host .detail-wrap .host-button button { background: #fff; border: 1px solid #333; border-radius: 10px; padding: 10px 20px; font-weight: bold; }
		.detail-host .detail-wrap .host-button button:hover { background: #f9f9f9; }
		
	</style>
	
	<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=5e9f8f00127fcd38f89ea645b5f3327f&libraries=services"></script>
	<script src="/spaceHub/js/air-datepicker.js"></script>
	<script>
		let checkinDatepicker;
		let checkoutDatepicker;
		let reserCheckinDatepicker;
		let reserCheckoutDatepicker;
	
		$(() => {
			// 체크인 datepicker
			checkinDatepicker = new AirDatepicker(".date-selector[data-type='checkin']", {
				minDate: new Date("${detailData.inDate}"),
				maxDate: new Date("${detailData.outDate}"),
				onSelect({date}) {
					let minDate = "";
					let reserCheckinDateList = reserCheckinDatepicker.selectedDates;
					
					if( !date ){
						if( reserCheckinDateList.length>0 ) 	reserCheckinDatepicker.clear();
					} else {
						let checkinDate = new Date(date);
						let reserCheckinDate = new Date(reserCheckinDateList[0]);
						
						if( reserCheckinDateList.length<1 || checkinDate.getTime()!==reserCheckinDate.getTime() ){
							reserCheckinDatepicker.selectDate(date);
						}
					
						minDate = checkinDate.setDate(date.getDate() + 2);
					}
					
					
					checkoutDatepicker.update({minDate: minDate});					
			    }
			});
			reserCheckinDatepicker = new AirDatepicker("input[name='checkin']", {
				autoClose: true,
				minDate: new Date("${detailData.inDate}"),
				maxDate: new Date("${detailData.outDate}"),
				onSelect({date}) {
					let minDate = "";
					let reserCheckinDate = new Date(date);
					let checkinDateList = checkinDatepicker.selectedDates;
					
					if( !date ){
						if( checkinDateList.length>0 ) 		checkinDatepicker.clear();
					} else {
						let checkinDate = new Date(checkinDateList[0]);
						
						if( checkinDateList.length<1 || reserCheckinDate.getTime()!==checkinDate.getTime() ){
							checkinDatepicker.selectDate(date);
						}
						
						minDate = reserCheckinDate.setDate(date.getDate() + 2);
					}
					
					reserCheckoutDatepicker.update({minDate: minDate});
					checkDateMsg();
			    }
			});
			
			// 체크아웃 datepicker
			checkoutDatepicker = new AirDatepicker(".date-selector[data-type='checkout']", {
				minDate: new Date("${detailData.inDate}"),
				maxDate: new Date("${detailData.outDate}"),
				onSelect({date}) {
					let maxDate = "";
					let checkoutDate = new Date(date);
					let reserCheckoutDateList = reserCheckoutDatepicker.selectedDates;
					
					if( !date ){
						if( reserCheckoutDateList.length>0 ) 	reserCheckoutDatepicker.clear();
					} else {
						let reserCheckoutDate = new Date(reserCheckoutDateList[0]);
						
						if( reserCheckoutDateList.length<1 || checkoutDate.getTime()!==reserCheckoutDate.getTime() ){
							reserCheckoutDatepicker.selectDate(date);
						}
						
						maxDate = checkoutDate.setDate(date.getDate() - 2);
					}
					
					checkinDatepicker.update({maxDate: maxDate});
			    }
			});
			reserCheckoutDatepicker = new AirDatepicker("input[name='checkout']", {
				autoClose: true,
				minDate: new Date("${detailData.inDate}"),
				maxDate: new Date("${detailData.outDate}"),
				onSelect: ({date}) => {
					let maxDate = "";
					let reserCheckoutDate = new Date(date);
					let checkoutDateList = checkoutDatepicker.selectedDates;
					
					if( !date ){
						if( checkoutDateList.length>0 ){
							checkoutDatepicker.clear();
						}
					} else {
						let checkoutDate = new Date(checkoutDateList[0]);
						
						if( checkoutDateList.length<1 || reserCheckoutDate.getTime()!==checkoutDate.getTime() ){
							checkoutDatepicker.selectDate(date);
						}
						
						maxDate = reserCheckoutDate.setDate(date.getDate() - 2);
					}
					
					reserCheckinDatepicker.update({maxDate: maxDate});
					checkDateMsg();
				}
			});
			
			
			// 게스트 증감
			$(".guest-control-button").on("click", e => {
				let _this = $(e.currentTarget);
				let type = _this.data("type");
				
				let buttonEl = $("input[name='guest']");
				let buttonVal = buttonEl.val();
				
				if( type=='minus' ) 	buttonVal = parseInt(buttonVal)-1;
				else 					buttonVal = parseInt(buttonVal)+1;
				
				if( buttonVal<0 ) 		buttonVal = 0;
				
				buttonEl.val(buttonVal)
			});
			
			
			// 우측 예약하기 영역 고정
			$(window).on("scroll resize", e => {
				let _this = $(e.currentTarget);
				let contentEl = $(".detail-content");
				let fixedEl = $(".content-fixed");
				
				// Data
				let scrollTop = _this.scrollTop();				
				let contentPos = contentEl.position();
				let contentWidth = contentEl.outerWidth()/2
			
				let fixedStartTopPos = contentPos.top;
				let fixedEndTopPos = (fixedStartTopPos+contentEl.outerHeight())-(fixedEl.outerHeight()+80);
				let leftPos = contentPos.left+contentWidth+(contentWidth-fixedEl.outerWidth());
				let lastTopPos = contentEl.outerHeight()-(fixedEl.outerHeight()+50);
				
				// Process
				if( scrollTop<fixedStartTopPos ){
					fixedEl.css({position: "absolute", left: "inherit", right: 0});
				} else if( scrollTop>=fixedStartTopPos && scrollTop<=fixedEndTopPos ){
					fixedEl.css({position: "fixed", top: "30px", left: leftPos+"px"});
				} else if( scrollTop>fixedEndTopPos ) {
					fixedEl.css({position: "absolute", top: lastTopPos+"px", left: "inherit", right: 0});
				}
			});
		});
		
		// 체크인/아웃 날짜 선택시 변경되는 문구 제어
		const checkDateMsg = () => {
			let dateMsgEl = $(".detail-date .title-name");
			let dateTermEl = $(".detail-date .title-etc");
			let checkinDate = checkinDatepicker.selectedDates;
			let checkoutDate = checkoutDatepicker.selectedDates;
			
			let dateMsg = "";
			let dateTerm = "";
			
			
			// 체크인 날짜만 있을 경우
			if( checkinDate.length>0 && checkoutDate.length<1 ){
				dateMsg = "체크아웃 날짜를 선택해주세요.";
				dateTerm = "최소 숙박일수 : 2일";
			}
			// 체크아웃만 있을 경우
			else if( checkinDate.length<1 && checkoutDate.length>0 ){
				dateMsg = "체크인 날짜를 선택해주세요.";
				dateTerm = "최소 숙박일수 : 2일";
			}
			// 둘다 있을 경우
			else if( checkinDate.length>0 && checkoutDate.length>0 ){
				checkinDate = new Date(checkinDate[0]);
				checkoutDate = new Date(checkoutDate[0]);

				let dayDiff = (checkoutDate.getTime() - checkinDate.getTime()) / (1000*3600*24);
				checkinDate = formatDate(checkinDate);
				checkoutDate = formatDate(checkoutDate);
				
				
				dateMsg = "${spaceData.subject} 에서 "+dayDiff+"박";
				dateTerm = checkinDate+" ~ "+checkoutDate;
			}
			// 둘다 없을 경우
			else if( checkinDate.length<1 && checkoutDate.length<1 ){
				dateMsg = "체크인 날짜를 선택해주세요.";
				dateTerm = "여행 날짜를 입력하여 정확한 요금을 확인하세요.";
			}
			
			
			dateMsgEl.text(dateMsg);
			dateTermEl.text(dateTerm);
		}
		
		// 날짜 포맷 변경
		const formatDate = inputDate => {
			let date = new Date(inputDate);
			let year = date.getFullYear();
			let month = String(date.getMonth() + 1).padStart(2, '0');
			let day = String(date.getDate()).padStart(2, '0');
			  
			return year+"-"+month+"-"+day;
		}
		
		// 호스트 문의하기
		const hostMessageWrite = () => {
			let form = $("form[name='messageForm']");
			
			if( !$("textarea[name='contents']", form).val() ){
				alert("문의할 메시지를 입력해주세요.");
				$("textarea[name='contents']", form).focus();
			}
			
			$.ajax({
				type: "POST",
				url: "/spaceHub/message",
				data: form.serialize(),
				dataType: "json",
				success: result => {
					if( result.errorCode ){
						alert(result.errorMsg);
						return false;
					}
					
					document.location.href = "/spaceHub/message";
				}
			});
		}
	</script>
	<script>
		// 카카오 맵 스크립트
		$(() => {
			var mapContainer = document.querySelector(".loc-map");
			var map = new kakao.maps.Map(mapContainer, {
				level: 3,
				scrollwheel: false,
				center: new kakao.maps.LatLng(${detailData.x}, ${detailData.y}),
			});
			
			var geocoder = new kakao.maps.services.Geocoder();
			geocoder.addressSearch('${spaceData.addr}', function(result, status) {
				if (status === kakao.maps.services.Status.OK) {					
					var marker = new kakao.maps.Marker({
			            map: map,
			            position: coords
			        });
					
					var infowindow = new kakao.maps.InfoWindow({
			            content: '<div style="width:150px;text-align:center;padding:6px 0;">${spaceData.subject}</div>'
			        });
			        infowindow.open(map, marker);
			        
					var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
			        map.setCenter(coords);
			        
			        var circle = new kakao.maps.Circle({
					    center : new kakao.maps.LatLng(result[0].y, result[0].x),  // 원의 중심좌표 입니다 
					    radius: 50, // 미터 단위의 원의 반지름입니다 
					    strokeWeight: 5, // 선의 두께입니다 
					    strokeColor: '#75B8FA', // 선의 색깔입니다
					    strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
					    strokeStyle: 'dashed', // 선의 스타일 입니다
					    fillColor: '#CFE7FF', // 채우기 색깔입니다
					    fillOpacity: 0.7  // 채우기 불투명도 입니다   
					}); 

					// 지도에 원을 표시합니다 
					circle.setMap(map);
				}
			});
		});
	</script>
	
	
<jsp:include page="../common/header.jsp" />

	<div class="main">
		<div class="inner">
			<div class="detail-subject">
				<p class="subject-name">${spaceData.subject}</p>
				<div class="subject-etc">
					<button class="share-button">
						<i class="bi bi-share"></i>
						공유하기
					</button>
					<button class="jjim-button">
						<i class="bi bi-heart"></i>
						찜 하기
					</button>
				</div>
			</div>
			<div class="detail-images">
				<div class="images-main">
					<img src="${imageList[0].path}" alt="" />
				</div>
				<div class="images-sub">
					<ul class="images-list">
						<c:forEach begin="1" end="4" var="i" step="1">
						<li>
							<img src="${imageList[i].path}" alt="" />
						</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="detail-content">
				<div class="content-scroll">
					<div class="detail-info">
						<div class="detail-title">
							<p class="title-name">${spaceData.addr}</p>
							<p class="title-etc">최대인원 ${detailData.maxGuest} · 침실 ${detailData.bed} · 욕실 ${detailData.bathroom}</p>
						</div>
						<div class="info-wrap">
							<div class="info-review">
								후기 ${reviewCount}개
							</div>
							&nbsp;·&nbsp;
							<div class="info-rating">
								<i class="bi bi-star-fill"></i>
								<span>${avgRating}</span>
							</div>
						</div>
					</div>
					<div class="detail-text">
						<div class="detail-title">
							<p class="title-name">숙소 설명</p>
						</div>
						<div class="detail-wrap">
							${detailData.detail}
						</div>
					</div>
					<div class="detail-factory">
						<div class="detail-title">
							<p class="title-name">숙소 편의시설</p>
						</div>
						<div class="detail-wrap">
							<ul class="factory-list">
								<c:if test="${factoryData.wifi>0}">
								<li><i class="bi bi-wifi"></i> 와이파이</li>
								</c:if>
								<c:if test="${factoryData.tv>0}">
								<li><i class="bi bi-tv"></i> TV</li>
								</c:if>
								<c:if test="${factoryData.kitchen>0}">
								<li><i class="bi bi-tv"></i> 주방</li>
								</c:if>
								<c:if test="${factoryData.wm>0}">
								<li><i class="bi bi-water"></i> 세탁기</li>
								</c:if>
								<c:if test="${factoryData.aircon>0}">
								<li><i class="bi bi-snow2"></i> 에어컨</li>
								</c:if>
								<c:if test="${factoryData.canpark>0}">
								<li><i class="bi bi-car-front"></i> 유료 주차 가능</li>
								</c:if>
								<c:if test="${factoryData.canfreepark>0}">
								<li><i class="bi bi-car-front"></i> 무료 주차 가능</li>
								</c:if>
								<c:if test="${factoryData.swim>0}">
								<li><i class="bi bi-tsunami"></i> 수영장</li>
								</c:if>
								<c:if test="${factoryData.bbq>0}">
								<li><i class="bi bi-fire"></i> 바베큐</li>
								</c:if>
								<c:if test="${factoryData.bbq>0}">
								<li><i class="bi bi-tablet-landscape"></i> 당구대</li>
								</c:if>
								<c:if test="${factoryData.bbq>0}">
								<li><i class="bi bi-fire"></i> 벽난로</li>
								</c:if>
								<c:if test="${factoryData.bbq>0}">
								<li><i class="bi bi-megaphone"></i> 화재경보기</li>
								</c:if>
								<c:if test="${factoryData.bbq>0}">
								<li><i class="bi bi-bandaid"></i> 구급상자</li>
								</c:if>
								<c:if test="${factoryData.bbq>0}">
								<li><i class="bi bi-fire"></i> 소화기</li>
								</c:if>
							</ul>
						</div>
					</div>
					<div class="detail-date">
						<div class="detail-title">
							<p class="title-name">체크인 날짜를 선택해주세요.</p>
							<p class="title-etc">여행 날짜를 입력하여 정확한 요금을 확인하세요.</p>
						</div>
						<div class="detail-wrap">
							<div class="date-wrap">
								<p class="date-title">체크인</p>
								<div class="date-selector" data-type="checkin"></div>
							</div>
							<div class="date-wrap">
								<p class="date-title">체크아웃</p>
								<div class="date-selector" data-type="checkout"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="content-fixed">
					<div class="detail-reservation">
						<form name="reservationForm" action="/spaceHub/order" method="post">
							<input type="hidden" name="cmd" value="info">
							<input type="hidden" name="spaceno" value="${detailData.spaceno}">
							
							<div class="reservation-field">
								<div class="reservation-wrap">
									<p class="item-title">체크인</p>
									<div class="item-data">
										<input type="text" name="checkin" placeholder="날짜 선택">
									</div>
								</div>
								<div class="reservation-wrap">
									<p class="item-title">체크아웃</p>
									<div class="item-data">
										<input type="text" name="checkout" placeholder="날짜 선택">
									</div>
								</div>
								<div class="reservation-wrap guest">
									<p class="item-title">게스트</p>
									<div class="item-data">
										<button type="button" class="guest-control-button" data-type="minus">
											<i class="bi bi-dash-lg"></i>
										</button>
										<input type="text" name="guest" value="0" readonly>
										<button type="button" class="guest-control-button" data-type="plus">
											<i class="bi bi-plus-lg"></i>
										</button>
									</div>
								</div>
							</div>
							<div class="reservation-button">
								<button class="submit">예약하기</button>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="detail-review">
				<div class="detail-title">
					<p class="title-name">숙소 후기</p>
				</div>
				<div class="detail-wrap">
					<ul class="review-list">
						<c:forEach var="vo" items="${reviewList}">
						<li>
							<div class="item-user">
								<div class="user-img">
									<img src="${vo.profileImg}" alt="${vo.nickname}" >
								</div>
								<div class="user-info">
									<p class="info-name">${vo.nickname}</p>
									<p class="info-etc">
										<span class="etc-rating">
											<c:forEach begin="1" end="${vo.rating}" step="1">
											<i class="bi bi-star-fill"></i>
											</c:forEach>
										</span>
										&nbsp;·&nbsp;
										<span class="etc-date">${vo.regdate}</span>
									</p>
								</div>
							</div>
							<div class="item-subject">${vo.subject}</div>
							<div class="item-contents">${vo.contents}</div>
						</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="detail-loc">
				<div class="detail-title">
					<p class="title-name">호스팅 지역</p>
				</div>
				<div class="detail-wrap">
					<div class="loc-map"></div>
				</div>
			</div>
			
			<div class="detail-host">
				<div class="info-img">
					<img src="${hostData.profileImg}" alt="${hostData.nickname}">
				</div>
				<div class="detail-wrap">
					<div class="detail-title">
						<p class="title-name">호스트 : ${hostData.nickname} 님</p>
						<div class="title-etc">회원가입일 : ${hostData.regdate}</div>
					</div>
					<div class="host-message">
						<form name="messageForm">
							<input type="hidden" name="cmd" value="writeOk" />
							<input type="hidden" name="spaceno" value="${spaceData.spaceno}" />
							
							<textarea name="contents" placeholder="호스트에게 궁금한점을 물어보세요!"></textarea>
						</form>
					</div>
					<div class="host-button">
						<button type="button" onclick="hostMessageWrite();">호스트에게 문의하기</button>
					</div>
				</div>
			</div>
			
			
		</div>
	</div>
	

</body>
</html>