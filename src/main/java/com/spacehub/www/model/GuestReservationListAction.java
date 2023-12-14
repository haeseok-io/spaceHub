package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.vo.SmemberVO;

public class GuestReservationListAction implements Action {
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();		
		SmemberVO memberVO = (SmemberVO)session.getAttribute("member");
		
		// 회원이 아닐 경우 메인페이지로 이동되도록
		if( memberVO==null ) {
			return null;
		}

		return "guest/space.jsp";
	}
}