<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
	<link rel="stylesheet" href="/spaceHub/css/common.css" />
	<link rel="stylesheet" href="/spaceHub/css/datepicker.css" />
	<style>
		* { padding: 0; margin: 0; box-sizing: border-box; }
		ul li { list-style: none; }
		
		.inner { width: 1120px; margin: 0 auto; padding: 50px 0; }
		
		.detail-subject { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }
		.detail-subject .subject-name { font-weight: bold; font-size: 26px; }
		.detail-subject .subject-etc {}
		.detail-subject .subject-etc button { background: #fff; border: none; border-radius: 5px; padding: 5px 10px; font-size: 16px; color: #666; cursor: pointer; }
		.detail-subject .subject-etc button:hover { background: #e0e0e0; }
		
		.detail-images { display: flex; justify-content: space-between; }
		.detail-images img { width: 100%; }
		.detail-images .images-main { width: 50%; }
		.detail-images .images-sub { width: 48%; }
		.detail-images .images-sub .images-list { display: flex; justify-content: space-between; flex-wrap: wrap; }
		.detail-images .images-sub .images-list li { width: 49%; }
	</style>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script src="/spaceHub/js/datepicker.js"></script>
	<script src="/spaceHub/js/datepicker.kr.js"></script>
	<script>
		$(() => {
			// 체크인/아웃 날짜 선택
			$(".date-selector").datepicker({
				language: 'kr',
				inline: true,
				range: true,
				minDate: new Date("${detail.inDate}"),
				maxDate: new Date("${detail.outDate}"),
				onSelect: (formatterDate, date, inst) => {
					let dateMsgEl = $(".detail-date .title-name");
					let dateTermEl = $(".detail-date .title-etc");
					let dateMsg = "";
					let dateTerm = "";
					
					// 체크인/아웃 미선택
					if( date.length==0 ){
						dateMsg = "체크인 날짜를 선택해주세요.";
						dateTerm = "여행 날짜를 입력하여 정확한 요금을 확인하세요.";
					} else if( date.length==1 ){
						dateMsg = "체크아웃 날짜를 선택해주세요.";
						dateTerm = "최소 숙박일수 : 2일";
					} else {
						let dateInfo = formatterDate.split(",");
						
						let startDate = new Date(dateInfo[0]);
						let endDate = new Date(dateInfo[1]);
						let dayDiff = (endDate.getTime() - startDate.getTime()) / (1000*3600*24);
						
						dateMsg = "${data.subject} 에서 "+dayDiff+" 박";
						dateTerm = dateInfo[0]+" ~ "+dateInfo[1];
					}
					
					// 문구입력
					dateMsgEl.text(dateMsg);
					dateTermEl.text(dateTerm);
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
						<form action="/spaceHub" method="get">
							<input type="hidden" name="cmd" value="list" />
							
							<ul class="search-list">
								<li class="list-item">
									<p class="item-title">여행</p>
									<div class="item-data">
										<input type="text" name="addr" placeholder="여행지 검색" value="" />
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
				메
			</div>
		</div>
	</div>

	<div class="main">
		<div class="inner">
			<div class="detail-subject">
				<p class="subject-name">${data.subject}</p>
				<div class="subject-etc">
					<button class="share-button">공유하기</button>
					<button class="jjim-button">찜 하기</button>
				</div>
			</div>
			<div class="detail-images">
				<div class="images-main">
					<img src="${images[0].path}" alt="" />
				</div>
				<div class="images-sub">
					<ul class="images-list">
						<c:forEach begin="0" end="3" var="i" step="1">
						<li>
							<img src="${images[i].path}" alt="" />
						</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="detail-content">
				<div class="content-scroll">
					<div class="detail-info">
						<div class="detail-title">
							<p class="title-name">${data.addr}</p>
							<p class="title-etc">최대인원 ${detail.maxGuest} · 침실 ${detail.bed} · 욕실 ${detail.bathroom}</p>
						</div>
						<div class="info-rating">
							<span class="rating-score"></span>
							<span class="rating-star"></span>
						</div>
					</div>
					<div class="detail-text">
						<div class="detail-title">
							<p class="title-name">숙소 설명</p>
						</div>
						<div class="text-wrap">
							${detail.detail}
						</div>
					</div>
					<div class="detail-factory">
						<div class="detail-title">
							<p class="title-name">숙소 편의시설</p>
						</div>
						<div class="factory-wrap">
							<ul class="factory-list">
								<c:if test="${factory.wifi>0}">
								<li><i class="bi bi-wifi"></i> 와이파이</li>
								</c:if>
								<c:if test="${factory.tv>0}">
								<li><i class="bi bi-tv"></i> TV</li>
								</c:if>
								<c:if test="${factory.kitchen>0}">
								<li><i class="bi bi-tv"></i> 주방</li>
								</c:if>
								<c:if test="${factory.wm>0}">
								<li><i class="bi bi-water"></i> 세탁기</li>
								</c:if>
								<c:if test="${factory.aircon>0}">
								<li><i class="bi bi-snow2"></i> 에어컨</li>
								</c:if>
								<c:if test="${factory.canpark>0}">
								<li><i class="bi bi-car-front"></i> 유료 주차 가능</li>
								</c:if>
								<c:if test="${factory.canfreepark>0}">
								<li><i class="bi bi-car-front"></i> 무료 주차 가능</li>
								</c:if>
								<c:if test="${factory.swim>0}">
								<li><i class="bi bi-tsunami"></i> 수영장</li>
								</c:if>
								<c:if test="${factory.bbq>0}">
								<li><i class="bi bi-fire"></i> 바베큐</li>
								</c:if>
								<c:if test="${factory.bbq>0}">
								<li><i class="bi bi-tablet-landscape"></i> 당구대</li>
								</c:if>
								<c:if test="${factory.bbq>0}">
								<li><i class="bi bi-fire"></i> 벽난로</li>
								</c:if>
								<c:if test="${factory.bbq>0}">
								<li><i class="bi bi-megaphone"></i> 화재경보기</li>
								</c:if>
								<c:if test="${factory.bbq>0}">
								<li><i class="bi bi-bandaid"></i> 구급상자</li>
								</c:if>
								<c:if test="${factory.bbq>0}">
								<li><i class="bi bi-fire"></i> 소화기</li>
								</c:if>
							</ul>
						</div>
					</div>
					
					<div class="detail-date">
						<div class="detail-title">
							<p class="title-name">체크인 날짜를 선택해주세요.</p>
							<p class="title-etc">여행 날짜를 입력하여 정확한 요금을 확인하세요.</p>
						</div>
						<div class="date-wrap">
							<div class="date-selector"></div>
						</div>
					</div>
				</div>
				<div class="content-fixed"></div>
			</div>
			<div class="detail-review">
				<div class="datail-title">
					<p class="title-name">숙소 후기</p>
				</div>
				<div class="review-wrap">
					<ul class="review-list">
						<c:forEach var="vo" items="${review}">
						<li>
							<div class="item-subject">
								<p class="subject-name">게스드 : ${vo.subject}</p>
								<p class="subject-etc">
									<span class="etc-rating">
										<c:forEach begin="1" end="${vo.rating}" step="1">
										<i class="bi bi-star-fill"></i>
										</c:forEach>
									</span>
									<span class="etc-date">등록일 : ${vo.regdate}</span>
								</p>
							</div>
							<div class="item-contents">${vo.contents}</div>
						</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			
			<div class="detail-loc">
				<div class="detail-title">
					<p class="title-name">호스팅 지역</p>
				</div>
				<div class="loc-wrap">
					
				</div>
			</div>
			
			<div class="detail-host">
				<div class="detail-title">
					<p class="title-name">호스트 : ${host.name} 님</p>
					<div class="title-etc">회원가입일 : ${host.regdate}</div>
				</div>
				<div class="host-wrap">
					<ul class="host-list">
						<li>호스트 경력 : </li>
						<li>받은후기 : </li>
					</ul>
					<div class="host-message">
						<button>호스트에게 문의하기</button>
					</div>
					<div class="space-order">
						<button onclick="location.href='/spaceHub/order?cmd=info&spaceno=${detail.spaceno}'">예약하기</button>
					</div>
				</div>
			</div>
			
			
		</div>
	</div>
	

</body>
</html>