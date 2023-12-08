<%@page import="com.spacehub.www.vo.SmemberVO"%>
<%@page import="com.spacehub.www.dao.SmemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String email = request.getParameter("email");
	
	if(email != "") {
		SmemberDAO dao = new SmemberDAO();
		SmemberVO vo = dao.getOne(email);
		
			if(vo == null) {
				out.println("false");
			}else if(vo != null) {
				out.println("true");
			}
	}

%>