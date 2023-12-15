<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/common/common.jsp" />
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css"/>
<style>
	.content-wrap { display: flex; flex-wrap: wrap; }
	.content-wrap .content { width: 25%; padding: 0 20px; margin-bottom: 30px; }
	
    .content-wrap .content .wrap { position: relative; width: 100%; height: 200px; border-radius: 10px; overflow: hidden; margin-bottom: 10px; }
    .content-wrap .content .wrap .slick-list { width: 100%; height: 100%; }
    .content-wrap .content .wrap .slick-list .slick-track { height: 100%; }
    .content-wrap .content .wrap .slick-list .slick-track img { width: 100%; height: 100%; object-fit: cover; }

	.content-wrap .content .subject { display: block; width: 100%; font-weight: bold; font-size: 18px; text-overflow: ellipsis; overflow: hidden;  white-space: nowrap;  }
	.content-wrap .content .addr { display: block; width: 100%; font-size: 14px; color: #666; text-overflow: ellipsis; overflow: hidden;  white-space: nowrap; }
	.content-wrap .content .price { margin-top: 10px; font-size: 14px; color: #666; }
	.content-wrap .content .price span { font-weight: bold; font-size: 16px; color: #333; }
    
    
    /* slick 플러그인 제어 */
	.slick-slider {}
	.slick-dotted.slick-slider { margin-bottom: 0; }
	
	.slick-prev, .slick-next { z-index: 2; }
	.slick-prev { left: 10px; }
	.slick-next { right: 10px; }
	
	.slick-dots { bottom: 10px; }
	.slick-dots li button:before { font-size: 10px; color: #fff; }
	.slick-dots li.slick-active button:before { color: #fff; }
</style>
<script src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
<script>
	$(document).ready(function() {
		$('.slider').slick({
			slidesToShow : 1,
			slidesToScroll : 1,
			autoplay : false,
			dots : true,
			pauseOnFocus: true,
		});
	});
</script>
<jsp:include page="/common/header.jsp" />

	<form action="/mypage" method="post">
		<input type="hidden" name="cmd" value="likeList"/>
		<input type="hidden" name="memno" value="${member.memno }" />
		<div class="container">		
			<h1>찜한 숙소</h1>
			<div class="content-wrap">
				<c:forEach var="vo" items="${jjimList }">
				<div class="content">
					<div class="wrap slider">
						<c:forEach var="img" items="${vo.imgList }">
							<a href="/spaceHub/space?cmd=detail&spaceno=${vo.spaceno }">
								<img src="${img.path }" alt="" />			
							</a>
						</c:forEach>
					</div>
					<div class="subject">${vo.subject }</div>
					<div class="addr">${vo.addr }</div>
					<div class="price">₩ <span>${vo.price }</span> / 1박</div>
				</div>
				</c:forEach>
			</div>
		</div>
		<c:if test="${empty jjimList}">
	    <p>찜한 숙소가 없습니다. 숙소를 찜하려면 하트를 클릭해주세요.</p>
       	</c:if>
	</form>
</body>
</html>