package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.ReservationDAO;
import com.spacehub.www.vo.ReservationVO;

public class CancelCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String r = req.getParameter("reservno");
		System.out.println(r);
		if(r != null) {
			int reservno = Integer.parseInt(r);
			ReservationDAO dao = new ReservationDAO();
			ReservationVO vo = new ReservationVO();
			vo.setStatus(4);
			vo.setReservno(reservno);
			dao.modifyStatus(vo);
		}
		return "/mypage/guest?cmd=spaceList";
	}

}
