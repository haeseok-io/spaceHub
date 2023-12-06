<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="./common/common.jsp" />
	
	<title>spaceHub</title>
	<link rel="stylesheet" href="/spaceHub/css/datepicker.css" />
	<style>
		.main .inner { padding: 50px 0; }
		.space-list { display: flex; justify-content: flex-start; flex-wrap: wrap; }
		.space-list .list-item { width: 23%; margin: 20px 1%; overflow: hidden; cursor: pointer; }
		.space-list .list-item .item-thumbnail { position: relative; overflow: hidden; border-radius: 15px; }
		.space-list .list-item .item-thumbnail img { width: 100%; height: 200px; vertical-align: top; }
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
	</style>
	
	<script src="/spaceHub/js/datepicker.js"></script>
	<script src="/spaceHub/js/datepicker.kr.js"></script>
	<script>
		$(() => {
			// datepicker
			$("input[name='in_date']").datepicker({language: 'kr'});
			$("input[name='out_date']").datepicker({language: 'kr'});
			
			
			// 상세검색
			$(".search-toggle .toggle-item").click(e => {
				let _this = $(e.currentTarget);
				let status = _this.data('status');
				
				_this.parents(".header-search").attr('data-status', status);
			});
			
			// 리스트 클릭
			$(".space-list li").click(e => {
				let _this = $(e.currentTarget);
				let spaceno = _this.data("spaceno");
				
				// Check
				if( $(e.target).parent().hasClass("space-jjim") ) 	return;
				
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
							
							return;
						}
						
						// 찜 아이콘 변경
						let className = _this.attr("class");
						className = className.replace(classList[1], iconName);
						_this.attr("class", className);
					}
				});
			});
			
			
			// 초기 리스트 호출
			callList();
		});
		
		const callList = () => {
			// Val
			let form = $("form[name='spaceSearchForm']");
			let appendSel = $(".space-list");
			let appendTemplate = $("#space-template").html();
			
			// Init
			appendSel.html("");
		
			// Process
			$.ajax({
				type: "GET",
				url: "/spaceHub/space",
				data: form.serialize(),
				dataType: "json",
				success: result => {
					
					if( result.data.length<1 ){
						alert("데이터 없음");
						return false;
					}
					
					// 데이터 추가
					result.data.forEach(obj => {
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
						
						appendSel.append(appendHtml);
					});				
				}
			});
			
			return false;
		}
	</script>
	
<jsp:include page="./common/header.jsp" />

	<div class="main">
		<div class="inner">
			<div class="space">
				<ul class="space-list"></ul>
			</div>
		</div>
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