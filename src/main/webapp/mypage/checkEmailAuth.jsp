<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.JSONObject" %>

<%
	//클라이언트로부터 전송된 인증번호
	String clientEmailKey = request.getParameter("emailKey");
	
	//실제로 저장된 인증번호
	String storedEmailKey = (String) session.getAttribute("authNum");
	
	//결과를 담을 JSON 객체 생성
	JSONObject jsonResult = new JSONObject();
	
	//Null 체크 및 인증번호 확인 로직
	if (clientEmailKey != null && storedEmailKey != null) {
		if (clientEmailKey.equals(storedEmailKey)) {
		     jsonResult.put("success", true);
		} else {
		     jsonResult.put("success", false);
		}
	} else {
	 // 적절한 예외 처리 또는 오류 상태를 설정
		jsonResult.put("error", "Invalid emailKey or authNum");
	}
	
	//JSON 객체를 문자열로 변환하여 응답
	response.setContentType("application/json");
	response.getWriter().write(jsonResult.toJSONString());
%>