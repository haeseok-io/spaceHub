<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호스트 마이페이지</title>
<jsp:include page="/common/common.jsp" />
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css"/>
<style>
	img{
		width: 200px;
		height: 200px;
		display: inline-block; /* 이미지를 옆으로 배열하려면 인라인 블록 요소로 설정 */
        margin-right: 20px;
	}
	a {
		color: black;
		text-decoration: none;
	}
	.spaceInfo p.space_subject {
	    text-decoration: none; /* P태그의 밑줄 삭제 */
	}
	.account {
		border: 3px solid gray;
		border-radius: 10px;
		width: fit-content; /* 텍스트 크기에 맞게 조절될 수 있도록 수정 */
        padding: 10px; /* 텍스트와 테두리 사이의 간격 조절 */
	}
	.account a.account_update {
		text-decoration: underline;
	}
	.empty_account, .empty_hostSpace {
		border: 3px solid gray;
		border-radius: 10px;
		width: fit-content; /* 텍스트 크기에 맞게 조절될 수 있도록 수정 */
        padding: 10px; /* 텍스트와 테두리 사이의 간격 조절 */
	}
	.empty_account a.account_update {
		text-decoration: underline;
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
    .img_wrap {
    	position: relative;
        width: 100%; /* 슬라이드 전체 너비 */
    }
    .hostSpace_update {
    	text-decoration: underline;
    }
    
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
	
    function confirmSpaceDeletion(memno, spaceno) {
        var confirmation = confirm("정말로 이 공간을 삭제하시겠습니까?");
        if (confirmation) {
            window.location.href = "/spaceHub/mypage/host?cmd=sapceDelete&memno="+memno+"&spaceno="+spaceno+"";
        } else {
            // 사용자가 취소를 선택한 경우
            // 추가적인 작업을 할 수 있으면 여기에 추가합니다.
        }
    }

</script>
<jsp:include page="/common/header.jsp" />

	<div class="container">
		<form action="" method="post">
			<div class="greet">
				<h2>${member.name }님 안녕하세요</h2>
				<h4>숙소 등록 후 24시간이 지나면 게스트가 예약을 할 수 있습니다! 예약을 받을 수 있도록 설정을 마쳐보세요!</h4>
			</div>
			<div>
			<c:if test="${empty member.accountNum}">
				<div class="empty_account">
					<div>계정 정보를 제출하셔야 합니다!</div>
					<div>대금 수령을 위한 정보가 필요합니다.</div>
					<a class="account_update" href="/spaceHub/mypage?cmd=modify&memno=${member.memno}">계정 정보 업데이트</a>
				</div>
			</c:if>
			<c:if test="${not empty member.accountNum}">
				<div class="account">
					<a class="account_update" href="/spaceHub/mypage?cmd=modify&memno=${member.memno}">계좌 번호 확인</a>
				</div>
			</c:if>
		</div>
			<div>
				<h2>등록한 숙소</h2>
				<c:if test="${empty hostList }">
					<div class="empty_hostSpace">
						<div>등록한 공간이 없습니다!</div>
						<a class="hostSpace_update" href="/spaceHub/space?cmd=write&memno=${member.memno }">공간 등록하러 가기</a>
					</div>
				</c:if>
				<c:if test="${not empty hostList }">
				<c:forEach var="vo" items="${hostList }">
						<div class="spaceInfo">
							<div class="img_wrap slider">
								<c:forEach var="img" items="${vo.imgList }">
										<img src="${img.path }" alt="" />
									</a>
								</c:forEach> 
							</div>
							<p class="space_subject">${vo.subject }</p>
							<button type="button" class="btn btn-primary"><a href="/spaceHub/mypage/host?cmd=reservCalender">예약자 확인</a></button>
							<button type="button" class="btn btn-warning"><a href="/spaceHub/mypage/host?cmd=spaceModify&memno=${member.memno }&spaceno=${vo.spaceno }">공간 수정</a></button>
							<button type="button" class="btn btn-danger" onclick="confirmSpaceDeletion(${member.memno}, ${vo.spaceno})">공간 삭제</button>
						</div>
				</c:forEach>
				</c:if>
			</div>
		</form>
	</div>
</body>
</html>