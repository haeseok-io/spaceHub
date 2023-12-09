<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../common/common.jsp"/>
	<title>공간 수정</title>
	<style>
		h2{font-weight:bold;}
		.img-thumbnail{width: 200px; height:200px; display: inline-block; margin: 5px 20px;}
		#stopStatus, #normalStatus{width: 200px; margin: 5px 20px;}
		.deleteBtnDiv {padding: 0 21px; text-align: right;}
		.plusImgContainer{margin: 5px 20px; text-align: right;}
	</style>
	
	<script>
		$(()=>{
			var vstatus = "${spaceVo.VStatus}"
			console.log("vstatus:" + vstatus);
			
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
	        });
	        
	        // 이미지 삭제
	        $(".deleteBtn").on("click", function() {
	        	 var container = $(this).closest('.imageContainer'); // 삭제할 이미지 컨테이너
	        	 container.remove(); // 이미지 컨테이너 삭제
	        	 $.ajax({
	        		url:"/mypage/host",
	        		data:{cmd:imgDeleteOk, spaceno: spaceno},
	        		dataType: "json",
	        		success: data =>{
	        			
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
		<form action="/mypage/host" method="post" enctype="multipart/form-data">
			<input type="hidden" name="cmd" value="spaceModifyOk" />
			<p></p>
			<div>
				<h2>숙소 정보 수정하기</h2>
				<button type="button" class="btn btn-success" id="normalStatus" value="1">상태 : 운영중</button>
				<button type="button" class="btn btn-danger" id="stopStatus" value="2" style="display:none;">상태 : 운영중지</button>
			</div>
			<c:forEach var="vo" items="${list }">
			<div class="imageContainer" style="display: inline-block;">
				<img src=${vo.path } class="img-thumbnail" alt="...">
				 <div class="deleteBtnDiv">
                    <button type="button" class="btn btn-outline-danger deleteBtn">삭제</button>
                </div>
			</div>
			</c:forEach>
				<button type="button" class="btn btn-outline-primary plusImgContainer">사진추가</button>
		</form>
	</div><!--container end -->
</body>
</html>