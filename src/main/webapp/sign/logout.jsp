<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	HttpSession hs;
	
	session.invalidate();
	
	response.sendRedirect("/spaceHub/sign?cmd=login");
%>