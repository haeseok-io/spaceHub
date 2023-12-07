package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.ReservationDAO;
import com.spacehub.www.vo.SmemberVO;

public class SpaceListCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		ReservationDAO dao = new ReservationDAO();
		HttpSession session = req.getSession();
		SmemberVO memberVO = (SmemberVO)session.getAttribute("member");
		
		
		
		dao.close();
//		req.setAttribute("list", list);
		
		return "guest/space.jsp";
	}

}
