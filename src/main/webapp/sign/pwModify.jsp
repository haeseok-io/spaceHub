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
	$(()=>{
		$("#pwCk").on("click",()=>{
			let pw = $("#pw").val().trim();
			$.ajax({
				type: "POST",
				asyne: true,
				url : "sign/pwCheck.jsp",
				data : {"pw":pw},
				success : function(response,status,request){
					if(response.trim() == 'true'){
						$("#pc").val('y');
						alert("사용 가능합니다.");
					}else if(response.trim() == 'false'){
						alert("8자리 이상 입력해주세요.");
					}else {
						alert("비밀번호를 입력해주세요.")
					}
				}
			});
			
		});
	});
	
	function inputck(){
		let pw = $("#pw").val();
		
		if(pw == ""){
			alert("비밀번호를 입력해 주세요.");
			return false;
		}else if($("#pc").val() == ""){
			alert("비밀번호 확인을 해주세요.");
			return false;
		}
	}
</script>
</head>
<body>
	<div class="container">
		<form action="/spaceHub/sign?cmd=pwModifyOk" method="post" onsubmit="return inputck()">
			<h2>비밀번호 변경</h2>
			<div class="mb-3 row">
			    <label for="staticEmail" class="col-sm-2 col-form-label">이메일</label>
			    <div class="col-sm-10">
			      <input type="text" readonly class="form-control-plaintext" name="email" id="staticEmail" value="${email}">
			    </div>
			</div>
			<div>
				 <label for="exampleFormControlInput1" class="form-label">비밀번호</label>
				 <input type="hidden" name="pkck" id="pc" value="" />
			</div>
			<div class="input-group mb-3">
				<input type="password" id="pw" name="pw" class="form-control" placeholder="password" aria-label="password" aria-describedby="button-addon2">
				<button class="btn btn-outline-secondary" type="button" id="pwCk">비밀번호 확인</button>
			</div>
			<div id="passwordHelpBlock" class="form-text">
			  	비밀번호는 8자리 이상입니다.
			</div>
			<div class="d-grid gap-2 col-6 mx-auto">
			  <button class="btn btn-outline-secondary" type="submit" id="signupbtn">변경하기</button>
		  </div>
		</form>
	</div>
</body>
</html>