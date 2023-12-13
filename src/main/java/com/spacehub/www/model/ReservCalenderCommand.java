package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.ReservationDAO;
import com.spacehub.www.vo.ReservationVO;
import com.spacehub.www.vo.SmemberVO;

public class ReservCalenderCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String s = req.getParameter("spaceno");
		HttpSession session = req.getSession();
		SmemberVO smemberData = (SmemberVO)session.getAttribute("member");
		
		// 미로그인 상태일 경우 null 리턴
		if( smemberData==null ) {
			return "/sign/login.jsp";
		}
		if(s != null || smemberData!=null) {
			int spaceno = Integer.parseInt(s);
			ReservationDAO dao = new ReservationDAO();
			ArrayList<ReservationVO> list = dao.getSpace(spaceno);
			
			req.setAttribute("spaceno", spaceno);
			req.setAttribute("list", list);		
			dao.close();
			
		}
		return "/mypage/host/reservCalendar.jsp";
	}

}
