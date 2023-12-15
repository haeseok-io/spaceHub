<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/common.jsp" />
	<title>spaceHub - 예약확인</title>
	
	<link rel="stylesheet" href="https://uicdn.toast.com/calendar/latest/toastui-calendar.min.css" />
	
	<script src="https://uicdn.toast.com/calendar/latest/toastui-calendar.min.js"></script>
	<script>
		const Calendar = window.tui.Calendar;
		let calendar;
	
		$(() => {
			calendar = new Calendar('#calendar', {
				defaultView: 'month',
				isReadOnly: true,
				useDetailPopup: true,
				usageStatistics: false,
				gridSelection: {
					enableDbClick: false,
					enableClick: false,
				},
				month: {
					startDayOfWeek: 0,
					dayNames: ["일", "월", "화", "수", "목", "금", "토"],
				},
				template: {
					popupDelete() {
						return '예약취소';
					},
					popupEdit(){
						return '예약수정';
					}
				},
			});
			
			// 
			calendar.on('beforeDeleteEvent', (eventObj) => {
			  	calendar.deleteEvent(eventObj.id, eventObj.calendarId);
			});
			
			
			$(document).on("click", ".detail-view-button", e => {
				let _this = $(e.currentTarget);
				let spaceno = _this.data("spaceno");
				
				window.open("/spaceHub/space?cmd=detail&spaceno="+spaceno);
			});
			
			callList();			
		});
		
		const callList = date => {
			let appendTemplate = $("#calendar-detail-template").html();
			
			
			$.ajax({
				type: "GET",
				url: "/spaceHub/mypage/guest",
				data: {cmd: "reservationData"},
				dataType: "json",
				success: result => {
					let reservationData = result.data;
					let calendarData = new Array();
					
					reservationData.forEach(obj => {
						let appendHtml = $(appendTemplate);
						
						appendHtml.find(".detail-spaceImage img").attr("src", obj.spaceImage);
						appendHtml.find(".info-addr span").text(obj.spaceAddr);
						appendHtml.find(".info-type span").text(obj.spaceType);
						appendHtml.find(".info-regdate span").text(obj.regdate);
						appendHtml.find(".info-guest span").text(obj.guest);
						appendHtml.find(".price-space").text(obj.spacePrice);
						appendHtml.find(".price-total").text(obj.price);
						appendHtml.find(".detail-button button").attr("data-spaceno", obj.spaceno);
						
						// 상태별 색상 처리
						let statusColor = "#999999";
						if( obj.status==2 ) 		statusColor = "#5A78FF";
						else if( obj.status==3 )	statusColor = "#B04F53";
						else if( obj.status==4 ) 	statusColor = "#666666";
						
						
						calendarData.push({
							id: obj.reservno,
						    calendarId: obj.spaceno,
						   	backgroundColor: statusColor,
						   	color: "#fff",
						    category: 'allday',
						    start: obj.checkin,
						    end: obj.checkout,						    
						    title: obj.spaceSubject,
						    body: appendHtml.html(),
						    attendees: "",
						    state: "",
						})
					});
					
					calendar.createEvents(calendarData);
				}
			});
			
		}
		
		// 달력 컨트롤러 제어
		const calendarController = type => {
			if( type=="prev" ) 			calendar.prev();
			else if( type=="next" )		calendar.next();
			else if( type=="today" ) 	calendar.today();
		}
		
		
	</script>
<jsp:include page="../../common/header.jsp" />

	<div class="main">
		<div class="inner">
			<div class="page-title">
				<div class="title-name">예약 내역</div>
			</div>
		
			<div class="calendar-wrap">
				<div class="calendar-control" style="display: flex; justify-content: space-between;">
					<button type="button" class="btn btn-primary" onclick="calendarController('prev');">이전달</button>
					<button type="button" class="btn btn-primary" onclick="calendarController('next');">다음달</button>
				</div>
				<div id="calendar" style="height: 600px;"></div>
			</div>
		</div>
	</div>
	
	
	
	<template id="calendar-detail-template">
		<div class="calendar-detail-wrap">
			<div class="detail-spaceImage">
				<img src="" alt="" width="100%" />
			</div>
			<div class="detail-info">
				<p class="info-addr">숙소위치: <span></span></p>
				<p class="info-type">숙소형태: <span></span></p>
				<p class="info-regdate">예약일: <span></span></p>
				<p class="info-guest">게스트: <span></span></p>
			</div>
			<div class="detail-price">
				<p>1박금액 : <span class="price-space"></span></p>
				<p>결제금액 : <span class="price-total"></span></p>
			</div>
			<div class="detail-button">
				<button type="button" class="detail-view-button" data-spaceno="" style="display: block; width: 100%;">상세보기</button>
			</div>
		</div>
	</template>
	
</body>
</html>