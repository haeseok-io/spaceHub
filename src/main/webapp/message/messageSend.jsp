<%@page import="com.spacehub.www.vo.ReservMessageVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.spacehub.www.dao.MessageDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script type="text/javascript">
	function sendMsg(){
		let mes = $("#mes").val();
		let name = $("#name").val();
		$("#uli").append("<li>"+name+" : "+mes+"</li>"+"\n");
		
	}
</script>
<style type="text/css">
.chat {
		padding-bottom:60px;
	}
	.chat ul {
		width:100%;
		list-style:none;
	}
	.input-group{
		position:absolute;
		bottom:0;
		width:80%;
	}
</style>
</head>
<body>
	<div class="container">
		<h2>메세지</h2>
		<form action="/spaceHub/message?cmd=messageOk" method="post" onsubmit="sendMsg()">
			<div class="mb-3 row">
		<c:forEach var="vo" items="${list}">
			    <label for="staticEmail" class="col-sm-2 col-form-label">호스트 :</label>
			    <div class="col-sm-10">
			      <input type="text" readonly class="form-control-plaintext" id="staticEmail" value="${vo.name}">
			       <input type="hidden" name="cmd" value="messageOk" />
			       <input type="hidden" name="spaceno" value="${vo.spaceno}" />
			  		<input type="hidden" name="reservno" value="${vo.reservno}" />
			    </div>
		</c:forEach>
			</div>
			<div class="chat">
			  <ul id="uli">
		<c:forEach var="vo" items="${mlist}">
			  	<li>${vo.name} : ${vo.contents}</li>
		</c:forEach>
			  </ul>
			</div>
			<c:forEach var="vo" items="${slist}">
			<input type="hidden" id="name" value="${vo.name}" />
			</c:forEach>
			<div class="input-group mb-3">
			  <input type="text" id="mes" name="mes" class="form-control" placeholder="호스트에게 보낼 메세지를 입력해주세요" aria-label="Recipient's username" aria-describedby="button-addon2">
			  <input class="btn btn-outline-secondary" type="submit" id="btn" value="전송">
			</div>
		</form>
	</div>
</body>
</html>