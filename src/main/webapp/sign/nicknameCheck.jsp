<%@page import="com.spacehub.www.vo.SmemberVO"%>
<%@page import="com.spacehub.www.dao.SmemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String nickname = request.getParameter("nickname");

if(nickname != "") {
	SmemberDAO dao = new SmemberDAO();
	SmemberVO vo = dao.getNick(nickname);
	
		if(vo == null) {
			out.println("true");
		}else if(vo != null) {
			out.println("false");
		}
}


%>