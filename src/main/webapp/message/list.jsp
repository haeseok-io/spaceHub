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
		.message-list { width: 400px; border-right: 1px solid #ccc; }
		.message-list .message-wrap .list-item li { display: flex; justify-content: space-between; align-items: center; width: 100%; height: 100px; border-radius: 10px; padding: 10px 20px; margin-bottom: 10px; cursor: pointer; }
		.message-list .message-wrap .list-item .item-profile { position: relative; width: 50px; height:50px; }
		.message-list .message-wrap .list-item .item-profile .profile-img { position: relative; width: 100%; height: 100%; border: 1px solid #333; border-radius: 100%;  overflow: hidden; }
		.message-list .message-wrap .list-item .item-profile .profile-img img { position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); width: 100%; height: 100%; object-fit: cover; }

		.message-list .message-wrap .list-item li:hover { background: #f1f1f1; }
		.message-list .message-wrap .list-item li.new .item-profile:after { position: absolute; top: 0; left: 0; width: 10px; height: 10px; background: red; border-radius: 100%; content: ""; display: block; }
		
		.message-list .message-wrap .list-item .item-info { width: calc(100% - 70px); font-size: 12px; }
		.message-list .message-wrap .list-item .item-info .info-subject { display: flex; justify-content: flex-start; align-items: center; }
		.message-list .message-wrap .list-item .item-info .info-subject .badge { margin-right: 5px; padding: 5px 8px; font-weight: normal; font-size: 11px; }
		.message-list .message-wrap .list-item .item-info .info-subject .subject-name { font-size: 14px; }
		.message-list .message-wrap .list-item .item-info .info-subject .subject-spacename { color: #999; }
		.message-list .message-wrap .list-item .item-info .info-subject .subject-spacename:before { content: "·"; padding: 0 5px; }
		.message-list .message-wrap .list-item .item-info .info-contents { width: 100%; margin-top: 5px; font-size: 14px; overflow: hidden; white-space: nowrap; text-overflow:ellipsis }
		.message-list .message-wrap .list-item .item-info .info-alarm { color: #999; }
		
		/* 메시지 본문 */
		.message-content { position: relative; width: calc(100% - 900px); }
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
		.message-space_detail { width: 500px; border-left: 1px solid #ccc; }
	</style>
	
	<script>
		const MEMNO = "3";
	
		$(() => {
			// 초기 리스트 호출
			callList();
			
			// 리스트 클릭
			$(document).on("click", ".message-list .message-wrap ul li", e => {
				// Val
				let _this = $(e.currentTarget);
				let bno = _this.data("bno");
				
				// 본문 호출
				callContent(bno);
			});
		});
		
		
		
		const callList = () => {
			let appendTemplate = $("#list-template").html();
			let appendEl = $(".message-list .list-item");
			
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
							let qnaBadge = "<span class='badge text-bg-success'>문의</span>";
							appendHtml.find(".info-subject").prepend(qnaBadge);
						}
						
						// 데이터 담기
						appendHtml.attr("data-bno", obj.bno);
						appendHtml.find(".profile-img img").attr("src", profileImg);
						appendHtml.find(".subject-name").text(nickname);
						appendHtml.find(".subject-spacename").text(obj.spaceName);
						appendHtml.find(".info-contents").text(obj.contents);
						appendHtml.find(".info-alarm").text(alarmMsg);
						
						appendEl.append(appendHtml);
					});
				}
			});
		}
		
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
				}
			});
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
				<form name="messageWriteForm">
					<input type="hidden" name="cmd" value="messageWriteOk" />
					<input type="hidden" name="bno" />
					<input type="hidden" name="spaceno" />
					<input type="hidden" name="memno" value="${member.memno}" />
				
					<input type="text" name="contents" placeholder="메시지를 입력하세요." disabled/>
				</form>
			</div>
		</div>
		<div class="message-space_detail">
			<div class="message-title">
				<p class="title-name">숙소 상세정보</p>
			</div>
			<div class="space_detail-wrap"></div>
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

</body>
</html>