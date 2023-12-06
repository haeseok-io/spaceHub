<%@page import="com.spacehub.www.vo.SmemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<style>
	hr{
		color:gray;
	}
	a{
		text-decoration-line:none;
	}
	p{
		text-align:center;
	}
</style>
<script type="text/javascript">
	function inputck(){
		let email = $("#email").val();
		let pw = $("#pw").val();
		
		if(email == ""){
			alert("이메일을 입력해주세요.");
			return false;
		}else if(pw == ""){
			alert("비밀번호를 입력해주세요.");
			return false;
		}
	}
</script>
</head>
<body>
	<%
		Object obj = session.getAttribute("vo");
	
		if(obj != null){
			SmemberVO vo = (SmemberVO)obj;
			response.sendRedirect("/spaceHub/home");
			
		} else{
	%>
	<div class="container">
		<form action="/spaceHub/sign?cmd=loginOk" method="post" onsubmit="return inputck()">
			<h1>로그인</h1>
			<div class="mb-3">
				  <label for="exampleFormControlInput1" class="form-label">이메일</label>
				  <input type="text" class="form-control" id="email" name="email" placeholder="name@example.com">
				  <input type="hidden" name="cmd" value="loginOk" />
			</div>
			<div>
				<label for="inputPassword5" class="form-label">비밀번호</label>
				<input type="password" id="pw" name="pw" class="form-control" aria-describedby="passwordHelpBlock">
			</div>
			<br />
			<div class="d-grid gap-2">
			  <button class="btn btn-outline-secondary" type="submit">로그인</button>
			</div>
			<br />
			<p>
				<a href="#" class="link-secondary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">비밀번호 찾기</a>
				<a href="/spaceHub/sign?cmd=signup" class="link-secondary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">회원가입</a>
			</p>
		<hr />
			<div class="d-grid gap-2">
			  <button class="btn btn-outline-secondary" type="button">네이버 로그인</button>
			</div>
			<br />
			<div class="d-grid gap-2">
			  <button class="btn btn-outline-secondary" type="button">구글 로그인</button>
			</div>
			<br />
			<div class="d-grid gap-2">
			  <button class="btn btn-outline-secondary" type="button">카카오 로그인</button>
			</div>
			<br />
			<div class="d-grid gap-2">
			  <button class="btn btn-outline-secondary" type="button">애플 로그인</button>
			</div>
		</form>
	</div>
	<%} %>
</body>
</html>