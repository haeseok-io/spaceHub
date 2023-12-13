package com.spacehub.www.model;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.ReservationDAO;
import com.spacehub.www.vo.AdminVO;
import com.spacehub.www.vo.ReservationVO;

public class ReservationListCommand implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		AdminVO admin = (AdminVO)session.getAttribute("admin");
		
		// 미로그인 상태일 경우 null 리턴
		if( admin==null ) {
			return "/admin/adminLogin.jsp";
		}else {
		
		ReservationDAO dao = new ReservationDAO();
		ArrayList<ReservationVO> list = dao.getAll();
		
		req.setAttribute("list", list);	
		dao.close();
		}
		
		return "admin/reservationList.jsp";
	}

}
