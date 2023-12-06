<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

</head>
<body>
	<div id="header">
		<div class="inner">
			<div class="header-logo">
				<h1>
					<a href="">spaceHub</a>
				</h1>
			</div>
			<div class="header-search" data-status="Y">
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
										<button type="button" class="gueest-control-button" data-type="minus">
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
			<div class="header-gnb">
				<c:if test="${sessionScope.member==null}">				
					<a href="/spaceHub/sign?cmd=login">로그인</a>
				</c:if>
				<c:if test="${sessionScope.member!=null}">
					<a href="/spaceHub/sign?cmd=logoutOk">로그아웃</a>
				</c:if>
			</div>
		</div>
	</div>