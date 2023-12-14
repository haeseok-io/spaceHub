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
	img {
		height: 200px;
		width: 200px;
		display: inline-block; /* 이미지를 옆으로 배열하려면 인라인 블록 요소로 설정 */
        margin-right: 20px;
	}
	p {
		color : black;
	}
    .wrap {
        position: relative;
        width: 100%; /* 슬라이드 전체 너비 */
    }

    .slider {
        position: relative;
        max-width: 200px; /* 슬라이더의 최대 너비를 조절 */
    }

    .slider img {
        width: 200px; /* 이미지가 슬라이더 안에 꽉 차도록 설정 */
        height: 200px; /* 가로 비율에 따라 세로 비율 자동 조절 */
        border-radius: 8px; /* 이미지에 둥근 테두리 적용 */
    }
    span {
    	font-weight: bold; font-size: 18px;
    }
    .content {
    	display: inline-block;
    	
    }
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
				<c:forEach var="vo" items="${jjimList }">
					<div class="content">
						<div class="wrap slider">
							<c:forEach var="img" items="${vo.imgList }">
								<a href="/spaceHub/space?cmd=detail&spaceno=${vo.spaceno }">
									<img src="${img.path }" alt="" />			
								</a>
							</c:forEach>
						</div>
						<div><span class="subject">${vo.subject }</span></div>
						<div>${vo.addr }</div>
						<div>₩ <span class="price">${vo.price }</span> / 1박</div>
					</div>
				</c:forEach>
			</div>
			<c:if test="${empty jjimList}">
        		    <p>찜한 숙소가 없습니다. 숙소를 찜하려면 하트를 클릭해주세요.</p>
        	</c:if>
	</form>
</body>
</html>