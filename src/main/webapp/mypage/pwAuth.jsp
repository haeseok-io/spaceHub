<%@page import="javax.mail.internet.MimeMessage"%>
<%@page import="javax.mail.Transport"%>
<%@page import="javax.mail.internet.InternetAddress"%>
<%@page import="javax.mail.Message"%>
<%@page import="javax.mail.PasswordAuthentication"%>
<%@page import="javax.mail.Authenticator"%>
<%@page import="javax.mail.Session"%>
<%@page import="com.spacehub.www.dao.SmemberDAO"%>
<%@page import="java.util.Properties"%>
<%@page import="com.spacehub.www.vo.SmemberVO"%>
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
<script>
	// 비밀번호 입력
	$(() => {
		$("#pwCheck").on("click", () => {
			if($("#password").val().length < 8) {
				alert("비밀번호는 8자리 이상 입력해주세요.")
			}else {
				alert("전송 버튼을 눌러 이메일 인증 번호를 입력해주세요.")
			}
		})
	})
	
	$(() => {
		$("#emailAuth").on("click", () => {
			$.ajax({
				type : "GET",
				async : true,
				url : "/spaceHub/mypage/emailAuth.jsp",
				dataType : "json",
				data : { pw : $("#password").val(),
						 memno : "${vo.memno }",
						 email : "${vo.email }"},
				successs : function(data) {
					alert("인증번호가 전송되었습니다.");
				}
			})
		})
	})

</script>
</head>
<body>
	<form action="/spaceHub/mypage" method="get">
		<input type="hidden" name="cmd" value="pwAuthOk"/>
		<input type="hidden" name="memno" value="${vo.memno }"/>
		<div>
			<p>변경할 비밀번호 : </p>
			<input type="password" name="password" id="password" />
			<input type="button" name="pwCheck" id="pwCheck" value="확인"/>
			<input type="button" name="emailAuth" id="emailAuth" value="전송" />
		</div>
		<p>인증번호 :</p>
		<input type="text" name="emailKey" id="emailKey" />
		<input type="submit" value="확인" />
	</form>
</body>
</html>