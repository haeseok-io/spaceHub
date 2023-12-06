<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>spaceHub</title>
	
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
	<link rel="stylesheet" href="/spaceHub/css/common.css" />
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
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script>
		$(() => {
			// 상세검색
			$(".search-toggle .toggle-item").click(e => {
				let _this = $(e.currentTarget);
				let status = _this.data('status');
				
				console.log(status);
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
			$(".space-jjim i").click(e => {
				let _this = $(e.currentTarget);
				let classList = _this.attr("class").split(" ");
				
				let spaceno = _this.parents(".list-item").data("spaceno");
				let processType = classList[1]=="bi-heart" ? "likeWriteOk" : "likeDeleteOk";
				let iconName = classList[1]=="bi-heart" ? "bi-heart-fill" : "bi-heart";
				
				$.ajax({
					type: "GET",
					url: "/spaceHub/space",
					data: {cmd: processType, spaceno: spaceno},
					dataType: "json",
					success: data => {
						let className = _this.attr("class");
						className = className.replace(classList[1], iconName);
						_this.attr("class", className);
					},
					error: error => {
						let errorData = error.responseJSON;
						alert(errorData.error);
					}
				});
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
						<form action="/spaceHub/home" method="get">
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
			<div class="space">
				<ul class="space-list">
					<c:forEach var="data" items="${requestScope.list}">
						<li class="list-item" data-spaceno="${data.spaceno}">
							<div class="item-thumbnail">
								<img src="${data.path}" width="150" alt="">
								<span class="space-jjim">
									<c:if test="${data.userJjimStatus}">
									<i class="bi bi-heart-fill"></i>
									</c:if>
									<c:if test="${!data.userJjimStatus}">
									<i class="bi bi-heart"></i>
									</c:if>
								</span>
							</div>
							<div class="item-subject">
								<p class="subject-name">${data.subject}</p>
								<span class="subject-rating">
									<i class="bi bi-star-fill"></i>
									${data.rating}
								</span>
							</div>
							<div class="item-info">
								<p class="info-addr">${data.addr}</p>
								<p class="info-date">
									${data.inDate} ~ ${data.outDate}
								</p>
							</div>
							<div class="item-price">
								<span class="price">₩ ${data.priceFormat}</span>
								/ 1박
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>

		</div>
	</div>

</body>
</html>