<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script>

function DaumPostcode() {
    new daum.Postcode({
    	oncomplete: function(data) {     
		var addr = ''; 
		var extraAddr = ''; 
		
		if (data.userSelectedType === 'R') {
            addr = data.roadAddress;
        } else {
            addr = data.jibunAddress;
        }

		if(data.userSelectedType === 'R'){
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraAddr += data.bname;
            }
            if(data.buildingName !== '' && data.apartment === 'Y'){
                extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            if(extraAddr !== ''){
                extraAddr = ' (' + extraAddr + ')';
            }
            document.getElementById("extraAddress").value = extraAddr;
        
	        } else {
	            document.getElementById("extraAddress").value = '';
	        }
	        document.getElementById('postcode').value = data.zonecode;
	        document.getElementById("address").value = addr;
	        document.getElementById("detailAddress").focus();
	    }
	}).open();
}

	$(()=>{
		$("#emailCk").on("click",()=>{
			let email = $("#email").val().trim();
			$.ajax({
				type: "POST",
				asyne: true,
				url : "sign/emailCheck.jsp",
				data : {"email":email},
				success : function(response,status,request){
					if(response.trim() == 'true'){
						$("#ec").val('y');
						alert("사용가능합니다.");
					}else if(response.trim() == 'false'){
						alert("이메일이 이미 존재합니다.");
					}else if(response.trim() == 'err'){
						alert("이메일을 형식을 확인해주세요.")
					}else {
						alert("이메일을 입력해주세요.")
					}
				}
			});
		});
		
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
		
		
		$(".form-check-input").on("click", ()=>{
			let check = $(".form-check-input:checked").val();
			
			if(check == "option1"){
				$("#signupbtn").attr("disabled", true);
			}else if(check == "option2"){
				$("#signupbtn").attr("disabled", false);
			}
			
		});
		
	});
	
	function inputck(){
		let email = $("#email").val();
		let pw = $("#pw").val();
		let name = $("#name").val();
		let postcode = $("#postcode").val();
		let address = $("#address").val();
		let detailAddress = $("#detailAddress").val();
		
		if(email == "" || pw == "" || name == "" || postcode == "" || address == "" || detailAddress == ""){
			alert("필수 사항을 모두 입력해 주세요.");
			return false;
		}else if($("#ec").val() == ""){
			alert("이메일 중복 체크를 해주세요.");
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
		<h1>회원가입</h1>
		<form action="/spaceHub/sign?cmd=signupOk" method="post" onsubmit="return inputck()">
			<div>
				 <label for="exampleFormControlInput1" class="form-label">이메일 (필수)</label>
				 <input type="hidden" name="cmd" value="signupOk" />
				 <input type="hidden" name="emailck" id="ec" value="" />
				 <input type="hidden" name="pkck" id="pc" value="" />
			</div>
			<div class="input-group mb-3">
				<input type="text" id="email" name="email" class="form-control" placeholder="email" aria-label="email" aria-describedby="button-addon2">
				<button class="btn btn-outline-secondary" type="button" id="emailCk">이메일 확인</button>
			</div>
			<div>
				 <label for="exampleFormControlInput1" class="form-label">비밀번호 (필수)</label>
			</div>
			<div class="input-group mb-3">
				<input type="password" id="pw" name="pw" class="form-control" placeholder="password" aria-label="password" aria-describedby="button-addon2">
				<button class="btn btn-outline-secondary" type="button" id="pwCk">비밀번호 확인</button>
			</div>
		  <div id="passwordHelpBlock" class="form-text">
		  	비밀번호는 8자리 이상입니다.
		  </div>
		  <br />
		  <div class="mb-3">
			  <label for="exampleFormControlInput1" class="form-label">이름 (필수)</label>
			  <input type="text" class="form-control" id="name" name="name" placeholder="name">
		  </div>
		  <div>
			    <label for="exampleFormControlInput1" class="form-label">주소 (필수)</label>
		   </div>
		   <div class="input-group mb-3">
			  <input type="text" class="form-control" id="postcode" name="post" placeholder="우편번호" aria-label="우편번호" aria-describedby="button-addon2">
			  <button class="btn btn-outline-secondary" type="button" id="button-addon2" onclick="DaumPostcode()">우편번호 찾기</button>
		   </div>
		   <div>
			    <input type="text" class="form-control" id="address" name="address" placeholder="주소">
			    <input type="text" class="form-control" id="detailAddress" name="detailAddress" placeholder="상세주소">
			    <input type="text" class="form-control" id="extraAddress" name="extraAddress" placeholder="참고항목">
		   </div>
		   <br />   
		  <div class="mb-3">
			  <label for="exampleFormControlInput1" class="form-label">계좌번호 (선택)</label>
			  <input type="text" class="form-control" id="accountNum" name="accountNum" placeholder="accountNum">
		  </div>
		   <div>
			    <label for="exampleFormControlInput1" class="form-label">이용약관 동의 (필수)</label>
		   </div>
		  <div id="passwordHelpBlock" class="form-text">
		  	약관 내용 어쩌구저쩌구
		  </div>
		  <div class="form-check form-check-inline">
			  <input class="form-check-input" type="radio" name="agree" id="disagree" value="option1" checked>
			  <label class="form-check-label" for="inlineRadio1">비동의</label>
		  </div>
			<div class="form-check form-check-inline">
			  <input class="form-check-input" type="radio" name="agree" id="agree" value="option2">
			  <label class="form-check-label" for="inlineRadio2">동의</label>
		  </div>
		  <br />
		  <div class="d-grid gap-2 col-6 mx-auto">
			  <button class="btn btn-outline-secondary" type="submit" id="signupbtn" disabled>회원가입</button>
		  </div>
		  
		</form>
	</div>

</body>
</html>