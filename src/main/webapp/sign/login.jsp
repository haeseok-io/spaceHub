<%@page import="com.spacehub.www.vo.SmemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/common.jsp" />
	
	<title>spaceHub - 로그인</title>

	<style>
		.main .inner { width: 500px; padding: 100px 0; }
		.fieldset-wrap .login-find { display: flex; justify-content: center; margin-top: 10px; }
		.fieldset-wrap .login-find ul { display: flex; justify-content: space-between; width: 40%; }
		.fieldset-wrap .login-find ul li { width: 50%; text-align: center; }
		.fieldset-wrap .login-find ul li a { font-size: 14px; color: #999; }
	</style>
	<script>
		const checkForm = () => {
			// Val
			let form = document.loginForm;
			
			let regexObj = {
				email : /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
			}
			
			// Check
			if( !form.email.value ){
				alert("이메일을 입력해주세요.");
				form.email.focus();
				return false;
			} else if( !regexObj.email.test(form.email.value) ){
				alert("이메일 형식이 올바르지 않습니다.");
				form.email.focus();
				return false;
			} else if( !form.pw.value ){
				alert("비밀번호를 입력해주세요.");
				form.pw.focus();
				return false;
			}
			
		}
	</script>
<jsp:include page="../common/header.jsp" />
	<%
		Object obj = session.getAttribute("vo");
	
		if(obj != null){
			SmemberVO vo = (SmemberVO)obj;
			response.sendRedirect("/spaceHub/home");
			
		} else{
	%>
	
	<div class="main">
		<div class="inner">
			
			<div class="page-title">
				<p class="title-name">로그인</p>
			</div>
			
			<div class="fieldset-wrap">
				<form name="loginForm" action="/spaceHub/sign" method="post" onsubmit="return checkForm()">
					<input type="hidden" name="cmd" value="loginOk" />
					
					<div class="fieldset-data">
						<div class="data-wrap">
							<input type="text" name="email" placeholder="이메일을 입력해주세요." />
						</div>
						<div class="data-wrap">
							<input type="password" name="pw" placeholder="비밀번호를 입력해주세요." />
						</div>
					</div>
					
					<div class="fieldset-button">
						<button class="btn btn-success" type="submit">로그인</button>
					</div>
					<div class="login-find">
						<ul>
							<li><a href="/spaceHub/sign?cmd=pwFind">비밀번호 찾기</a></li>
							<li><a href="/spaceHub/sign?cmd=signup">회원가입</a></li>
						</ul>
					</div>
				</form>
			</div>
			
		</div>
	</div>
	
	<%} %>
</body>
</html>