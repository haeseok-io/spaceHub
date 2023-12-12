<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../common/common.jsp" />

	<title>spaceHub - 회원가입</title>
	
	<style>
		.main .inner { width: 500px; padding: 100px 0; }
	</style>
	
	<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
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
			
			$("#nkname").on("click",()=>{
				let nickname = $("#nickname").val().trim();
				$.ajax({
					type: "POST",
					asyne: true,
					url : "sign/nicknameCheck.jsp",
					data : {"nickname":nickname},
					success : function(response,status,request){
						if(response.trim() == 'true'){
							$("#nc").val('y');
							alert("사용 가능합니다.");
						}else if(response.trim() == 'false'){
							alert("다른 닉네임을 입력해주세요.");
						}else {
							alert("닉네임을 입력해주세요.")
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
			let nickname = $("#nickname").val();
			let postcode = $("#postcode").val();
			let address = $("#address").val();
			let detailAddress = $("#detailAddress").val();
			
			if(email == "" || pw == "" || name == "" || nickname == "" || postcode == "" || address == "" || detailAddress == ""){
				alert("필수 사항을 모두 입력해 주세요.");
				return false;
			}else if($("#ec").val() == ""){
				alert("이메일 중복 체크를 해주세요.");
				return false;
			}else if($("#pc").val() == ""){
				alert("비밀번호 확인을 해주세요.");
				return false;
			}else if($("#nc").val() == ""){
				alert("닉네임 확인을 해주세요.");
				return false;
			}
		}
		
	</script>
	
<jsp:include page="../common/header.jsp" />

	<div class="main">
		<div class="inner">
			
			<div class="page-title">
				<p class="title-name">회원가입</p>
			</div>
			
			<div class="fieldset-wrap">
				<form name="signupForm" action="/spaceHub/sign" method="post" onsubmit="return checkForm()">
					<input type="hidden" name="cmd" value="signupOk" />
				
				
					<div class="fieldset-data">
						<div class="data-wrap">
							
						</div>
					</div>
				
				</form>
			</div>
		
		
			<div>
				<h1>회원가입</h1>
				<form action="/spaceHub/sign?cmd=signupOk" method="post" onsubmit="return inputck()">
					<div>
						 <label for="exampleFormControlInput1" class="form-label">이메일 (필수)</label>
						 <input type="hidden" name="emailck" id="ec" value="" />
						 <input type="hidden" name="pkck" id="pc" value="" />
						 <input type="hidden" name="nc" id="nc" value="" />
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
					  <label for="exampleFormControlInput1" class="form-label">닉네임 (필수)</label>
				  </div>
				  <div class="input-group mb-3">
						<input type="text" id="nickname" name="nickname" class="form-control" placeholder="nickname" aria-label="nickname" aria-describedby="button-addon2">
						<button class="btn btn-outline-secondary" type="button" id="nkname">닉네임 확인</button>
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
					  <select class="form-select" aria-label="Default select example">
						  <option selected>은행 선택</option>
						  <option value="1">신한은행</option>
						  <option value="2">KB국민은행</option>
						  <option value="3">우리은행</option>
						  <option value="4">하나은행</option>
						  <option value="5">NH농협은행</option>
						  <option value="6">외환은행</option>
						</select>
					  <input type="text" class="form-control" id="accountNum" name="accountNum" placeholder="accountNum">
				  </div>
				   <div>
					    <label for="exampleFormControlInput1" class="form-label">이용약관 동의 (필수)</label>
				   </div>
				  <div class="accordion" id="accordionPanelsStayOpenExample">
					  <div class="accordion-item">
					    <h2 class="accordion-header">
					      <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="true" aria-controls="panelsStayOpen-collapseOne">
					        이용약관
					      </button>
					    </h2>
					    <div id="panelsStayOpen-collapseOne" class="accordion-collapse collapse show">
					      <div class="accordion-body">
					        제1조(목적) 이 약관은 업체 회사(전자상거래 사업자)가 운영하는 업체 사이버 몰(이하 “몰”이라 한다)에서 제공하는 인터넷 관련 서비스(이하 “서비스”라 한다)를 이용함에 있어 사이버 몰과 이용자의 권리․의무 및 책임사항을 규정함을 목적으로 합니다.
							  ※「PC통신, 무선 등을 이용하는 전자상거래에 대해서도 그 성질에 반하지 않는 한 이 약관을 준용합니다.」
							제2조(정의)
							  ① “몰”이란 업체 회사가 재화 또는 용역(이하 “재화 등”이라 함)을 이용자에게 제공하기 위하여 컴퓨터 등 정보통신설비를 이용하여 재화 등을 거래할 수 있도록 설정한 가상의 영업장을 말하며, 아울러 사이버몰을 운영하는 사업자의 의미로도 사용합니다.
							  ② “이용자”란 “몰”에 접속하여 이 약관에 따라 “몰”이 제공하는 서비스를 받는 회원 및 비회원을 말합니다.
							  ③ ‘회원’이라 함은 “몰”에 회원등록을 한 자로서, 계속적으로 “몰”이 제공하는 서비스를 이용할 수 있는 자를 말합니다.
							  ④ ‘비회원’이라 함은 회원에 가입하지 않고 “몰”이 제공하는 서비스를 이용하는 자를 말합니다.
						</div>
					    </div>
					  </div>
					  <div class="accordion-item">
					    <h2 class="accordion-header">
					      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseTwo" aria-expanded="false" aria-controls="panelsStayOpen-collapseTwo">
					        개인정보 수집 및 이용
					      </button>
					    </h2>
					    <div id="panelsStayOpen-collapseTwo" class="accordion-collapse collapse">
					      <div class="accordion-body">
					        개인정보처리방침
							1. 총칙
							
							본 업체 사이트는 회원의 개인정보보호를 소중하게 생각하고, 회원의 개인정보를 보호하기 위하여 항상 최선을 다해 노력하고 있습니다
							1) 회사는 「정보통신망 이용촉진 및 정보보호 등에 관한 법률」을 비롯한 모든 개인정보보호 관련 법률규정을 준수하고 있으며, 관련 법령에 의거한 개인정보처리방침을 정하여 이용자 권익 보호에 최선을 다하고 있습니다.
							2) 회사는 「개인정보처리방침」을 제정하여 이를 준수하고 있으며, 이를 인터넷사이트 및 모바일 어플리케이션에 공개하여 이용자가 언제나 용이하게 열람할 수 있도록 하고 있습니다.
							3) 회사는 「개인정보처리방침」을 통하여 귀하께서 제공하시는 개인정보가 어떠한 용도와 방식으로 이용되고 있으며 개인정보보호를 위해 어떠한 조치가 취해지고 있는지 알려드립니다.
							4) 회사는 「개인정보처리방침」을 홈페이지 첫 화면 하단에 공개함으로써 귀하께서 언제나 용이하게 보실 수 있도록 조치하고 있습니다.
							5) 회사는  「개인정보처리방침」을 개정하는 경우 웹사이트 공지사항(또는 개별공지)을 통하여 공지할 것입니다.
							
							2. 개인정보 수집에 대한 동의
							귀하께서 본 사이트의 개인정보보호방침 또는 이용약관의 내용에 대해 「동의 한다」버튼 또는 「동의하지 않는다」버튼을 클릭할 수 있는 절차를 마련하여, 「동의 한다」버튼을 클릭하면 개인정보 수집에 대해 동의한 것으로 봅니다.
					      </div>
					    </div>
					  </div>
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
		</div>
	</div>
	

</body>
</html>