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
						return '취소';
					},
					popupSave() {
						return '저장';
					},
					popupUpdate() {
						return '수정';
					},
					popupEdit(){
						return '편집';
					}
				},
			});
			
			calendar.createEvents([
				  {
				    id: '1',
				    calendarId: '1',
				    title: '가나다라마',
				    category: 'allday',
				    dueDateClass: '',
				    start: '2023-12-18',
				    end: '2023-12-20',
				  },
				]);
		});
		
		const callList = date => {
			
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
			<div class="calendar-wrap">
				<div class="calendar-control">
					<button type="button" onclick="calendarController('prev');">이전달</button>
					<span class="calendar-today">
						
					</span>
					<button type="button" onclick="calendarController('next');">다음달</button>
				</div>
				<div id="calendar" style="height: 600px;"></div>
			</div>
		</div>
	</div>
</body>
</html>