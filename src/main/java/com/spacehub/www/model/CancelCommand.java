package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.ReservationDAO;
import com.spacehub.www.vo.ReservationVO;
import com.spacehub.www.vo.SmemberVO;

public class CancelCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String r = req.getParameter("reservno");
		System.out.println(r);
		HttpSession session = req.getSession();
		
		SmemberVO memberVO = (SmemberVO)session.getAttribute("member");
		if( memberVO==null) {
			return "/sign/login.jsp";
		}
		if(r != null || memberVO != null) {
			int reservno = Integer.parseInt(r);
			ReservationDAO dao = new ReservationDAO();
			ReservationVO vo = new ReservationVO();
			vo.setStatus(4);
			vo.setReservno(reservno);
			dao.modifyStatus(vo);
			dao.close();
		}
		return "/mypage/guest?cmd=spaceList";
	}

}
