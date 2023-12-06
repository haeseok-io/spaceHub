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
<style>
	.report-popup {
		display: none;
		position: fixed;
		top: 0;
		right: 0;
		bottom: 0;
		left: 0;
		background-color: rgba(0, 0, 0, 0.5);
		z-index: 100;
	}
	
	.report-popup.show {
		display: block;
	}
	
	.dialog {
		width: 300px;
		margin: 40px auto;
		background-color: #fff;
	}
	
	.contents {
		padding:10px 15px;
		text-align: center;
		line-height: 100px;
	}
</style>
<script>
	//팝업 열기
	$(document).on("click", ".btn-open", function (e){
		var target = $(this).attr("href");
		$(target).addClass("show");
	});
	
	// 외부영역 클릭 시 팝업 닫기
	$(document).mouseup(function (e){
		var ReportPopup = $(".report-popup");
		if(ReportPopup.has(e.target).length === 0){
			ReportPopup.removeClass("show");
		}
	});
</script>
</head>
<body>
	<form action="/space" method="get">
		<a href="#report-popup" class="btn-open" value="">신고하기</a>
		<input type="hidden" name="memno" value="${vo.memno }"/>
		<input type="hidden" name="spaceno" value="${vo.spaceno }" />
		<input type="hidden" name="spaceSubject" value="${vo.spaceSubject }" />		
		<input type="hidden" name="cmd" value="reportOk"/>
		
		<div class="container">
			<div class="report-popup show" id="report-popup">
				<div class="dialog">
					<table class="table">
						<tr>
							<th>제목</th>
							<td><input type="text" class="text" name="subject" id="subject" value="${vo.subject }" /></td>
						</tr>
						
						<tr>
							<th>내용</th>
							<td><textarea class="textarea" name="contents" id="contents" cols="30" rows="10" value="${vo.contents }"></textarea></td>
						</tr>
						
						<tr>
							<td colspan="2">
								<input type="submit" value="신고" />
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
</body>
</html>