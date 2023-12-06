<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String pw = request.getParameter("pw");

if(pw != "") {
	if(pw.length() >= 8) {
		out.println("true");
	}else {
		out.println("false");
	}
}


%>