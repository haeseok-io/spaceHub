package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.MemCardDAO;
import com.spacehub.www.vo.SmemberVO;

public class CardDeleteCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String m = req.getParameter("mcardno");
		
		if(m != null) {
			int mcardno = Integer.parseInt(m);
			MemCardDAO dao = new MemCardDAO();
			HttpSession session = req.getSession();
			
			SmemberVO memberVO = (SmemberVO)session.getAttribute("member");
			
			dao.deleteTwo(memberVO.getMemno(), mcardno);
		}
		return "/mypage/guest?cmd=cardList";
	}

}
