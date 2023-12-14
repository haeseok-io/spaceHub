package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.MemCardDAO;
import com.spacehub.www.dao.ReservationDAO;
import com.spacehub.www.vo.MemCardVO;
import com.spacehub.www.vo.ReservationVO;
import com.spacehub.www.vo.ResevSpaceVO;
import com.spacehub.www.vo.SmemberVO;

public class CardListCommand implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		HttpSession session = req.getSession();
		
		SmemberVO memberVO = (SmemberVO)session.getAttribute("member");
//		System.out.println(memberVO.getMemno());
		
		if( memberVO==null ) {
			return "/sign/login.jsp";
		}else {
		
		MemCardDAO dao = new MemCardDAO();
		ArrayList<MemCardVO> list = dao.getAll(memberVO.getMemno());
		
		dao.close();
		req.setAttribute("list", list);
		}
		
		return "/mypage/guest/cardList.jsp";
	}

}
