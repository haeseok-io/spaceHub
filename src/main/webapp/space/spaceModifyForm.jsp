<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../common/common.jsp"/>
	<title>공간 수정</title>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/air-datepicker/2.1.0/css/datepicker.min.css">
	<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=5e9f8f00127fcd38f89ea645b5f3327f&libraries=services,clusterer,drawing"></script>
	<style>
		h2{font-weight:bold;}
		input[type='radio']{margin: 5px 20px;}
		.img-thumbnail{width: 200px; height:200px; display: inline-block; margin: 5px 20px;}
		#stopStatus, #normalStatus{width: 200px; margin: 5px 20px;}
		.deleteBtnDiv {padding: 0 21px; text-align: right;}
		.plusImgContainer{margin: 5px 20px; text-align: right;}
		.map_wrap {position:relative;width:100%;height:500px;}
	    .title {font-weight:bold;display:block;}
	    .hAddr {position:absolute;left:10px;top:10px;border-radius: 2px;background:#fff;background:rgba(255,255,255,0.8);z-index:1;padding:5px;}
	    #centerAddr {display:block;margin-top:2px;font-weight: normal;}
	    .bAddr {padding:5px;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
	</style>
	
	<script>
		$(()=>{
			var vstatus = "${spaceVo.VStatus}"
			console.log("vstatus:" + vstatus);
			
/* 			console.log('${spaceDetailVo.type}')
			console.log('${dcList[0]}');
			console.log('${dcList[1]}');
 */			
			//radio
			$("input[name='detailType'][value='${spaceDetailVo.type}']").prop("checked", true);
			//checkbox
			$("input[type='checkbox'][value='1']").prop("checked", true);
			
			<%-- dcList를 자바스크립트 배열로 변환 --%>
			var dcList = [
			    <c:forEach var="item" items="${dcList}">
			        {
			            dcratio: "${item.dcratio}"
			        }<c:if test="${not loop.last}">,</c:if>
			    </c:forEach>
			];

			<%-- 각 dcratio 값을 갖는 input 요소를 찾아 checked 속성 설정 --%>
			dcList.forEach(function(dcItem) {
			    $("input[name='dcRatio'][value='" + dcItem.dcratio + "']").prop("checked", true);
			});

			
			// 페이지 로딩 시 초기 상태에 따라 버튼 보이기/숨기기 설정
	        if (vstatus == "1") {
	            $("#normalStatus").show();
	            $("#stopStatus").hide();
	        } else {
	            $("#normalStatus").hide();
	            $("#stopStatus").show();
	        }
			
	        // 버튼 토글
	        $("#normalStatus, #stopStatus").on("click", () => {
	            $("#normalStatus, #stopStatus").toggle();
	            if ($("#normalStatus").is(":visible")) {
	                vstatus = "1"; // VStatus 값을 1로 설정
	                console.log("vstatus:" + vstatus);
	            } else {
	                vstatus = "2"; // VStatus 값을 2로 설정
	                console.log("vstatus:" + vstatus);
	            }
		        document.spaceModifyForm.vstatus.value = vstatus;
	        });
	        
	        
		    var spaceno = "${spaceVo.spaceno}";
		    // 이미지 삭제
		    $(".deleteBtn").on("click", function() {
		        var container = $(this).closest('.imageContainer'); // 삭제할 이미지 컨테이너
		        container.remove(); // 이미지 컨테이너 삭제
		        
		        var imgno = container.data('imgno'); // 데이터 속성에서 imgno 값을 가져옴
		        console.log("이미지 번호:", imgno);
		        
		        // Ajax 요청
		        $.ajax({
		            url: "/spaceHub/mypage/host",
		            data: { cmd: 'imgDeleteOk', spaceno: spaceno, imgno: imgno },
		            dataType: "json",
		            success: function(data) {
		                if (data.errorCode) {
		                    alert(data.errorMsg);
		                    if (data.errorCode === "member empty") {
		                        document.location.href = "/spaceHub/sign?cmd=login";
		                    }
		                    return false;
		                }
		            },
		            error: function(xhr, status, error) {
		                console.error("오류 발생", error);
		            }
		        });
		    });

	        // 이미지 컨테이너
	        $(".plusImgContainer").on("click", function() {
	            var imageContainers = $('.imageContainer').length; // 이미지 컨테이너의 개수 확인

	            if (imageContainers < 10) { // 이미지 컨테이너 개수가 10개 미만인 경우에만 새 이미지 추가 가능
	                var newImageContainer = "<div class='imageContainer' style='display: inline-block; vertical-align: top;'>" +
	                    "<img src='' class='img-thumbnail' alt='...'>" +
	                    "<div class='deleteBtnDiv'>" +
	                    "<button type='button' class='btn btn-outline-danger deleteBtn'>삭제</button>" +
	                    "</div>" +
	                    "<div class='plusBtnDiv'>" +
	                    "<input type='file' class='fileUpload' style='display: none;' accept='image/*'>" +
	                    "</div>" +
	                    "</div>";

	                $(this).before(newImageContainer);

	                // 새로 생성된 이미지 컨테이너의 삭제 버튼에 대한 이벤트 핸들러 연결
	                $(this).prev('.imageContainer').find('.deleteBtn').on('click', function() {
	                    $(this).closest('.imageContainer').remove(); // 해당 이미지 컨테이너 삭제
	                    var remainingContainers = $('.imageContainer').length;
	                    if (remainingContainers < 10) { // 남은 이미지 컨테이너가 10개 미만인 경우
	                        $(".plusImgContainer").show(); // 사진 추가 버튼 보이기
	                    }
	                });

	                // 새로 생성된 이미지 컨테이너의 파일 업로드 버튼에 대한 기능 추가
	                $(this).prev('.imageContainer').find('.fileUpload').on('change', function() {
	                    var fileInput = this;
	                    var img = $(this).closest('.imageContainer').find('img');
	                    if (fileInput.files && fileInput.files[0]) {
	                        var reader = new FileReader();
	                        reader.onload = function(e) {
	                            img.attr('src', e.target.result);
	                        };
	                        reader.readAsDataURL(fileInput.files[0]);
	                    }
	                }).click(); // 파일 업로드 창을 띄우기 위해 click 이벤트 호출
	            }

	            // 이미지 컨테이너가 10개인 경우 사진 추가 버튼 숨김 처리
	            if ($('.imageContainer').length >= 10) {
	                $(this).hide();
	            }
	        });

	        
	    });//$ end
	</script>
	
<jsp:include page="../common/header.jsp"/>
	<div class="container">
		<form name="spaceModifyForm" action="/spaceHub/mypage/hostControl" method="post" enctype="multipart/form-data" >
			<input type="hidden" name="cmd" value="spaceModifyOk" />
			<input type="hidden" name="memno" value="${member.memno }"/>
			<input type="hidden" name="spaceno" value="${spaceVo.spaceno }"/>
			<input type="hidden" name="vstatus" value="${spaceVo.VStatus}"/>
			<input type="hidden" name="y" /> <!-- 위도  -->
			<input type="hidden" name="x" /> <!-- 경도  -->
			<p></p>
			<div>
				<h2>숙소 정보 수정하기</h2>
				<button type="button" class="btn btn-success" id="normalStatus" value="1">상태 : 운영중</button>
				<button type="button" class="btn btn-danger" id="stopStatus" value="2" style="display:none;">상태 : 운영중지</button>
			</div>
			<div class="text-left" >
				<div class="form-check form-check-inline">
					<img src="../upload/house.PNG" class="img-thumbnail" alt="..."><br>
					<input type="radio" name="detailType" id="house" value="공간전체">
			  		<label for="house">공간전체</label>
				</div>
				<div class="form-check form-check-inline">
					<img src="../upload/door.PNG" class="img-thumbnail" alt="..."><br>
					<input type="radio" name="detailType" id="room" value="방">
			  		<label for="room">방</label>
				</div>
				<div class="form-check form-check-inline">
					<img src="" class="img-thumbnail" alt="..."><br>
					<input type="radio" name="detailType" id="multiRoom" value="도미토리" >
			  		<label for="multiRoom">도미토리</label>
				</div>
			</div>
			<c:forEach var="vo" items="${list }">
			<div class="imageContainer" style="display: inline-block;" data-imgno="${vo.imgno}">
				<img src=${vo.path } class="img-thumbnail" alt="...">
				 <div class="deleteBtnDiv">
                    <button type="button" class="btn btn-outline-danger deleteBtn">삭제</button>
                </div>
			</div>
			</c:forEach> 
				<button type="button" class="btn btn-outline-primary plusImgContainer">사진추가</button>
		 
			<%-- <div class="input-group mb-3">
			  <label class="input-group-text" for="inputGroupFile01">공간이미지 업로드</label>
			  <c:forEach var="vo" items="${list }">
				  <input name="img5" type="file" class="form-control" id="inputGroupFile01" value="${vo.path }">
				  <input name="img4" type="file" class="form-control" id="inputGroupFile02" value="${vo.path }">
				  <input name="img3" type="file" class="form-control" id="inputGroupFile03" value="${vo.path }">
				  <input name="img2" type="file" class="form-control" id="inputGroupFile04" value="${vo.path }">
				  <input name="img1" type="file" class="form-control" id="inputGroupFile05" value="${vo.path }">
			  </c:forEach>
			</div>  --%>
			<div class="p-3 text-primary-emphasis --bs-info-border-subtle border border-primary-subtle rounded-3">
			  다음 중 숙소를 가장 잘 설명하는 것은 무엇인가요?
			</div>
			<div class="mb-3">
				<select name="type" class="form-select" required aria-label="select example">
				  <option value="건물 유형 선택" ${spaceVo.type eq '건물 유형 선택' ? 'selected' : ''}>건물 유형 선택</option>
   				  <option value="아파트" ${spaceVo.type eq '아파트' ? 'selected' : ''}>아파트</option>
				  <option value="한옥" ${spaceVo.type eq '한옥' ? 'selected' : ''}>한옥</option>
				  <option value="해변 바로 앞" ${spaceVo.type eq '해변 바로 앞' ? 'selected' : ''}>해변 바로 앞</option>
				  <option value="민박" ${spaceVo.type eq '민박' ? 'selected' : ''}>민박</option>
				  <option value="게스트하우스" ${spaceVo.type eq '게스트하우스' ? 'selected' : ''}>게스트하우스</option>
				  <option value="최고의 전망" ${spaceVo.type eq '최고의 전망' ? 'selected' : ''}>최고의 전망</option>
				  <option value="디자인" ${spaceVo.type eq '디자인' ? 'selected' : ''}>디자인</option>
				  <option value="캠핑카" ${spaceVo.type eq '캠핑카' ? 'selected' : ''}>캠핑카</option>
				  <option value="통나무집" ${spaceVo.type eq '통나무집' ? 'selected' : ''}>통나무집</option>
				  <option value="컨테이너하우스" ${spaceVo.type eq '컨테이너하우스' ? 'selected' : ''}>컨테이너하우스</option>
				 </select>
			<div class="invalid-feedback">옵션을 선택해주세요.</div>
			</div>
			<div class="p-3 text-primary-emphasis --bs-info-border-subtle border border-primary-subtle rounded-3">
			  숙소위치는 어디인가요?
			</div>
			   <div class="input-group mb-3">
				  <input type="text" class="form-control" id="postcode" name="post" placeholder="우편번호" aria-label="우편번호" aria-describedby="button-addon2" value="${spaceVo.post }">
				  <button class="btn btn-outline-secondary" type="button" id="button-addon2" onclick="DaumPostcode()">우편번호 찾기</button>
			   </div>
			   <div>
				    <input type="text" class="form-control" id="address" name="address" placeholder="주소" value="${spaceVo.addr }">
				    <input type="text" class="form-control" id="detailAddress" name="detailAddress" placeholder="상세주소">
				    <input type="text" class="form-control" id="extraAddress" name="extraAddress" placeholder="참고항목">
			   </div> <br />
					<p style="margin-top:-12px">
			    <em class="link">
			        <a href="javascript:void(0);" onclick="window.open('http://fiy.daum.net/fiy/map/CsGeneral.daum', '_blank', 'width=981, height=650')">
			        </a>
			    </em>
			</p>
			<div id="map" style="width:100%;height:350px;"></div>
			<script>
				let m='';
				  var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
				    mapOption = {
				        center: new kakao.maps.LatLng("${spaceDetailVo.y}", "${spaceDetailVo.x}"), // 지도의 중심좌표
				        level: 3 // 지도의 확대 레벨
				    };  
				
				// 지도를 생성합니다    
				var map = new kakao.maps.Map(mapContainer, mapOption); 
				
				
				//우편번호 검색 후 주소값을 지도에 핀으로 나타냄
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
					        m = addr;
				     // 주소-좌표 변환 객체를 생성합니다
						var geocoder = new kakao.maps.services.Geocoder();
						//console.log("주소:" +m);
					        
					// 주소로 좌표를 검색합니다
					geocoder.addressSearch(m, function(result, status) {
				
				    // 정상적으로 검색이 완료됐으면 
				     if (status === kakao.maps.services.Status.OK) {
				
				        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
				        
				        //위도,경도 값 가져오기
						var y = coords.getLat();
						document.spaceModifyForm.y.value = y;
						var x = coords.getLng();
						document.spaceModifyForm.x.value = x;
						
				        // 결과값으로 받은 위치를 마커로 표시합니다
				        var marker = new kakao.maps.Marker({
				            map: map,
				            position: coords
				        });
				
				        // 인포윈도우로 장소에 대한 설명을 표시합니다
				        var infowindow = new kakao.maps.InfoWindow({
				            content: '<div style="width:150px;text-align:center;padding:6px 0;">주소 입력값</div>'
				        });
				        infowindow.open(map, marker);
				
				        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
				        map.setCenter(coords);
				        
					    } 
					}); 
				}
			}).open();
		}
		</script> <br />
			<div class="p-3 text-primary-emphasis --bs-info-border-subtle border border-primary-subtle rounded-3">
			 숙소 기본 정보
			</div> <br />
			최대인원 <input type="number" name="maxGuest" id="" step="1" value="${spaceDetailVo.maxGuest }"/> 
			침대 <input type="number" name="bed" id="" step="1" value="${spaceDetailVo.bed }"/> 
			욕실 <input type="number" name="bathroom" id="" step="1" value="${spaceDetailVo.bathroom }"/> 
			<br /> <br />
			<div class="p-3 text-primary-emphasis --bs-info-border-subtle border border-primary-subtle rounded-3">
			 숙소 예약가능일
			</div> <br />
			<div>
			<input size="30" type="text"  data-range="true" data-multiple-dates-separator=" ~ "
			 name="inOutDate" id="datepicker" value="${spaceDetailVo.inDate } ~ ${spaceDetailVo.outDate }" /> 
			</div> <br />
			<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
		    <script src="https://cdnjs.cloudflare.com/ajax/libs/air-datepicker/2.1.0/js/datepicker.min.js"></script>
		    <script>
			    document.addEventListener('DOMContentLoaded', function () {
			    // air-datepicker를 input 요소에 연결
			    $('#datepicker').datepicker({
			        language: {
			            daysMin: ['일', '월', '화', '수', '목', '금', '토'],
			            months: [
			                '1월', '2월', '3월', '4월', '5월', '6월',
			                '7월', '8월', '9월', '10월', '11월', '12월'
			            ],
			            today: '오늘',
			            clear: '지우기',
			            dateFormat: 'yyyy-mm-dd',
			            firstDay: 0
			        },
			        minDate: new Date(),  // 최소 선택 가능한 날짜를 현재 날짜로 설정
			        dateFormat: 'yyyy-mm-dd',  // 날짜 형식 설정 (년-월-일)
					onSelect: function (formattedDate, date, picker) {
						if (date) {
							$('#selected-date').text('선택한 날짜: ' + formattedDate);
			
							var dayOfWeek = date.getDay();
							var days = ['일', '월', '화', '수', '목', '금', '토'];
							$('#day-of-week').text('요일: ' + days[dayOfWeek]);
						} else {
							$('#selected-date').text('');
							$('#day-of-week').text('');
						}
					}
			    });
			});
	
	    </script>
			<div class="p-3 text-primary-emphasis --bs-info-border-subtle border border-primary-subtle rounded-3">
			숙소 편의시설 정보 추가
			</div>
			<div class="form-check form-check-inline">
			  <input name="wifi" class="form-check-input" type="checkbox" id="inlineCheckbox1" value="${spaceFacVo.wifi }">
			  <label class="form-check-label" for="inlineCheckbox1">WIFI</label>
			</div>
			<div class="form-check form-check-inline">
			  <input name="tv" class="form-check-input" type="checkbox" id="inlineCheckbox2" value="${spaceFacVo.tv }">
			  <label class="form-check-label" for="inlineCheckbox2">TV</label>
			</div>
			<div class="form-check form-check-inline">
			  <input name="kitchen" class="form-check-input" type="checkbox" id="inlineCheckbox3" value="${spaceFacVo.kitchen }">
			  <label class="form-check-label" for="inlineCheckbox2">주방</label>
			</div>
			<div class="form-check form-check-inline">
			  <input name="wm" class="form-check-input" type="checkbox" id="inlineCheckbox4" value="${spaceFacVo.wm }">
			  <label class="form-check-label" for="inlineCheckbox2">세탁기</label>
			</div>
			<div class="form-check form-check-inline">
			  <input name="aircon" class="form-check-input" type="checkbox" id="inlineCheckbox5" value="${spaceFacVo.aircon }">
			  <label class="form-check-label" for="inlineCheckbox2">에어컨</label>
			</div>
			<div class="form-check form-check-inline">
			  <input name="canpark" class="form-check-input" type="checkbox" id="inlineCheckbox6" value="${spaceFacVo.canpark }">
			  <label class="form-check-label" for="inlineCheckbox2">주차가능</label>
			</div>
			<div class="form-check form-check-inline">
			  <input name="canfreepark" class="form-check-input" type="checkbox" id="inlineCheckbox7" value="${spaceFacVo.canfreepark }">
			  <label class="form-check-label" for="inlineCheckbox2">무료주차</label>
			</div>
			<div class="form-check form-check-inline">
			  <input name="swim" class="form-check-input" type="checkbox" id="inlineCheckbox8" value="${spaceFacVo.swim }">
			  <label class="form-check-label" for="inlineCheckbox2">수영장</label>
			</div>
			<div class="form-check form-check-inline">
			  <input name="bbq" class="form-check-input" type="checkbox" id="inlineCheckbox9" value="${spaceFacVo.bbq }">
			  <label class="form-check-label" for="inlineCheckbox2">바비큐</label>
			</div>
			<div class="form-check form-check-inline">
			  <input name="pooltable" class="form-check-input" type="checkbox" id="inlineCheckbox10" value="${spaceFacVo.pooltable }">
			  <label class="form-check-label" for="inlineCheckbox2">당구대</label>
			</div>
			<div class="form-check form-check-inline">
			  <input name="fireplace" class="form-check-input" type="checkbox" id="inlineCheckbox11" value="${spaceFacVo.fireplace }">
			  <label class="form-check-label" for="inlineCheckbox2">벽난로</label>
			</div>
			<div class="form-check form-check-inline">
			  <input name="firealarm" class="form-check-input" type="checkbox" id="inlineCheckbox12" value="${spaceFacVo.firealarm }">
			  <label class="form-check-label" for="inlineCheckbox2">화재경보기</label>
			</div>
			<div class="form-check form-check-inline">
			  <input name="aidkit" class="form-check-input" type="checkbox" id="inlineCheckbox13" value="${spaceFacVo.aidkit }">
			  <label class="form-check-label" for="inlineCheckbox2">구급상자</label>
			</div>
			<div class="form-check form-check-inline">
			  <input name="firearm" class="form-check-input" type="checkbox" id="inlineCheckbox14" value="${spaceFacVo.firearm }">
			  <label class="form-check-label" for="inlineCheckbox2">소화기</label>
			</div>
			<br /> <br />
			<div class="input-group mb-3">
			  <span class="input-group-text" id="basic-addon1">숙소이름</span>
			  <input type="text" value="${spaceVo.subject }" name="subject" class="form-control" placeholder="숙소이름을 정해주세요." aria-label="Username" aria-describedby="basic-addon1">
			</div>
			<div class="form-floating">
			  <textarea name="detail" class="form-control" placeholder="숙소 설명을 작성해주세요." id="floatingTextarea2" style="height: 100px" >
			  	${spaceDetailVo.detail }
			  </textarea>
			  <label for="floatingTextarea2">숙소 설명을 작성해주세요.</label>
			</div> <br />
			<div class="input-group">
			  <input type="text" value="${spaceVo.price }" name="price" placeholder="1박당 요금을 입력해주세요. "class="form-control" aria-label="Dollar amount (with dot and two decimal places)">
			  <span class="input-group-text">￦</span>
			</div> <br />
			<div class="p-3 text-primary-emphasis --bs-info-border-subtle border border-primary-subtle rounded-3">
				할인 추가
			</div>
			<div class="form-check">
				<input type="hidden" name="disName" value="신규 숙소 프로모션 (첫 3건의 예약에 20% 할인 제공)" />
				<input name="dcRatio" class="form-check-input" type="checkbox" value="20" id="flexCheckDefault">
				<label  class="form-check-label" for="flexCheckDefault">
				 신규 숙소 프로모션 (첫 3건의 예약에 20% 할인 제공)
				</label>
			</div>
			<div class="form-check">
				<input type="hidden" name="disName" value="주간 할인 (7박 이상의 숙박에 적용되는 할인)" />
			  	<input name="dcRatio" class="form-check-input" type="checkbox" value="10" id="flexCheckDefault">
			  	<label class="form-check-label" for="flexCheckDefault">
   			   주간 할인 (7박 이상의 숙박에 적용되는 할인)
			  	</label>
			</div>
			<div class="form-check">
				<input type="hidden" name="disName" value="월간 할인 (28박 이상의 숙박에 적용되는 할인)" />
			  <input name="dcRatio" class="form-check-input" type="checkbox" value="18" id="flexCheckDefault">
			  <label class="form-check-label" for="flexCheckDefault">
			   월간 할인 (28박 이상의 숙박에 적용되는 할인)
			  </label>
			</div>
			<div class="d-grid gap-2 d-md-flex justify-content-md-end">
			  <input type="submit" class="btn btn-warning" value="수정"/>
			</div>
		</form>
	</div><!--container end -->
</body>
</html>