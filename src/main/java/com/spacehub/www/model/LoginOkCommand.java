package com.spacehub.www.model;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.SmemberDAO;
import com.spacehub.www.vo.SmemberVO;

public class LoginOkCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		String email = req.getParameter("email");
		String pw = req.getParameter("pw");
		
		SmemberDAO dao = new SmemberDAO();
		SmemberVO vo = dao.getOne(email, pw);
		
		HttpSession session = req.getSession();
		
		if(vo == null) {
			resp.setContentType("text/html; charset=utf-8");
			PrintWriter writer;
			try {
				writer = resp.getWriter();
				writer.println("<script type='text/javascript'>");
				writer.println("alert('아이디나 비밀번호가 다릅니다.'); history.go(-1) ");
				writer.println("</script>");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			session.setAttribute("vo",vo);
		}
		return "/sign/login.jsp";
	}
}