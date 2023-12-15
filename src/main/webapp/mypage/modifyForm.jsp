<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<jsp:include page="/common/common.jsp" />

<script>
    window.onload = function () {
        <% if (request.getAttribute("showAlert") != null && (boolean) request.getAttribute("showAlert")) { %>
            alert("${alertMessage}");
            window.location.replace("/spaceHub/mypage");
        <% } %>
    }
</script>

<jsp:include page="/common/header.jsp" />

	<div class="main">
		<div class="inner">
			<div class="page-title">
				<div class="title-name">회원정보 수정</div>
			</div>
		
			<form action="/spaceHub/mypage?cmd=modifyOk" method="post">
				<input type="hidden" name="cmd" value="modifyOk" />
				<input type="hidden" name="memno" value="${member.memno }"/>
				<table class="table">
					<tr>
						<th>이름</th>
						<td>${member.name }</td>
					</tr>
					
					<tr>
						<th>이메일</th>
						<td>${member.email }</td>
					</tr>
					
					<tr>
						<td>
							<a href="/spaceHub/mypage?cmd=pwAuth&memno=${member.memno }">
								<input type="button" value="비밀번호 변경" />
							</a>
						</td>
					</tr>
					<tr>
						<th>우편번호</th>
						<td><input type="text" name="post" id="post" value="${member.post }"/></td>
					</tr>
					
					<tr>
						<th>주소</th>
						<td><input type="text" name="addr" id="addr" value="${member.addr }"/></td>
					</tr>
					
					<tr>
						<th>계좌번호</th>
						<td><input type="text" name="accountNum" id="accountNum" value="${member.accountNum }"/></td>
					</tr>
					
					<tr>
						<td>
							<input type="submit" value="수정" />
						</td>
					</tr>
			</table>
			</form>	
		</div>
	</div>
</body>
</html>