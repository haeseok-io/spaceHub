<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../common/common.jsp"/>
	<title>공간 수정</title>
	<style>
		h2{font-weight:bold;}
		.img-thumbnail{width: 200px; height:200px; display: inline-block; margin: 5px 20px;}
	</style>
<jsp:include page="../common/header.jsp"/>
	<div class="container">
		<p></p>
		<h2>숙소 정보 수정하기</h2>
		
		<c:forEach var="vo" items="${list }">
		<img src=${vo.path } class="img-thumbnail" alt="...">
		</c:forEach>
	</div>




</body>
</html>