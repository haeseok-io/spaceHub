package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.SmemberDAO;
import com.spacehub.www.vo.SmemberVO;

public class pwAuthOkAction implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		String memno = req.getParameter("memno");
		String password = req.getParameter("password");
		String emailKey = req.getParameter("emailKey");
		String authNum = (String)session.getAttribute("authNum");
		
		
		if( !authNum.equals(emailKey) ) {
			return "/spaceHub/mypage?cmd=pwAuth&memno=" + memno; 
		}
		
		// 정보수정
		SmemberDAO dao = new SmemberDAO();
		SmemberVO vo = new SmemberVO();
		
		vo.setPassword(password);
		vo.setMemno(Integer.parseInt(memno));
		
		dao.modifyPw(vo);
		
		session.setAttribute("password", vo.getPassword());
		session.removeAttribute(authNum);
		dao.close();
		
		return "/spaceHub/mypage?cmd=modify";
	}

}
