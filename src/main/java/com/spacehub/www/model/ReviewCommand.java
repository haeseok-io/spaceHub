package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.ReservationDAO;
import com.spacehub.www.vo.SpaceHostVO;

public class ReviewCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String r = req.getParameter("reservno");
		if(r != null) {
			int reservno = Integer.parseInt(r);
			ReservationDAO dao = new ReservationDAO();
			ArrayList<SpaceHostVO> list = dao.getHone(reservno);
			req.setAttribute("list", list);		
			dao.close();
		}
		
		return "/review/review.jsp";
	}

}
