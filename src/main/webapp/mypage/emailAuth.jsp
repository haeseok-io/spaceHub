<%@page import="org.json.simple.JSONObject"%>
<%@page import="javax.mail.Transport"%>
<%@page import="javax.mail.internet.InternetAddress"%>
<%@page import="javax.mail.internet.MimeMessage"%>
<%@page import="javax.mail.Message"%>
<%@page import="javax.mail.PasswordAuthentication"%>
<%@page import="javax.mail.Authenticator"%>
<%@page import="javax.mail.Session"%>
<%@page import="java.util.Properties"%>
<%@page import="com.spacehub.www.vo.SmemberVO"%>
<%@page import="com.spacehub.www.dao.SmemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String m = request.getParameter("memno");
	String pw = request.getParameter("password");
	out.print(m);
	if(m != null) {
			
		int memno = Integer.parseInt(m);
	
		SmemberDAO dao = new SmemberDAO();
		SmemberVO vo = dao.getOne(memno);
		
		String email = vo.getEmail();
		Properties p = new Properties();
		
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.transport.protocol", "smtp");
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.auth", "true");
		p.put("mail.stmp.port", "465");
		
		p.put("mail.smtp.quitwait", "false");
		p.put("mail.smtp.socKetFactory.port", "465");
		p.put("mail.smtp.socKetFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback", "false");
		
		String num = "" + (int)(Math.random()*100000);
		
		session.setAttribute("authNum", num);
		
		// 보낼 메세지
		StringBuffer sb = new StringBuffer();
		sb.append("<h3>인증번호</h3>");
		sb.append("<h3>" + num + "</h3>");
		
		// 보내는 사람
		String username = "gkfnglfn@gmail.com";
		String password = "xegw urzj xmoc guri";
		// 받는 사람
		String receiver = email;
		
		Session ss = Session.getInstance(p, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		// 메일제목
		String title = "spaceHub 비밀번호 인증번호입니다.";
		
		try{
			Message message = new MimeMessage(ss);
			// 보내는 사람
			message.setFrom(new InternetAddress(username, "고객지원센터", "utf-8"));
			// 받는 사람
			message.addRecipient(Message.RecipientType.TO , new InternetAddress(receiver));
			// 제목
			message.setSubject(title);
			// 내용
			message.setContent(sb.toString(), "text/html;charset=UTF-8");
			Transport.send(message);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
%>