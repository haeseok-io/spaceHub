<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<script type="text/javascript">
	function checkPw() {
		let pw = $("#password").val().trim();
		let pwConfirm = $("#pwConfrim").val().trim();
		if(pw.length<8) {
			$("#pwCheck").text("비밀번호를 8자리 이상 입력하세요.");
			return false;
		}else {
			alert("비밀번호가 정상적으로 입력되었습니다.");
			return true;
		}
	}
</script>
</head>
<body>
<div class="container">
	<form action="/mypage" method="get">
		<input type="hidden" name="cmd" value="modifyOk" />
		<input type="hidden" name="memno" value="${vo.memno }"/>
		<table class="table">
			<tr>
				<th>이름</th>
				<td>${vo.name }</td>
			</tr>
			
			<tr>
				<th>이메일</th>
				<td>${vo.email }</td>
			</tr>
			
			<tr>
				<td>
					<a href="/spaceHub/mypage?cmd=pwAuth&memno=${vo.memno }">
						<input type="button" value="비밀번호 변경" />
					</a>
				</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td>
					<input type="password" name="pwConfirm" id="pwConfirm" value=""/>
					<button onclick="checkPw()"></button>
				</td>
			</tr>
			<tr>
				<th>우편번호</th>
				<td><input type="text" name="post" id="post" value="${vo.post }"/></td>
			</tr>
			
			<tr>
				<th>주소</th>
				<td><input type="text" name="addr" id="addr" value="${vo.addr }"/></td>
			</tr>
			
			<tr>
				<th>계좌번호</th>
				<td><input type="text" name="accountNum" id="accountNum" value="${vo.accountNum }"/></td>
			</tr>
			
			<tr>
				<td>
					<input type="submit" value="수정" />
				</td>
			</tr>			
		</table>
	</form>
</div>
</body>
</html>