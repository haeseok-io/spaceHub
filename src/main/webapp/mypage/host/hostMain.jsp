<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호스트 마이페이지</title>
<jsp:include page="/common/common.jsp" />
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
	.account a.account_update {
		text-decoration: underline;
	}
	.account {
		border: 3px solid gray;
		border-radius: 10px;
		width: 250px;
	}
</style>

<jsp:include page="/common/header.jsp" />

	<div class="container">
		<form action="" method="post">
			<div class="greet">
				<h2>${member.name }님 안녕하세요</h2>
				<h4>숙소 등록 후 24시간이 지나면 게스트가 예약을 할 수 있습니다! 예약을 받을 수 있도록 설정을 마쳐보세요!</h4>
			</div>
			<div>
				<div class="account">
					<div>계정 정보를 제출하셔야 합니다!</div>
					<div>대금 수령을 위한 정보가 필요합니다.</div>
					<a class="account_update" href="/spaceHub/mypage?cmd=modify&memno=${member.memno }">계정 정보 업데이트</a>
				</div>
			</div>
			<div>
				<h2>등록한 숙소</h2>
				<c:forEach var="vo" items="${hostList }">
						<div class="spaceInfo">
							<div class="img_wrap">
								<c:forEach var="img" items="${vo.imgList }">
									<a href="/spaceHub/mypage/host?cmd=reservCalender">
										<img src="${img.path }" alt="" />
									</a>
								</c:forEach> 
							</div>
							<p class="space_subject">${vo.subject }</p>
							<button type="button" class="btn btn-secondary"><a href="/spaceHub/mypage/host?cmd=spaceModify&memno=${member.memno }&spaceno=${vo.spaceno }">공간 수정</a></button>
							<button type="button" class="btn btn-secondary"><a href="/spaceHub/space?cmd=sapceDelete&memno=${member.memno }&spaceno=${vo.spaceno }">공간 삭제</a></button>
						</div>
				 </c:forEach>
			</div>
		</form>
	</div>
</body>
</html>