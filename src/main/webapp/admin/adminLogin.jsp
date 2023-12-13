<%@page import="com.spacehub.www.vo.AdminVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/common.jsp" />
	
	<title>spaceHub - 관리자 로그인</title>
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
			
			// Check
			if( !form.id.value ){
				alert("아이디를 입력해주세요.");
				form.id.focus();
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
		Object obj = session.getAttribute("admin");
	
		if(obj != null){
			AdminVO vo = (AdminVO)obj;
			%>
			<div>
				<a href="/spaceHub/admin?cmd=logoutOk" class="link-danger link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">관리자 로그아웃</a>
			</div>
			<%
			
		} else{
	%>
	
	<div class="main">
		<div class="inner">
			
			<div class="page-title">
				<p class="title-name">관리자 로그인</p>
			</div>
			
			<div class="fieldset-wrap">
				<form name="loginForm" action="/spaceHub/admin" method="post" onsubmit="return checkForm()">
					<input type="hidden" name="cmd" value="loginOk" />
					
					<div class="fieldset-data">
						<div class="data-wrap">
							<input type="text" name="id" placeholder="아이디를 입력해주세요." />
						</div>
						<div class="data-wrap">
							<input type="password" name="pw" placeholder="비밀번호를 입력해주세요." />
						</div>
					</div>
					
					<div class="fieldset-button">
						<button class="btn btn-success" type="submit">로그인</button>
					</div>
				</form>
			</div>
			
		</div>
	</div>
	
	<%} %>
</body>
</html>