<%@page import="javax.mail.Authenticator"%>
<%@page import="javax.mail.Session"%>
<%@page import="javax.mail.Transport"%>
<%@page import="javax.mail.Message"%>
<%@page import="javax.mail.internet.InternetAddress"%>
<%@page import="javax.mail.internet.MimeMessage"%>
<%@page import="javax.mail.PasswordAuthentication"%>
<%@page import="java.util.Properties"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>spaceHub - 인증번호 입력</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</head>
<body>
	<%
		Properties p = new Properties();
		
		//구글 설정
		p.put("mail.transport.protocol", "smtp");
	    p.put("mail.smtp.host", "smtp.gmail.com");
	    p.put("mail.smtp.port", "465");
	    p.put("mail.smtp.auth", "true");
	      
	    p.put("mail.smtp.quitwait", "false");
	    p.put("mail.smtp.socketFactory.port", "465");
	    p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	    p.put("mail.smtp.socketFactory.fallback", "false");
		
		String num = ""+(int)(Math.random()*100000);
		
		//보낼 메세지
		StringBuffer sb = new StringBuffer();
		sb.append("<h3> 안녕하세요 </h3>");
		sb.append("<h3> spaceHub 입니다. </h3>");
		sb.append("<h3> 승인번호는 "+num+"입니다.</h3>");
		session.setAttribute("num", num);
		out.println("<h3> 사용자에게 보낸 승인 번호 : "+num+"</h3>");
		
		String username = "wodms1102@gmail.com"; //실제 본인 이메일 비번
		String password = "gacb pide pjbw akpc";
		
		//받는 사람 이메일
		String email = request.getParameter("email");
		String receiver = email;
		
		Session ss = Session.getInstance(p, new Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(username, password);
			}
		});
		
		//메일 제목
		String title = "spaceHub 인증번호입니다.";
		
		Message message = new MimeMessage(ss);
		//보내는 사람
		message.setFrom(new InternetAddress(username, "고객지원센터", "utf-8"));
		//받는사람
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
		//제목
		message.setSubject(title);
		//내용
		message.setContent(sb.toString(), "text/html;charset=UTF-8");
		Transport.send(message);
		
	%>
	
	<form action="/spaceHub/sign?cmd=authOk" method="post">
			<div class="mb-3">
			  <label for="exampleFormControlInput1" class="form-label">인증번호입력</label>
			  <input type="text" class="form-control" name="num" id="num" placeholder="전송받은 인증번호">
			  <input type="hidden" name="email" value="<%=email%>" />
			</div>
			<div class="d-grid gap-2">
			  <button class="btn btn-secondary" type="submit">비밀번호 변경</button>
			</div>
	</form>
</body>
</html>