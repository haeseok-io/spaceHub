<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../common/common.jsp" />

<title>메시지 - spaceHub</title>

<script>
	$(() => {
		callList();
	});
	
	const callList = () => {
		
		
		$.ajax({
			type: "GET",
			url: "/spaceHub/message",
			data: {cmd: "listData"},
			dataType: "json",
			success: result => {
				console.log(result);
			}
		});
		
	}
</script>

<jsp:include page="../common/header.jsp" />

	<div class="main">
		<div class="inner">
			<div class="message-list">
				<div class="message-title">
					<p class="title-name">메시지</p>
				</div>
				<div class="list-wrap">
					<ul class="my-list">
						<li class="list-item">
							<div class="item-img">
								<img src="/spaceHub/upload/profile_empty.jpeg" alt="" />
							</div>
							<div class="item-info">
								<p class="info-name">홍길동</p>
								<p class="info-msg">최근에 전송된 문의 ....</p>
								<div class="info-etc">
									<p class="etc-status">답변/문의 상태</p>
									<p class="etc-date">전송</p>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="message-content">
				<div class="message-title">
					<p class="title-name">내용</p>
				</div>
			</div>
			<div class="message-space-detail">
				<div class="message-title">
					<p class="title-name">숙소 상세정보</p>
				</div>
			</div>
		</div>
		
			${sessionScope.member}
			<h2>message.jsp</h2>
	</div>

</body>
</html>