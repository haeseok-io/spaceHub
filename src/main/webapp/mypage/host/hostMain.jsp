<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<style>
	img{
		width: 200px;
		height: 200px;
		display: inline-block; /* 이미지를 옆으로 배열하려면 인라인 블록 요소로 설정 */
        margin-right: 20px;
	}
	a {
		color: black;
	}
	.spaceInfo p.space_subject {
	    text-decoration: none; /* P태그의 밑줄 삭제 */
	}
</style>
</head>
<body>
	<div class="container">
		<form action="" method="get">
			<div class="greet">
				<h1>${vo.name }님 안녕하세요</h1>
				<h2>숙소 등록 후 24시간이 지나면 게스트가 예약을 할 수 있습니다! 예약을 받을 수 있도록 설정을 마쳐보세요!</h2>
			</div>
			<div>
				<div class="account">
					<div>계정 정보를 제출하셔야 합니다!</div>
					<div>대금 수령을 위한 정보가 필요합니다.</div>
					<a href="/spaceHub/mypage?cmd=modify&memno=${vo.memno }">계정 정보 업데이트</a>
				</div>
			</div>
			<div>
				<h2>등록한 숙소</h2>
				<c:forEach var="vo" items="${list }">
						<div class="spaceInfo">
							<a href="/spaceHub/mypage/host?cmd=reservCalender">
							<div class="img_wrap">
								<c:forEach var="img" items="${vo.imgList }">
									<img src="${img.path }" alt="" />
								</c:forEach> 
							</div>
								<p class="space_subject">${vo.subject }</p>
							</a>
							<a href="/spaceHub/space?cmd=spaceModify&memno=${vo.memno }&spaceno=${vo.spaceno }">공간 수정</a>
							<a href="/spaceHub/space?cmd=sapceDelete&memno=${vo.memno }&spaceno=${vo.spaceno }">공간 삭제</a>
						</div>
				 </c:forEach>
			</div>
		</form>
	</div>
</body>
</html>