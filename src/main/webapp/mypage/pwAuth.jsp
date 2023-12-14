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
$(() => {
	// 비밀번호 입력
	$("#pwCheck").on("click", () => {
		if ($("#password").val().length < 8) {
			alert("비밀번호는 8자리 이상 입력해주세요.")
		} else {
			// 전송 버튼 활성화
			$("#emailAuth").prop("disabled", false);
			alert("전송 버튼을 눌러 이메일 인증 번호를 입력해주세요.");
		}
	});

	// 이메일 인증
	$("#emailAuth").on("click", () => {
		$.ajax({
			type: "GET",
			async: true,
			url: "/spaceHub/mypage/emailAuth.jsp",
			dataType: "json",
			data: {
				pw: $("#password").val(),
				memno: "${vo.memno}",
				email: "${vo.email}",
			},
			success: function (data) {
				alert("가입하신 이메일로 인증번호가 전송되었습니다.");
			},
			error: function (xhr, status, error) {
				alert("전송 중 오류가 발생했습니다.");
			}
		})
	});

	// 인증번호 검사
	$("form").on("submit", (event) => {
		event.preventDefault(); // 폼 기본 동작 방지

		// 필수 입력 값 검증
		var emailKey = $("#emailKey").val();
		if (!emailKey) {
			alert("인증번호를 입력하세요.");
			return;
		}

		// 서버로 인증번호 확인 요청
		$.ajax({
			type: "POST",
			async: true,
			url: "/spaceHub/mypage/checkEmailAuth.jsp",
			dataType: "json",
			data: {
				emailKey: $("#emailKey").val(),
				// 기타 필요한 데이터 전송
			},
			success: function (data) {
				if (data.success) {
					alert("성공적으로 인증되었습니다.");
					// 인증 성공 시에만 폼 제출
					$("form").off("submit").submit();
				} else {
					alert("인증번호가 일치하지 않습니다. 다시 시도해주세요.");
				}
			},
			error: function (xhr, status, error) {
				alert("오류가 발생했습니다.");
			}
		});
	});
});
</script>
</head>
<body>
	<form action="/spaceHub/mypage" method="post" >
		<input type="hidden" name="cmd" value="pwAuthOk"/>
		<input type="hidden" name="memno" value="${member.memno }"/>
		<div>
			<p>변경할 비밀번호 : </p>
			<input type="password" name="password" id="password" />
			<input type="button" name="pwCheck" id="pwCheck" value="비밀번호 확인"/>
			<input type="button" name="emailAuth" id="emailAuth" value="인증번호 전송" />
		</div>
		<p>인증번호 :</p>
		<input type="text" name="emailKey" id="emailKey" />
		<input type="submit" value="확인" />
	</form>
</body>
</html>