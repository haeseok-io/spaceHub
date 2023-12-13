package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.ReservationDAO;
import com.spacehub.www.vo.ResevSpaceVO;
import com.spacehub.www.vo.SmemberVO;

public class SpaceEndCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		
		SmemberVO memberVO = (SmemberVO)session.getAttribute("member");
//		System.out.println(memberVO.getMemno());
		
		// 미로그인 상태일 경우 null 리턴
		if( memberVO==null ) {
			return "/sign/login.jsp";
		}else {
		
		ReservationDAO dao = new ReservationDAO();
		ArrayList<ResevSpaceVO> list = dao.getEnd(memberVO.getMemno());
		
		dao.close();
		req.setAttribute("list", list);
		}
		return "/mypage/guest/spaceEnd.jsp";
	}

}
