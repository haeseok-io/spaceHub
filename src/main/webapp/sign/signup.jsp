<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../common/common.jsp" />

	<title>spaceHub - 회원가입</title>
	
	<style>
		.main .inner { width: 500px; padding: 100px 0; }
		
		.data-agree { margin: 30px 0; }
		.data-agree .checkbox-wrap {}
		.data-agree .checkbox-wrap input[type='checkbox'] { display: none; }
		.data-agree .checkbox-wrap label { display: flex; align-items: center; }
		.data-agree .checkbox-wrap label .checkbox-view { display: inline-block; width: 20px; height: 20px; background: #fff; border: 1px solid #ccc; border-radius: 3px; margin-right: 10px; }
		
		.data-agree .checkbox-wrap input[type='checkbox']:checked + label .checkbox-view { position: relative; background: #666; border: 1px solid #666; }
		.data-agree .checkbox-wrap input[type='checkbox']:checked + label .checkbox-view:after { position: absolute; left: 6px; top: 2px; display: block; content: ""; width: 6px; height: 10px; border: solid #fff; border-width: 0 2px 2px 0; -webkit-transform: rotate(45deg); -ms-transform: rotate(45deg); transform: rotate(45deg); }
		
		.data-agree .agree-subject { margin-bottom: 10px; font-weight: bold; font-size: 18px; color: #333; }
		.data-agree .agree-wrap {}
		.data-agree .agree-wrap .agree-list {}
		.data-agree .agree-wrap .agree-list li { display: flex; justify-content: space-between; align-items: center; height: 40px; font-size: 14px; color: #666 }
		.data-agree .agree-wrap .agree-list li button { background: #fff; border: 0; color: #888; text-decoration: underline; }
	</style>
	
	<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script>
		const regexObj = {
			email : /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
			password : /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/,
		}
	
		$(() => {
			// 이메일 중복 체크
			$("input[name='email']").on("keyup", e => {
				let _this = $(e.currentTarget);
				let email = _this.val().trim();
				
				// Check
				if( !email ) {
					printErrorMsg(_this, "", null);
					return false;
				}
				
				// - 이메일 정규식 체크
				if( !regexObj.email.test(email) ) {
					printErrorMsg(_this, "이메일 형식을 확인해주세요.", true);
					return false;
				}
				
				// Process
				$.ajax({
					type: "GET",
					url: "/spaceHub/sign",
					data: {cmd: "emailCheck", email: email},
					dataType: "json",
					success: result => {
						// 오류메시지
						if( result.errorCode ){
							printErrorMsg(_this, result.errorMsg, true);
							return false;
						}
						
						// 정상
						printErrorMsg(_this, "사용 가능한 이메일입니다.", false);
					}
				})
			});
			
			// 비밀번호 체크
			$("input[name='password']").on("keyup", e => {
				let _this = $(e.currentTarget);
				
				// Init
				passwordReCheck();
				if( !_this.val() ){
					printErrorMsg(_this, "", null);
					return false;
				}
				
				// Check
				if( !regexObj.password.test(_this.val()) ){
					printErrorMsg(_this, "최소 8자 영문+숫자를 포함하여야 합니다.", true);
					return false;
				}
				
				// Result
				printErrorMsg(_this, "사용 가능한 비밀번호 입니다.", false);
			});
			$("input[name='password_chk']").on("keyup", passwordReCheck);
			
			// 닉네임 중복 체크
			$("input[name='nickname']").on("keyup", e => {
				let _this = $(e.currentTarget);
				let nickname = _this.val();
				
				// Check
				if( !nickname ) {
					printErrorMsg(_this, "", null);
					return false;
				}
				
				// Process
				$.ajax({
					type: "GET",
					url: "/spaceHub/sign",
					data: {cmd: "nicknameCheck", nickname: nickname},
					dataType: "json",
					success: result => {
						// 오류메시지
						if( result.errorCode ){
							printErrorMsg(_this, result.errorMsg, true);
							return false;
						}
						
						// 정상
						printErrorMsg(_this, "사용 가능한 닉네임입니다.", false);
					}
				})
			});
			
			// 이용약관 전체 동의
			$("input[name='agreeAllChk']").on("change", e => {
				// Val
				let allChkEl = $(e.currentTarget);
				let allChkState = allChkEl.is(":checked");
				let chkEl = $("input[name='agree']");
				
				// Result
				chkEl.prop("checked", allChkState);
			});
			
			// 이용약관 개별 동의
			$("input[name='agree']").on("change", e => {
				// Val
				let allChkEl = $("input[name='agreeAllChk']");
				let chkLength = $("input[name='agree']").length;;
				let checkedLength = $("input[name='agree']:checked").length;
				
				// Result
				if( chkLength==checkedLength )	allChkEl.prop("checked", true);
				else 							allChkEl.prop("checked", false);
				
			});
		});
		
		// 회원가입 폼 체크
		const checkForm = () => {
			// Val
			let form = document.signupForm;
			
			// Check
			if( !form.email.value ) {
				alert("이메일을 입력해주세요.");
				form.email.focus();
				return false;
			} else if( !regexObj.email.test(form.email.value) ) {
				alert("이메일 형식을 확인해주세요.");
				form.email.focus();
				return false;
			} else if( !form.password.value ) {
				alert("비밀번호를 입력해주세요.");
				form.password.focus();
				return false;
			} else if( !regexObj.password.test(form.password.value) ) {
				alert("비밀번호는 최소 8자 영문+숫자 조합이여야 합니다.");
				form.password.focus();
				return false;
			} else if( form.password.value!=form.password_chk.value ){
				alert("비밀번호를 다시 확인해주세요.");
				form.password_chk.focus();
				return false;
			} else if( !form.name.value ) {
				alert("이름을 입력해주세요.");
				form.name.focus();
				return false;
			} else if( !form.post.value || !form.addr.value ) {
				alert("주소를 입력해주세요.");
				return false;
			}
			
			// 이용약관 체크
			let agreeElLength = $("input[name='agree']", form).length;
			let agreeChkElLength = $("input[name='agree']:checked", form).length;
			if( agreeElLength!=agreeChkElLength){
				alert("이용약관을 동의해주세요.");
				return false;
			}
		}
		
		// 우편번호 검색창 열기
		const searchAddress = () => {
			new daum.Postcode({
		    	oncomplete: callbackAddress
			}).open();
		}
		
		// 우편번호 검색 콜백
		const callbackAddress = data => {
			let form = document.signupForm;
			let postEl = $("input[name='post']", form);
			let addrEl = $("input[name='addr']", form);
			
			// Data
			postEl.val(data.zonecode);
			addrEl.val(data.roadAddress);
		
			// Result
			$("input[name='addr_detail']", form).focus();
		}
		
		// 비밀번호 재확인
		const passwordReCheck = () => {
			let passEl = $("input[name='password']");
			let repassEl = $("input[name='password_chk']");
			
			if( !repassEl.val() ){
				printErrorMsg(repassEl, "", null);
				return false;
			}
			
			if( passEl.val()!=repassEl.val() ){
				printErrorMsg(repassEl, "비밀번호가 일치하지 않습니다.", true);
				return false;
			}
			
			// Result
			printErrorMsg(repassEl, "비밀번호가 일치합니다.", false);
		}
		
		// 에러메시지 출력 함수
		const printErrorMsg = (e, m, t) => {
			let wrapEl = $(e).parents(".data-wrap");
			let errorEl = wrapEl.find(".data-msg");
			
			// Init
			wrapEl.removeClass("error").removeClass("success");
			errorEl.removeClass("error").removeClass("success");
			
			// Check
			if( t==null ) {
				errorEl.text("");
				return false;
			}
			
			// Data
			let errorType = t ? "error" : "success";
			
			// Result
			wrapEl.addClass(errorType);
			errorEl.addClass(errorType);
			errorEl.text(m);
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
							<div class="data-field">
								<input type="text" name="email" placeholder="이메일" />
							</div>
							<div class="data-msg"></div>
						</div>
						<div class="data-wrap">
							<div class="data-field">
								<input type="password" name="password" placeholder="비밀번호" />
							</div>
							<div class="data-msg"></div>
						</div>
						<div class="data-wrap">
							<div class="data-field">
								<input type="password" name="password_chk" placeholder="비밀번호 확인" />
							</div>
							<div class="data-msg"></div>
						</div>
						<div class="data-wrap">
							<div class="data-field">
								<input type="text" name="name" placeholder="이름" />
							</div>
						</div>
						<div class="data-wrap">
							<div class="data-field">
								<input type="text" name="nickname" placeholder="닉네임" />
							</div>
							<div class="data-msg"></div>
						</div>
						<div class="data-wrap button">
							<div class="data-field">
								<input type="text" name="post" placeholder="우편번호" readonly onclick="searchAddress();" />
							</div>
							<div class="data-button">
								<button type="button" class="btn btn-outline-secondary" onclick="searchAddress();">우편번호 찾기</button>
							</div>
						</div>
						<div class="data-wrap">
							<div class="data-field">
								<input type="text" name="addr" placeholder="주소" readonly onclick="searchAddress();" />
							</div>
						</div>
						<div class="data-wrap">
							<div class="data-field">
								<input type="text" name="addr_detail" placeholder="상세주소" />
							</div>
						</div>
						<div class="data-wrap">
							<div class="data-field">
								<select name="bank_name">
									<option value="">은행 선택</option>
									<option value="신한은행">신한은행</option>
									<option value="국민은행">국민은행</option>
									<option value="우리은행">우리은행</option>
									<option value="하나은행">하나은행</option>
									<option value="농협은행">농협은행</option>
									<option value="외환은행">외환은행</option>
								</select>
							</div>
						</div>
						<div class="data-wrap">
							<div class="data-field">
								<input type="text" name="account_num" placeholder="계좌번호" />
							</div>
						</div>
						
						<div class="data-agree">
							<div class="agree-subject">
								<div class="checkbox-wrap">
									<input type="checkbox" name="agreeAllChk" id="agreeAllChk" />
									<label for="agreeAllChk">
										<span class="checkbox-view"></span>
										<span>전체 동의</span>
									</label>
								</div>
							</div>
							<div class="agree-wrap">
								<ul class="agree-list">
									<li>
										<div class="checkbox-wrap">
											<input type="checkbox" name="agree" id="agree1" />
											<label for="agree1">
												<span class="checkbox-view"></span>
												<span>이용약관</span>
											</label>
										</div>
										<div>
											<button type="button" data-bs-toggle="modal" data-bs-target="#agree1Modal">보기</button>
										</div>
									</li>
									<li>
										<div class="checkbox-wrap">
											<input type="checkbox" name="agree" id="agree2" />
											<label for="agree2">
												<span class="checkbox-view"></span>
												<span>개인정보 수집 및 이용</span>
											</label>
										</div>
										<div>
											<button type="button" data-bs-toggle="modal" data-bs-target="#agree2Modal">보기</button>
										</div>
									</li>
								</ul>
							</div>
						</div>
					</div>
				
					<div class="fieldset-button">
						<button type="submit" class="btn btn-success">회원가입</button>
					</div>
				</form>
			</div>
			
		</div>
	</div>
	
	<!-- 이용약관 -->
	<div class="modal fade modal-xl" id="agree1Modal" tabindex="-1" aria-labelledby="agree1ModalLabel" aria-hidden="true">
 		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="exampleModalLabel">이용약관</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					제1조(목적) 이 약관은 업체 회사(전자상거래 사업자)가 운영하는 업체 사이버 몰(이하 “몰”이라 한다)에서 제공하는 인터넷 관련 서비스(이하 “서비스”라 한다)를 이용함에 있어 사이버 몰과 이용자의 권리․의무 및 책임사항을 규정함을 목적으로 합니다.<br>
					※「PC통신, 무선 등을 이용하는 전자상거래에 대해서도 그 성질에 반하지 않는 한 이 약관을 준용합니다.<br><br>
					제2조(정의)<br>
					① “몰”이란 업체 회사가 재화 또는 용역(이하 “재화 등”이라 함)을 이용자에게 제공하기 위하여 컴퓨터 등 정보통신설비를 이용하여 재화 등을 거래할 수 있도록 설정한 가상의 영업장을 말하며, 아울러 사이버몰을 운영하는 사업자의 의미로도 사용합니다.<br>
					② “이용자”란 “몰”에 접속하여 이 약관에 따라 “몰”이 제공하는 서비스를 받는 회원 및 비회원을 말합니다.<br>
					③ ‘회원’이라 함은 “몰”에 회원등록을 한 자로서, 계속적으로 “몰”이 제공하는 서비스를 이용할 수 있는 자를 말합니다.<br>
					④ ‘비회원’이라 함은 회원에 가입하지 않고 “몰”이 제공하는 서비스를 이용하는 자를 말합니다.
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 개인정보 수집 및 이용 -->
	<div class="modal fade modal-xl" id="agree2Modal" tabindex="-1" aria-labelledby="agree2ModalLabel" aria-hidden="true">
 		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="exampleModalLabel">개인정보처리방침</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					1. 총칙<br>
					본 업체 사이트는 회원의 개인정보보호를 소중하게 생각하고, 회원의 개인정보를 보호하기 위하여 항상 최선을 다해 노력하고 있습니다<br>
					1) 회사는 「정보통신망 이용촉진 및 정보보호 등에 관한 법률」을 비롯한 모든 개인정보보호 관련 법률규정을 준수하고 있으며, 관련 법령에 의거한 개인정보처리방침을 정하여 이용자 권익 보호에 최선을 다하고 있습니다.<br>
					2) 회사는 「개인정보처리방침」을 제정하여 이를 준수하고 있으며, 이를 인터넷사이트 및 모바일 어플리케이션에 공개하여 이용자가 언제나 용이하게 열람할 수 있도록 하고 있습니다.<br>
					3) 회사는 「개인정보처리방침」을 통하여 귀하께서 제공하시는 개인정보가 어떠한 용도와 방식으로 이용되고 있으며 개인정보보호를 위해 어떠한 조치가 취해지고 있는지 알려드립니다.<br>
					4) 회사는 「개인정보처리방침」을 홈페이지 첫 화면 하단에 공개함으로써 귀하께서 언제나 용이하게 보실 수 있도록 조치하고 있습니다.<br>
					5) 회사는  「개인정보처리방침」을 개정하는 경우 웹사이트 공지사항(또는 개별공지)을 통하여 공지할 것입니다.<br><br>
					
					2. 개인정보 수집에 대한 동의<br>
					귀하께서 본 사이트의 개인정보보호방침 또는 이용약관의 내용에 대해 「동의 한다」버튼 또는 「동의하지 않는다」버튼을 클릭할 수 있는 절차를 마련하여, 「동의 한다」버튼을 클릭하면 개인정보 수집에 대해 동의한 것으로 봅니다.<br>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>
	

</body>
</html>