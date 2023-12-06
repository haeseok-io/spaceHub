package com.spacehub.www.model;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.SmemberDAO;
import com.spacehub.www.vo.SmemberVO;

public class LoginOkCommand implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String email = req.getParameter("email");
		String pw = req.getParameter("pw");
		
		// 이메일, 비밀번호가 없을 경우 로그인페이지로 이동
		if( email==null || pw==null || email.equals("") || pw.equals("") ) {
			return "/spaceHub/sign?cmd=login";
		}
		
		HttpSession session = req.getSession();
		
		// 회원정보에 회원이 존재하는지 체크
		SmemberDAO memberDAO = new SmemberDAO();
		SmemberVO memberVO = memberDAO.getOne(email, pw);
		
		if( memberVO==null ) {
			resp.setContentType("text/html; charset=utf-8");
			PrintWriter writer;
			
			try {
				writer = resp.getWriter();
				writer.println("<script type='text/javascript'>");
				writer.println("alert('아이디나 비밀번호가 다릅니다.'); history.go(-1); ");
				writer.println("</script>");
				
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// 회원정보가 있다면 세션에 담기
		session.setAttribute("member", memberVO);
		
		// Result
		return "/spaceHub/home";
	}
}
