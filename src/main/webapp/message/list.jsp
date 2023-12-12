<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../common/common.jsp" />

	<title>메시지 - spaceHub</title>
	
	<style>
		#header { position: fixed; top: 0; left: 0; width: 100%; }
		#header .inner { width: 100%; padding: 0 30px; }
	
	
		.main { position: fixed; top: 100px; left: 0; display: flex; justify-content: flex-start; align-items: flex-start; width: 100%; min-width: 1400px; height: calc(100% - 100px); }
		.main > div { height: 100%; }
		.message-title { display: flex; justify-content: space-between; align-items: center; width: 100%; height: 80px; border-bottom: 1px solid #ccc; padding: 0 30px; }
		.message-title .title-name { font-weight: bold; font-size: 20px; }
		.message-wrap { height: calc(100% - 80px); overflow-y: auto; padding: 30px; }
		
		/* 메시지 리스트 */
		.message-list { width: 450px; border-right: 1px solid #ccc; }
		.message-list .message-wrap .list-item li { display: flex; justify-content: space-between; align-items: center; width: 100%; height: 100px; border-radius: 10px; padding: 10px 20px; margin-bottom: 10px; cursor: pointer; }
		.message-list .message-wrap .list-item .item-profile { position: relative; width: 50px; height:50px; }
		.message-list .message-wrap .list-item .item-profile .profile-img { position: relative; width: 100%; height: 100%; border: 1px solid #333; border-radius: 100%;  overflow: hidden; }
		.message-list .message-wrap .list-item .item-profile .profile-img img { position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); width: 100%; height: 100%; object-fit: cover; }

		.message-list .message-wrap .list-item li.active,
		.message-list .message-wrap .list-item li:hover { background: #f1f1f1; border: 1px solid #e0e0e0; }
		.message-list .message-wrap .list-item li.new .item-profile:after { position: absolute; top: 0; left: 0; width: 10px; height: 10px; background: red; border-radius: 100%; content: ""; display: block; }
		
		.message-list .message-wrap .list-item .item-info { width: calc(100% - 70px); font-size: 12px; }
		.message-list .message-wrap .list-item .item-info .info-subject { display: flex; justify-content: flex-start; align-items: center; }
		.message-list .message-wrap .list-item .item-info .info-subject .badge { margin-right: 5px; padding: 4px 6px; font-weight: normal; font-size: 10px; }
		.message-list .message-wrap .list-item .item-info .info-subject .subject-name { font-size: 14px; }
		.message-list .message-wrap .list-item .item-info .info-subject .subject-spacename { color: #999; }
		.message-list .message-wrap .list-item .item-info .info-subject .subject-spacename:before { content: "·"; padding: 0 5px; }
		.message-list .message-wrap .list-item .item-info .info-contents { width: 100%; margin-top: 5px; font-size: 14px; overflow: hidden; white-space: nowrap; text-overflow:ellipsis }
		.message-list .message-wrap .list-item .item-info .info-alarm { color: #999; }
		
		/* 메시지 본문 */
		.message-content { position: relative; width: calc(100% - 850px); }
		.message-content .message-wrap { height: calc(100% - 150px); }
		.message-content .message-wrap li { display: flex; justify-content: flex-start; align-items: flex-start; width: 100%; padding-bottom: 30px; }
		.message-content .message-wrap .item-profile { width: 50px; height:50px; border: 1px solid #333; border-radius: 100%; margin-right: 20px;  overflow: hidden; }
		.message-content .message-wrap .item-profile img { width: 100%; height: 100%; object-fit: cover; }
		
		.message-content .message-wrap .item-info { width: calc(100% - 70px); }
		.message-content .message-wrap .item-info .info-subject { display: flex; align-items: center; }
		.message-content .message-wrap .item-info .info-subject .subject-name { margin-right: 10px; font-weight: bold; font-size: 16px; color: #333; }
		.message-content .message-wrap .item-info .info-subject .subject-date { font-size: 12px; color: #888; }
		.message-content .message-wrap .item-info .info-contents { margin-top: 5px; font-size: 14px; color: #666; }
		
		.message-content .message-write { position: absolute; bottom: 30px; left: 50%; transform: translateX(-50%); width: 60%; }
		.message-content .message-write input[type='text'] { width: 100%; height: 40px; border: 1px solid #ccc; border-radius: 10px; padding-left: 10px; }
		.message-content .message-write input[type='text']:focus { border: 2px solid #333; }
		
		/* 공간 상세정보 */
		.message-space { width: 400px; border-left: 1px solid #ccc; }
		.message-space .space-wrap {}
		.message-space .space-wrap .space-img {}
		.message-space .space-wrap .space-img img { width: 100%; height: 300px; object-fit: cover; }
		.message-space .space-wrap .space-subject { margin-top: 10px; font-weight: bold; font-size: 16px; color: #333; }
		.message-space .space-wrap .space-addr { margin-top: 5px; font-size: 14px; color: #888; }
		.message-space .space-wrap .space-info { margin-top: 10px; color: #666; }
		.message-space .space-wrap .space-info .info-price { font-size: 14px; }
		.message-space .space-wrap .space-info .info-price .price-data { font-weight: bold; font-size: 16px; color: #333; }
		
		.message-space .space-wrap .space-more { margin-top: 20px; }
		.message-space .space-wrap .space-more button { width: 100%; height: 40px; background: #333; border: 0; font-weight: bold; font-size: 14px; color: #fff; }
	</style>
	
	<script>
		const MEMNO = "${member.memno}";
		
		$(() => {
			// 파라미터값 추출
			const param = new URLSearchParams(location.search);
			
			// 초기 리스트 호출
			callList(param.get("bno"));
			
			// 리스트 클릭
			$(document).on("click", ".message-list .message-wrap ul li", e => {
				// Val
				let _this = $(e.currentTarget);
				let form = $("form[name='writeForm']");
				let contentTitle = _this.find(".subject-name").text()+" - "+_this.find(".subject-spacename").text();
				
				// Data
				_this.removeClass("new");
				$("input[name='bno']", form).val(_this.data("bno"));
				$("input[name='spaceno']", form).val(_this.data("spaceno"));
				$("input[name='contents']").attr("disabled", false);
				
				// Etc
				_this.addClass("active").siblings().removeClass("active");
				$(".message-content .title-name").text(contentTitle);
				
				// Result
				callContent(_this.data("bno"));
				callSpaceDetail(_this.data("spaceno"));
			});
		});
		
		// 메시지 리스트 호출
		const callList = bno => {
			let appendTemplate = $("#list-template").html();
			let appendEl = $(".message-list .list-item");
			
			// Init 
			appendEl.html("");
			
			$.ajax({
				type: "GET",
				url: "/spaceHub/message",
				data: {cmd: "listData"},
				dataType: "json",
				success: result => {
					let listData = result.data;
					
					if( listData.length<1 ){
						appendEl.hide();
						return false;
					}
					
					listData.forEach(obj => {
						let appendHtml = $(appendTemplate);
						let spaceOwnStatus = obj.spaceOwnStatus==1 ? true : false;
						let mnickname = obj.mnickname ? obj.mnickname : obj.mname;
						let wnickname = obj.wnickname ? obj.wnickname : obj.wname;
						let hnickname = obj.hnickname ? obj.hnickname : obj.hname;
						
						
						let nickname = "";
						let profileImg = "";
						let alarmMsg = "";
						
						
						// 새로운 메세지 알림
						if( obj.mmemno!=MEMNO && obj.status==1 ) {
							appendHtml.addClass("new");
						}
						
						// 본인 공간에 대한 메세지 일 경우
						if( spaceOwnStatus ){
							nickname = wnickname;
							profileImg = obj.wprofileImg;
							
							if( obj.mmemno==obj.hmemno ) 	alarmMsg = wnickname+" 님에게 메시지를 전송했습니다.";
							else 							alarmMsg = mnickname+" 님에게 메시지가 도착했습니다.";
						}
						// 다른 공간에 문의한 메시지 일 경우
						else {
							nickname = hnickname;
							profileImg = obj.hprofileImg;
							
							if( obj.mmemno==obj.hmemno ) 	alarmMsg = hnickname+" 님에게 메시지가 도착했습니다.";
							else 							alarmMsg = hnickname+" 님에게 메시지를 전송했습니다.";
						}
						
						// 문의글인 경우 라벨 추가
						if( spaceOwnStatus ){
							let qnaBadge = "<span class='badge text-bg-success'>공간문의</span>";
							appendHtml.find(".info-subject").prepend(qnaBadge);
						}
						
						// 데이터 담기
						appendHtml.attr("data-bno", obj.bno);
						appendHtml.attr("data-spaceno", obj.spaceno);
						appendHtml.find(".profile-img img").attr("src", profileImg);
						appendHtml.find(".subject-name").text(nickname);
						appendHtml.find(".subject-spacename").text(obj.spaceName);
						appendHtml.find(".info-contents").text(obj.contents);
						appendHtml.find(".info-alarm").text(alarmMsg);
						
						appendEl.append(appendHtml);
					});
					
					// 리스트 클릭 트리거
					if( !bno && typeof bno!="undefined" ) 	appendEl.find("li").eq(0).trigger("click");
					else 									appendEl.find("li[data-bno='"+bno+"']").trigger("click");
				}
			});
		}
		
		// 메시지 본문 호출
		const callContent = bno => {
			let appendTemplate = $("#content-template").html();
			let appendEl = $(".message-content .content-item");
			
			// Init
			appendEl.html("");
			
			// Process
			$.ajax({
				type: "GET",
				url: "/spaceHub/message",
				data: {cmd: "contentData", bno: bno},
				dataType: "json",
				success: result => {
					let contentData = result.data;
					
					contentData.forEach(obj => {
						let appendHtml = $(appendTemplate);
						
						appendHtml.find(".item-profile img").attr("src", obj.wprofileImg);
						appendHtml.find(".subject-name").text(obj.wnickname);
						appendHtml.find(".subject-date").text(obj.regdate);
						appendHtml.find(".info-contents").text(obj.contents);
						
						appendEl.append(appendHtml);
					});
					
					// 스크롤 하단으로 이동
					let scrollEl = $(".message-content .message-wrap");
					scrollEl.scrollTop(scrollEl[0].scrollHeight);
				}
			});
		}
		
		// 메시지 공간 호출
		const callSpaceDetail = spaceno => {
			let appendTemplate = $("#space-template").html();
			let appendEl = $(".message-space .message-wrap");
			
			// Init
			appendEl.html("");
			
			// Process
			$.ajax({
				type: "GET",
				url: "/spaceHub/space",
				data: {cmd: "detailData", spaceno: spaceno},
				dataType: "json",
				success: result => {
					let detailData = result.data;
					let appendHtml = $(appendTemplate);
					
					detailData.price = detailData.price.replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
					
					appendHtml.attr("data-spaceno", detailData.spaceno);
					appendHtml.find(".space-img img").attr("src", detailData.imgPath);
					appendHtml.find(".space-subject").text(detailData.subject);
					appendHtml.find(".space-addr").text(detailData.addr);
					appendHtml.find(".price-data").text("\\"+detailData.price);
					
					appendEl.append(appendHtml);
				}
			});
			
			
			
		}
		
		// 메시지 작성
		const messageWrite = () => {
			// Val
			let form = $("form[name='writeForm']");
			let contentEl = $("input[name='contents']", form);
			let scrollEl = $(".message-content .message-wrap");
			let appendTemplate = $("#content-template").html();
			let appendEl = $(".message-content .content-item");
			
			let bno = $("input[name='bno']", form).val();
			let spaceno = $("input[name='spaceno']", form).val();
			let contents = contentEl.val();
			
			// Check
			if( !contents ){
				return false;
			}
			if( !spaceno || !bno ) {
				alert("메시지 작성시 필요한 정보가 유실되었습니다. 리스트를 다시 클릭 후 진행 해주세요.");
				return false;
			}
			
			// Process
			$.ajax({
				type: "POST",
				url: "/spaceHub/message",
				data: form.serialize(),
				dataType: "json",
				success: result => {
					
					// Check
					if( result.errorCode ){
						alert(result.errorMsg);
						return false;
					}
					
					
					// Result
					callList();
					callContent(bno);
					
					contentEl.val("");
					scrollEl.scrollTop(scrollEl[0].scrollHeight);
				}
			});
			
			return false;
		}
		
		const getNowDateTime = () => {
			let date = new Date();
			return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
		}
	</script>

<jsp:include page="../common/header.jsp" />

	<div class="main">
		<div class="message-list">
			<div class="message-title">
				<p class="title-name">메시지</p>
			</div>
			<div class="message-wrap">
				<ul class="list-item"></ul>
			</div>
		</div>
		<div class="message-content">
			<div class="message-title">
				<p class="title-name">내용</p>
			</div>
			<div class="message-wrap">
				<ul class="content-item"></ul>
			</div>
			<div class="message-write">
				<form name="writeForm" autocomplete="off" onsubmit="return messageWrite();">
					<input type="hidden" name="cmd" value="writeOk" />
					<input type="hidden" name="bno" />
					<input type="hidden" name="spaceno" />
				
					<input type="text" name="contents" placeholder="메시지를 입력하세요." disabled/>
				</form>
			</div>
		</div>
		<div class="message-space">
			<div class="message-title">
				<p class="title-name">숙소정보</p>
			</div>
			<div class="message-wrap">
				
			</div>
		</div>
	</div>
	
	<template id="list-template">
		<li>
			<div class="item-profile">
				<div class="profile-img">
					<img src="" alt="" />
				</div>
			</div>
			<div class="item-info">
				<div class="info-subject">
					<p class="subject-name"></p>
					<p class="subject-spacename"></p>
				</div>
				<p class="info-contents"></p>
				<p class="info-alarm"></p>
			</div>
		</li>
	</template>
	
	<template id="content-template">
		<li>
			<div class="item-profile">
				<img src="" alt="" />
			</div>
			<div class="item-info">
				<div class="info-subject">
					<p class="subject-name"></p>
					<p class="subject-date"></p>
				</div>
				<p class="info-contents"></p>
				<p class="info-status"></p>
			</div>
		</li>
	</template>
	
	<template id="space-template">
		<div class="space-wrap">
			<div class="space-img">
				<img src="" alt="" />
			</div>
			<p class="space-subject"></p>
			<p class="space-addr"></p>
			<div class="space-info">
				<div class="info-price">
					<span class="price-data"></span>
					/ 1박
				</div>
			</div>
			<div class="space-more">
				<button>상세보기</button>
			</div>
		</div>
	</template>

</body>
</html>