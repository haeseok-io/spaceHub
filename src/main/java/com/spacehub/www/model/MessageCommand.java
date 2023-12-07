package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.MessageDAO;
import com.spacehub.www.dao.ReservationDAO;
import com.spacehub.www.vo.ReservMessageVO;
import com.spacehub.www.vo.ResevSpaceVO;
import com.spacehub.www.vo.SmemberVO;
import com.spacehub.www.vo.SpaceHostVO;

public class MessageCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String r = req.getParameter("reservno");
		if(r != null) {
			int reservno = Integer.parseInt(r);
			ReservationDAO dao = new ReservationDAO();
			ArrayList<SpaceHostVO> list = dao.getHone(reservno);
			
			MessageDAO mdao = new MessageDAO();
			ArrayList<ReservMessageVO> mlist = mdao.getMC(reservno);
			
			HttpSession session = req.getSession();
			SmemberVO memberVO = (SmemberVO)session.getAttribute("vo");
			ArrayList<ResevSpaceVO> slist = dao.getAll(memberVO.getMemno());
			
			req.setAttribute("mlist", mlist);
			req.setAttribute("slist", slist);
			req.setAttribute("list", list);		
			dao.close();
		}
		
		
		
		
		return "message/messageSend.jsp";
	}

}
