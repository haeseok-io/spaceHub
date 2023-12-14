package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.MemCardDAO;
import com.spacehub.www.vo.SmemberVO;

public class CardDeleteCommand implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String m = req.getParameter("mcardno");
		HttpSession session = req.getSession();
		
		SmemberVO memberVO = (SmemberVO)session.getAttribute("member");
		if( memberVO==null ) {
			return "/sign/login.jsp";
		}
		if(m != null || memberVO != null) {
			int mcardno = Integer.parseInt(m);
			MemCardDAO dao = new MemCardDAO();
			
			dao.deleteTwo(memberVO.getMemno(), mcardno);
			dao.close();
		}
		return "/mypage/guest?cmd=cardList";
	}

}
