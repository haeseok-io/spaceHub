<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/common/common.jsp" />
<style>
	img {
		height: 200px;
		width: 200px;
		display: inline-block; /* 이미지를 옆으로 배열하려면 인라인 블록 요소로 설정 */
        margin-right: 20px;
	}
</style>

<jsp:include page="/common/header.jsp" />

	<form action="/mypage" method="post">
		<input type="hidden" name="cmd" value="likeList"/>
		<input type="hidden" name="memno" value="${member.memno }" />
		<div class="container">		
		<h1>찜한 숙소</h1>
			<c:forEach var="vo" items="${jjimList }">
				<a href="/spaceHub/space?cmd=detail&spaceno=${vo.spaceno }">
					<div class="wrap">
						<c:forEach var="img" items="${vo.imgList }">
							<img src="${img.path }" alt="" />			
						</c:forEach>
						<p>${vo.subject }</p>
						<p>침대 : ${vo.bed }개</p>
						<p>${vo.inDate } ~ ${vo.outDate }</p>
						<p>$${vo.price }/박	</p>
						<p> 총액$${vo.price }</p>
					</div>
				</a>
			</c:forEach>
			<c:if test="${empty jjimList}">
        		    <p>찜한 숙소가 없습니다. 숙소를 찜하려면 하트를 클릭해주세요.</p>
        	</c:if>
		</div>
	</form>
</body>
</html>