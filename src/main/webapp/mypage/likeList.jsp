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
	img {
		height: 200px;
		width: 200px;
		display: inline-block; /* 이미지를 옆으로 배열하려면 인라인 블록 요소로 설정 */
        margin-right: 20px;
	}
</style>
</head>
<body>
	<form action="/mypage" method="post">
	<input type="hidden" name="cmd" value="likeList"/>
	<input type="hidden" name="memno" value="${member.memno }" />
		<h1>찜한 숙소</h1>
		<div class="container">		
			<a href="/spaceHub/space?cmd=detail&space=${member.memno }">
				<div class="wrap">
					<c:forEach var="vo" items="${likeList }">
						<c:forEach var="img" items="${vo.imgList }">
							<img src="${img.path }" alt="" />			
						</c:forEach>
						<p>${vo.subject }</p>
						<p>침대 : ${vo.bed }개</p>
						<p>${vo.inDate } ~ ${vo.outDate }</p>
						<p>$${vo.price }/박 총액$${vo.price }</p>
					</c:forEach>
				</div>
			</a>
			<%-- <c:if var="list" test="${empty list.spaceno }">
				<p>찜한 숙소가 없습니다. 숙소를 찜하려면 하트를 클릭해주세요.</p>
			</c:if> --%>
		</div>
	</form>
</body>
</html>