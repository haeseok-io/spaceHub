package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.ReservationDAO;
import com.spacehub.www.vo.ResevSpaceVO;
import com.spacehub.www.vo.SmemberVO;

public class SpaceListCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		ReservationDAO dao = new ReservationDAO();
		HttpSession session = req.getSession();
		
		SmemberVO memberVO = (SmemberVO)session.getAttribute("member");
//		System.out.println(memberVO.getMemno());
		ArrayList<ResevSpaceVO> list = dao.getAll(memberVO.getMemno());
		
		dao.close();
		req.setAttribute("list", list);
		
		return "guest/space.jsp";
	}

}
