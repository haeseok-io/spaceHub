<%@page import="com.spacehub.www.dao.SmemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	SmemberDAO dao = new SmemberDAO();
	dao.deleteOne(16);
%>
</body>
</html>