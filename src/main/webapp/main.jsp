<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="./common/common.jsp" />
	
	<title>spaceHub</title>
	<link rel="stylesheet" href="/spaceHub/css/air-datepicker.css" />
	<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
	<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css"/>
	<style>
		.space {}
		
		.space-list { display: flex; justify-content: flex-start; flex-wrap: wrap; }
		.space-list .list-item { width: calc(25% - 6px); border-radius: 10px; margin-right: 8px; margin-top: 20px; padding: 10px; overflow: hidden; cursor: pointer; transition: box-shadow .4s; }
		.space-list .list-item:nth-child(4n) { margin-right: 0; }
		.space-list .list-item:nth-child(-n+4) { margin-top: 0; }
		.space-list .list-item:hover { border: 1px soild #ccc; box-shadow: rgba(0, 0, 0, 0.3) 0px 2px 10px; transition: box-shadow .4s; }
		
		.space-list .list-item .item-thumbnail { position: relative; overflow: hidden; border-radius: 15px; }
		.space-list .list-item .item-thumbnail img { width: 100%; height: 300px; vertical-align: top; }
		.space-list .list-item .item-thumbnail .space-jjim { position: absolute; top: 10px; right: 10px; }
		.space-list .list-item .item-thumbnail .space-jjim i { font-size: 18px; color: #fff; }
		.space-list .list-item .item-thumbnail .space-jjim .bi-heart-fill { color: red; }
		
		.space-list .list-item .item-subject { display: flex; justify-content: space-between; margin-top: 20px; }
		.space-list .list-item .item-subject .subject-name { width: calc(100% - 80px); font-weight: bold; font-size: 16px; }
		.space-list .list-item .item-subject .subject-rating{ width: 80px; text-align: center; }
		.space-list .list-item .item-info { margin-top: 10px; }
		.space-list .list-item .item-info .info-addr { margin-top: 5px; font-size: 14px; color: #aaa; }
		.space-list .list-item .item-info .info-date { font-size: 14px; color: #aaa; }
		.space-list .list-item .item-price { margin-top: 10px; }
		.space-list .list-item .item-price span { font-weight: bold; font-size: 18px; }
		
		.space-empty { display: none; padding: 30px 0; text-align: center; }
		.space-empty i { font-size: 30px; color: #666; }
		.space-empty p { margin-top: 20px; font-weight: bold; font-size: 18px; line-height: 30px; color: #333; }
	
		.space-more { display: none; position: fixed; bottom: 30px; left: 50%; transform: translateX(-50%); }
		.space-more button { background: #333; border: none; border-radius: 10px; padding: 10px 20px; font-size: 16px; color: #fff; cursor: pointer; }
		
		
		/* slick 플러그인 제어 */
		.slick-slider {}
		.slick-dotted.slick-slider { margin-bottom: 0; }
		
		.slick-prev, .slick-next { z-index: 2; }
		.slick-prev { left: 10px; }
		.slick-next { right: 10px; }
		
		.slick-dots { bottom: 10px; }
		.slick-dots li button:before { font-size: 10px; color: #fff; }
		.slick-dots li.slick-active button:before { color: #fff; }
		
		.air-datepicker { z-index: 9999; }
	</style>
	
	<script src="/spaceHub/js/air-datepicker.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
	<script>
		let searchState = false;
		let moreViewState = false;
	
		$(() => {
			// 초기 리스트 호출
			callList();
			
			// datepicker
			const datepickerOption = {autoClose: true};
			const inDatepicker = new AirDatepicker("input[name='in_date']", datepickerOption);
			const outDatepicker = new AirDatepicker("input[name='out_date']", datepickerOption);
			
			// 검색 메뉴 클릭
			$("#header .search-toggle").on("click", e => {
				let searchEl = $("#header .header-search");
				let active = searchEl.hasClass("active");
				
				if( active ){
					searchState = false;
					searchEl.removeClass("active");
				}
				else{
					searchState = true;
					searchEl.addClass("active");
				}
			});
			
			// 검색메뉴 외 클릭시 닫기
			$(document).on("click", e => {
				let target = $(e.target);
				let searchEl = $("#header .header-search");
				
				
				
				if( searchState && !target.is('#header, #header *') && !target.is(".air-datepicker, .air-datepicker *") ){
					searchState = false;
					searchEl.removeClass("active");
				}
			});
			
			// 상세검색 - 게스트 인원
			$("#header .guest-control-button").click(e => {
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
				if( $(e.target).parent().hasClass("space-jjim") || $(e.target).is(".slick-dots *") || $(e.target).is(".slick-arrow") ){
					return false;
				}
				
				// Result
				document.location.href = "/spaceHub/space?cmd=detail&spaceno="+spaceno;
			});
			
			// 찜하기
			$(document).on("click", ".space-jjim i", e => {
				// Val
				let _this = $(e.currentTarget);
				let classList = _this.attr("class").split(" ");
				
				let spaceno = _this.parents(".list-item").attr("data-spaceno");
				let processType = classList[2]=="bi-heart" ? "likeWriteOk" : "likeDeleteOk";
				let iconName = classList[2]=="bi-heart" ? "bi-heart-fill" : "bi-heart";
				
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
						className = className.replace(classList[2], iconName);
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
			});
			
		});
		
		const callList = page => {
			// Val
			let form = $("form[name='spaceSearchForm']");
			let appendTemplate = $("#space-template").html();
			let appendEl = $(".space-list");
			let emptyEl = $(".space-empty");
			
			// Init
			emptyEl.hide();
			moreViewState = false;
			
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
					let spaceData = result.data;
					let pageData = result.paging;
					
					// 첫번째 페이지로 들어올 경우 리스트 초기화
					if( page==1 ) appendEl.html("");
					
					// 더 보여줄 데이터가 있을경우 더보기 버튼 노출
					if( pageData.remainCount>0 ) 	moreViewState = true;
				
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
						
						// 이미지 태그 생성
						obj.imgList.forEach((path, idx) => {
							appendHtml.find(".thunbnail-slider").append("<img src='"+path+"' />");
						});
						
						// - 이미지가 없다면 기본 이미지 하나를 지정
						if( obj.imgList.length<1 ){
							appendHtml.find(".thunbnail-slider").append("<img src='/spaceHub/upload/profile_empty.jpeg' />");
						}
						
						// 찜 아이콘
						let jjimIconClass = obj.userJjimStatus=='Y' ? "bi-heart-fill" : "bi-heart";
						appendHtml.find(".jjim-icon").addClass(jjimIconClass);
						
						// 데이터 담기
						appendHtml.attr("data-spaceno", obj.spaceno);
						appendHtml.find(".subject-name").text(obj.subject);
						appendHtml.find(".rating-value").text(obj.rating);
						appendHtml.find(".info-addr").text(obj.addr);
						appendHtml.find(".info-date").text(obj.inDate+" ~ "+obj.outDate);
						appendHtml.find(".price-value").text(obj.priceFormat);
						
						if( obj.rating=="0.0" ){
							appendHtml.find(".subject-rating").hide();
						}
						
						// html 추가
						appendEl.append(appendHtml);
						
						// 플러그인 추가
						appendHtml.find(".thunbnail-slider").slick({
							slidesToShow : 1,
							slidesToScroll : 1,
							dots : true,
							arrows: true,
							draggable: false,
						});
					});
					
					scrollDetect(window);
				},
				complete: () => {
					// 검색창 닫기
					$("#header .header-search").removeClass("active");
				}
			});
			
			return false;
		}
		
		const scrollDetect = e => {
			let moreEl = $(".space-more");
			let innerHeight = $(e).innerHeight();
			let scrollTop = $(e).scrollTop();
			let scrollHeight = $("body").prop("scrollHeight");
			
			if( moreViewState && innerHeight+scrollTop>=scrollHeight-200 ){
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
				<div class="thunbnail-slider">
					
				</div>
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