package com.spacehub.www.model;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.SmemberDAO;
import com.spacehub.www.vo.SmemberVO;

public class PwfindOkCommand implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("pw");
		
		if(password != null) {
			SmemberDAO dao = new SmemberDAO();
			SmemberVO vo = new SmemberVO();
			vo.setPassword(password);
			vo.setEmail(email);
			
			dao.modifyPw(vo);
			dao.close();
			
		}
		return "sign/login.jsp";
	}

}
