<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="./common/common.jsp" />
	
	<title>spaceHub</title>
	<link rel="stylesheet" href="/spaceHub/css/air-datepicker.css" />
	<style>
		.space { padding: 30px 0; }
		
		.space-list { display: flex; justify-content: flex-start; flex-wrap: wrap; margin: 0 -1%; }
		.space-list .list-item { width: 23%; margin: 2% 1%; overflow: hidden; cursor: pointer; }
		.space-list .list-item .item-thumbnail { position: relative; overflow: hidden; border-radius: 15px; }
		.space-list .list-item .item-thumbnail img { width: 100%; height: 300px; vertical-align: top; }
		.space-list .list-item .item-thumbnail .space-jjim { position: absolute; top: 10px; right: 10px; }
		.space-list .list-item .item-thumbnail .space-jjim i { font-size: 18px; color: #fff; }
		.space-list .list-item .item-thumbnail .space-jjim .bi-heart-fill { color: red; }
		
		.space-list .list-item .item-subject { display: flex; justify-content: space-between; margin-top: 20px; }
		.space-list .list-item .item-subject .subject-name { font-weight: bold; font-size: 16px; }
		.space-list .list-item .item-info { margin-top: 10px; }
		.space-list .list-item .item-info .info-addr { margin-top: 10px; font-size: 14px; color: #aaa; }
		.space-list .list-item .item-info .info-date { margin-top: 5px; font-size: 14px; color: #aaa; }
		.space-list .list-item .item-price { margin-top: 20px; }
		.space-list .list-item .item-price span { font-weight: bold; font-size: 18px; }
		
		.space-empty { display: none; padding: 30px 0; text-align: center; }
		.space-empty i { font-size: 30px; color: #666; }
		.space-empty p { margin-top: 20px; font-weight: bold; font-size: 18px; line-height: 30px; color: #333; }
	
		.space-more { display: none; position: fixed; bottom: 30px; left: 50%; transform: translateX(-50%); }
		.space-more button { background: #333; border: none; border-radius: 10px; padding: 10px 20px; font-size: 16px; color: #fff; cursor: pointer; }
	</style>
	
	<script src="/spaceHub/js/air-datepicker.js"></script>
	<script>
		let moreViewStatus = false;
	
		$(() => {
			// 초기 리스트 호출
			callList();
			
			// datepicker
			const datepickerOption = {autoClose: true};
			const inDatepicker = new AirDatepicker("input[name='in_date']", datepickerOption);
			const outDatepicker = new AirDatepicker("input[name='out_date']", datepickerOption);
			
			// 상세검색
			$(".search-toggle .toggle-item").click(e => {
				let _this = $(e.currentTarget);
				let status = _this.data('status');
				
				status = status=='Y' ? 'N' : 'Y';
				_this.parents(".header-search").attr('data-status', status);
			});
			
			// 상세검색 - 게스트 인원
			$(".guest-control-button").click(e => {
				let type = $(e.currentTarget).data("type");
				let guestEl = $("input[name='max_guest']");
				let guest = guestEl.val();
				
				if( type=='minus' ) 	guest = parseInt(guest)-1;
				else 					guest = parseInt(guest)+1;
				
				if( guest<0 ) 			guest = 0;
				
				guestEl.val(guest);
			});
			
			// 리스트 클릭
			$(document).on("click", ".space-list li", e => {
				let _this = $(e.currentTarget);
				let spaceno = _this.data("spaceno");
				
				// Check
				if( $(e.target).parent().hasClass("space-jjim") ) 	return false;
				
				// Result
				document.location.href = "/spaceHub/space?cmd=detail&spaceno="+spaceno;
			});
			
			// 찜하기
			$(document).on("click", ".space-jjim i", e => {
				// Val
				let _this = $(e.currentTarget);
				let classList = _this.attr("class").split(" ");
				
				let spaceno = _this.parents(".list-item").data("spaceno");
				let processType = classList[1]=="bi-heart" ? "likeWriteOk" : "likeDeleteOk";
				let iconName = classList[1]=="bi-heart" ? "bi-heart-fill" : "bi-heart";
				
				// Process
				$.ajax({
					type: "GET",
					url: "/spaceHub/space",
					data: {cmd: processType, spaceno: spaceno},
					dataType: "json",
					success: data => {
						
						if( data.errorCode ){
							alert(data.errorMsg);
							if( data.errorCode==="member empty" ){
								document.location.href = "/spaceHub/sign?cmd=login";
							}
							
							return false;
						}
						
						// 찜 아이콘 변경
						let className = _this.attr("class");
						className = className.replace(classList[1], iconName);
						_this.attr("class", className);
					}
				});
			});
			
			// 더보기
			$(".space-more").click(e => {
				let page = $("form[name='spaceSearchForm'] input[name='page']").val();
				
				page = parseInt(page)+1;
				callList(page);
			});
			
			// 윈도우 스크롤 이벤트
			$(window).scroll(e => {
				scrollDetect(window);
			})
			
		});
		
		const callList = page => {
			// Val
			let form = $("form[name='spaceSearchForm']");
			let appendTemplate = $("#space-template").html();
			let appendEl = $(".space-list");
			let emptyEl = $(".space-empty");
			
			// Init
			emptyEl.hide();
			moreViewStatus = false;
			
			// Data
			page = page ? page : 1;
			form.find("input[name='page']").val(page);
		
			// Process
			$.ajax({
				type: "GET",
				url: "/spaceHub/space",
				data: form.serialize(),
				dataType: "json",
				success: result => {
					console.log(result);
					
					let spaceData = result.data;
					let pageData = result.paging;
					
					// 첫번째 페이지로 들어올 경우 리스트 초기화
					if( page==1 ) appendEl.html("");
					
					// 더 보여줄 데이터가 있을경우 더보기 버튼 노출
					if( pageData.remainCount>0 ) 	moreViewStatus = true;
				
					// 데이터가 없을 경우
					if( spaceData.length<1 ) {
						appendEl.hide();
						emptyEl.show();
						return false;
					}
					
					// 데이터 추가
					appendEl.show();
					spaceData.forEach(obj => {
						let appendHtml = $(appendTemplate);
						
						appendHtml.attr("data-spaceno", obj.spaceno);
						appendHtml.find(".thumbnail-image").attr("src", obj.path);
						appendHtml.find(".subject-name").text(obj.subject);
						appendHtml.find(".rating-value").text(obj.rating);
						appendHtml.find(".info-addr").text(obj.addr);
						appendHtml.find(".info-date").text(obj.inDate+" ~ "+obj.outDate);
						appendHtml.find(".price-value").text(obj.priceFormat);
						
						// 찜 아이콘
						let jjimIconClass = obj.jjimStatus=='Y' ? "bi-heart-fill" : "bi-heart";
						appendHtml.find(".jjim-icon").addClass(jjimIconClass);
						
						// html 추가
						appendEl.append(appendHtml);
					});
					
					scrollDetect(window);
				},
				complete: () => {
					// 검색창 닫기
					let searchViewStatus = $(".header-search").attr("data-status");
					if( searchViewStatus=='Y' ){
						$(".header-search").attr("data-status", "N");
					}
				}
			});
			
			return false;
		}
		
		const scrollDetect = e => {
			let moreEl = $(".space-more");
			let innerHeight = $(e).innerHeight();
			let scrollTop = $(e).scrollTop();
			let scrollHeight = $("body").prop("scrollHeight");
			
			if( moreViewStatus && innerHeight+scrollTop>=scrollHeight-200 ){
				moreEl.fadeIn(300);
			} else {
				moreEl.fadeOut(300);
			}

		}
	</script>
	
<jsp:include page="./common/header.jsp" />

	<div class="main">
		<div class="inner">
			<div class="space">
				<ul class="space-list"></ul>
				<div class="space-empty">
					<i class="bi bi-database-x"></i>
					<p>검색되는 공간이 없습니다 :(</p>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 리스트 더보기 -->
	<div class="space-more">
		<button type="button">
			<i class="bi bi-plus-lg"></i>
			더보기
		</button>
	</div>
	
	
	<!-- 공간 리스트 템플릿 -->
	<template id="space-template">
		<li class="list-item" data-spaceno="">
			<div class="item-thumbnail">
				<img src="" alt="" class="thumbnail-image" />
				<span class="space-jjim">
					<i class="jjim-icon bi"></i>
				</span>
			</div>
			<div class="item-subject">
				<p class="subject-name"></p>
				<span class="subject-rating">
					<i class="bi bi-star-fill"></i>
					<span class="rating-value"></span>
				</span>
			</div>
			<div class="item-info">
				<p class="info-addr"></p>
				<p class="info-date"></p>
			</div>
			<div class="item-price">
				₩ <span class="price-value"></span>
				/ 1박
			</div>
		</li>
	</template>
	

</body>
</html>