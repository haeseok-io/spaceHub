package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.ReservationDAO;
import com.spacehub.www.vo.ReservationVO;

public class ReservCalenderCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String s = req.getParameter("spaceno");
		
		if(s != null) {
			int spaceno = Integer.parseInt(s);
			ReservationDAO dao = new ReservationDAO();
			ArrayList<ReservationVO> list = dao.getSpace(spaceno);
			
			req.setAttribute("list", list);		
			dao.close();
			
		}
		return "/mypage/host/reservCalendar.jsp";
	}

}
