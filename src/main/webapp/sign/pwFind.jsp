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
<script type="text/javascript">
	$(()=>{
		$("#emailCk").on("click",()=>{
			let email = $("#email").val().trim();
			$.ajax({
				type: "POST",
				asyne: true,
				url : "sign/emailFind.jsp",
				data : {"email":email},
				success : function(response,status,request){
					if(response.trim() == 'true'){
						$("#ec").val('y');
						alert("이메일이 확인되었습니다.");
					}else if(response.trim() == 'false'){
						alert("이메일이 존재하지 않습니다.");
					}else {
						alert("이메일을 입력해주세요.")
					}
				}
			});
		});
	});

	function inputck(){
		let email = $("#email").val();
		
		if(email == ""){
			alert("이메일을 입력해 주세요.");
			return false;
		}else if($("#ec").val() == ""){
			alert("이메일 중복 체크를 해주세요.");
			return false;
		}
	}
</script>
</head>
<body>
	<div class="container">
		<form action="sign/pwFindSend.jsp" method="post" onsubmit="return inputck()">
			<div>
			  <label for="exampleFormControlInput1" class="form-label">가입한 이메일</label>
				 <input type="hidden" name="emailck" id="ec" value="" />				
			</div>
			<div class="input-group mb-3">
			  <input type="email" class="form-control" id="email" name="email" placeholder="name@example.com">
			  <button class="btn btn-outline-secondary" type="button" id="emailCk">이메일 확인</button>
			</div>
			<div class="d-grid gap-2">
			  <button class="btn btn-secondary" type="submit">이메일 전송</button>
			</div>
		</form>
	</div>
</body>
</html>