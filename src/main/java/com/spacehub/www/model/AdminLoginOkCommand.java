package com.spacehub.www.model;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.AdminDAO;
import com.spacehub.www.vo.AdminVO;

public class AdminLoginOkCommand implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		
		// 아이디, 비밀번호가 없을 경우 로그인페이지로 이동
		if( id==null || pw==null || id.equals("") || pw.equals("") ) {
			return "/spaceHub/admin";
		}
		
		HttpSession session = req.getSession();
		
		// 관리자에 회원이 존재하는지 체크
		AdminDAO adminDAO = new AdminDAO();
		AdminVO adminVO = adminDAO.getOne(id, pw);
		
		if( adminVO==null ) {
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
		session.setAttribute("admin", adminVO);
		return "/spaceHub/admin?cmd=reservationList";
	}

}
