<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../common/common.jsp" />
<title>Insert title here</title>

<jsp:include page="../common/header.jsp" />

	<div class="main">
		<div class="inner">
			<div class="message-list">
				<div class="message-title">
					<p class="title-name">메시지</p>
				</div>
				<div class="list-wrap">
					<ul class="my-list">
						<li>
							
						</li>
					</ul>
				</div>
			</div>
			<div class="message-content">
				<div class="message-title">
					<p class="title-name">내용</p>
				</div>
			</div>
		</div>
		
			${sessionScope.member}
			<h2>message.jsp</h2>
	</div>

</body>
</html>