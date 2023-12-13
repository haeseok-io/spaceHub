<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	<script>
		let menuState = false;
	
		$(() => {
			// 메뉴 아이콘 클릭
			$("#header .menu-toggle").on("click", e => {
				let menuEl = $("#header .user-menu");
				let active = menuEl.hasClass("active");
				
				if( active ){
					menuState = false;
					menuEl.removeClass("active");
				}
				else{
					menuState = true;
					menuEl.addClass("active");
				}
			});
			
			
			document.addEventListener('click', e => {
				let target = $(e.target);
				let menuEl = $("#header .user-menu");
				
				if( menuState && !target.is('.menu-toggle, .menu-toggle *') ){
					menuState = false;
					menuEl.removeClass("active");
				}
			});
		});
	</script>

</head>
<body>
	<div id="header">
		<div class="inner">
			<div class="header-logo">
				<h1>
					<a href="/spaceHub/home">
						<img src="/spaceHub/upload/spaceHub-logo.png">
					</a>
				</h1>
			</div>
			<c:if test="${requestScope['javax.servlet.forward.servlet_path']=='/home'}">
			<div class="header-search">
				<div class="search-toggle">
					<div class="toggle-item" data-status="N">
						어디든 | 언제든 일주일 | 게스트
						<i class="bi bi-search"></i>
					</div>
					<div class="toggle-item" data-status="Y">
						원하는 조건을 검색해보세요.
						<i class="bi bi-x-lg"></i>
					</div>
				</div>
				
				<div class="search-field">
					<div class="search-wrap">
						<form name="spaceSearchForm" onsubmit="return callList();">
							<input type="hidden" name="cmd" value="list" />
							<input type="hidden" name="page" value="1" />
							<input type="hidden" name="scale" value="12" />
							
							<ul class="search-list">
								<li class="list-item">
									<p class="item-title">여행</p>
									<div class="item-data">
										<input type="text" name="subject" placeholder="여행지 검색" value="" />
									</div>
								</li>
								<li class="list-item">
									<p class="item-title">체크인</p>
									<div class="item-data">
										<input type="text" name="in_date" placeholder="날짜 입력" readonly />
									</div>
								</li>
								<li class="list-item">
									<p class="item-title">체크아웃</p>
									<div class="item-data">
										<input type="text" name="out_date" placeholder="날짜 입력" readonly />
									</div>
								</li>
								<li class="list-item guest">
									<p class="item-title">게스트</p>
									<div class="item-data">
										<button type="button" class="guest-control-button" data-type="minus">
											<i class="bi bi-dash-lg"></i>
										</button>
										<input type="text" name="max_guest" placeholder="게스트 인원" value="0" />
										<button type="button" class="guest-control-button" data-type="plus">
											<i class="bi bi-plus-lg"></i>
										</button>
									</div>
								</li>
							</ul>
							<div class="search-button">
								<button type="submit">검색</button>
							</div>
						</form>					
					</div>
				</div>
			</div>
			
			<form name="testForm">
				<input type="hidden" name="x" />
			</form>
			
			
			</c:if>
			<div class="header-user">
				<div class="user-advertise">
					<p><a href="/spaceHub/space?cmd=write">당신의 공간을 공유해보세요.</a></p>
				</div>
				<div class="user-menu">
					<div class="menu-toggle">
						<i class="bi bi-list"></i>
						<i class="bi bi-person-circle"></i>
					</div>
					<div class="menu-wrap">
						<ul class="menu-list">
							<c:if test="${sessionScope.member==null}">
							<li><a href="/spaceHub/sign?cmd=login" class="bold">로그인</a></li>
							<li><a href="/spaceHub/sign?cmd=signup" class="bold">회원가입</a></li>
							</c:if>
							
							<c:if test="${sessionScope.member!=null}">
							<li><a href="/spaceHub/message" class="bold">메시지</a></li>
							<li><a href="/spaceHub/mypage/guest" class="bold">예약내역</a></li>
							<li><a href="/spaceHub/mypage?cmd=likeList" class="bold">찜 리스트</a></li>
							<li class="dash"></li>
							<li><a href="/spaceHub/mypage/host">숙소 관리</a></li>
							<li><a href="/spaceHub/mypage">정보 수정</a></li>
							<li class="dash"></li>
							<li><a href="/spaceHub/sign?cmd=logoutOk">로그아웃</a></li>
							</c:if>
						</ul>
					</div>
				</div>
				
			</div>
		</div>
	</div>