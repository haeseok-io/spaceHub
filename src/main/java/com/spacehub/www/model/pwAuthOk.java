package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.SmemberDAO;
import com.spacehub.www.vo.SmemberVO;

public class pwAuthOk implements Action{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String m = req.getParameter("memno");
		
		if(m != null) {
			int memno = Integer.parseInt(m);
			
			SmemberDAO dao = new SmemberDAO();
			
			SmemberVO vo = dao.getOne(memno);
			
			req.setAttribute("vo", vo);
		}
		
		return null;
	}

}
