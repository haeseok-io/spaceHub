<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>spaceHub - 리뷰 쓰기</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</head>
<body>
	<div class="container">
		<h1>후기</h1>
		<c:forEach var="vo" items="${list}">
		<form action="/spaceHub/review" method="post" >
			<div class="mb-3 row">
			    <label for="staticEmail" class="col-sm-2 col-form-label">호스트 :</label>
			    <div class="col-sm-10">
			      <input type="text" readonly class="form-control-plaintext" id="staticEmail" value="${vo.name}">
			      <input type="hidden" name="cmd" value="reviewOk" />
			      <input type="hidden" name="reservno" value="${vo.reservno}" />
			    </div>
			</div>
			<div class="input-group mb-3">
			  <span class="input-group-text" id="basic-addon1">제목</span>
			  <input type="text" name="title" class="form-control" placeholder="후기 제목" aria-label="Username" aria-describedby="basic-addon1">
			</div>
			<div class="form-floating">
			  <textarea class="form-control" name="contents" placeholder="메세지를 보내주세요." id="floatingTextarea2" style="height: 600px"></textarea>
			</div>
			<br />
			<div>
				<h5>평점</h5>
			</div>
			<div class="form-check form-check-inline">
			  <input class="form-check-input" type="radio" name="rating" id="rating1" value="1">
			  <label class="form-check-label" for="inlineRadio1">1</label>
			</div>
			<div class="form-check form-check-inline">
			  <input class="form-check-input" type="radio" name="rating" id="rating2" value="2">
			  <label class="form-check-label" for="inlineRadio2">2</label>
			</div>
			<div class="form-check form-check-inline">
			  <input class="form-check-input" type="radio" name="rating" id="rating3" value="3">
			  <label class="form-check-label" for="inlineRadio3">3</label>
			</div>
			<div class="form-check form-check-inline">
			  <input class="form-check-input" type="radio" name="rating" id="rating4" value="4">
			  <label class="form-check-label" for="inlineRadio3">4</label>
			</div>
			<div class="form-check form-check-inline">
			  <input class="form-check-input" type="radio" name="rating" id="rating5" value="5">
			  <label class="form-check-label" for="inlineRadio3">5</label>
			</div>
			<br />
			<br />
			<div class="d-grid gap-2">
			  <input type="submit" value="전송" class="btn btn-outline-secondary"/>
			</div>
		</form>
		</c:forEach>
	</div>
</body>
</html>