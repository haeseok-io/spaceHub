<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="com.spacehub.www.vo.SmemberVO"%>
<%@page import="com.spacehub.www.dao.SmemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String email = request.getParameter("email");
	
	String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
	Pattern p = Pattern.compile(regex);
	Matcher m = p.matcher(email);
	
	if(email != "") {
		SmemberDAO dao = new SmemberDAO();
		SmemberVO vo = dao.getOne(email);
		
		if(m.matches()){
			if(vo == null) {
				out.println("true");
			}else if(vo != null) {
				out.println("false");
			}
		}else{
			out.println("err");
		}
	}

%>