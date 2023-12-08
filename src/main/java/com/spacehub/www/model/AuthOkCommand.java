package com.spacehub.www.model;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.SmemberDAO;
import com.spacehub.www.vo.SmemberVO;

public class AuthOkCommand implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		String email = req.getParameter("email");
		String n = req.getParameter("num");
		
		if(n != null){
			Object obj = session.getAttribute("num");
			if(obj != null){
				String key = (String)obj;
				if(key.equals(n)){
					req.setAttribute("email", email);
				}else{
					resp.setContentType("text/html; charset=utf-8");
					PrintWriter writer;
					
					try {
						writer = resp.getWriter();
						writer.println("<script type='text/javascript'>");
						writer.println("alert('인증번호가 다릅니다.'); history.go(-1); ");
						writer.println("</script>");
						
						writer.flush();
						writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
	}
		return "sign/pwModify.jsp";
		
}
}
