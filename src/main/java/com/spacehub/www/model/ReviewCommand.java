package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.ReservationDAO;
import com.spacehub.www.vo.SmemberVO;
import com.spacehub.www.vo.SpaceHostVO;

public class ReviewCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String r = req.getParameter("reservno");
		HttpSession session = req.getSession();
		SmemberVO smemberData = (SmemberVO)session.getAttribute("member");
		
		// 미로그인 상태일 경우 null 리턴
		if( smemberData==null ) {
			return "/sign/login.jsp";
		}
		if(r != null || smemberData!=null) {
			int reservno = Integer.parseInt(r);
			ReservationDAO dao = new ReservationDAO();
			ArrayList<SpaceHostVO> list = dao.getHone(reservno);
			req.setAttribute("list", list);		
			dao.close();
		}
		
		return "/review/review.jsp";
	}

}
