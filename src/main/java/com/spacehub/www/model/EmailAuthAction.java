package com.spacehub.www.model;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.SmemberDAO;
import com.spacehub.www.vo.SmemberVO;

public class EmailAuthAction implements Action{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String m = req.getParameter("memno");
		
		if(m != null) {
			HttpSession session = req.getSession();
			
			SmemberVO memberData = (SmemberVO) session.getAttribute("member");

			String email = memberData.getEmail();
			System.out.println(email);
			Properties p = new Properties();
			
			p.put("mail.transport.protocol", "smtp");
			p.put("mail.smtp.host", "smtp.gmail.com");
			p.put("mail.smtp.port", "465");
			p.put("mail.smtp.auth", "true");
			
			p.put("mail.smtp.quitwait", "false");
			p.put("mail.smtp.socketFactory.port", "false");
			p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			p.put("mail.smtp.socketFactory.fallback", "false");
			
			String num = "" + (int)(Math.random()*100000);
			
			// 보낼 메세지
			StringBuffer sb = new StringBuffer();
			sb.append("<h3>인증번호</h3>");
			sb.append("<h3>" + num + "</h3>");
			
			session.setAttribute("authNum", num);
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
		
		return null;
	}
	
}
