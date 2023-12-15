<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../common/common.jsp" />
	<style>
		.container{
			margin : auto;
			margin-top: 250px;
		}
	</style>
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
	
<jsp:include page="../common/header.jsp" />
	
	<div class="main">
		<div class="inner">
			<div class="page-title">
				<div class="title-name">비밀번호 찾기</div>
			</div>
		
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
	</div>
</body>
</html>