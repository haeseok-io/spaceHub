package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.ReviewDAO;
import com.spacehub.www.vo.ResevSpaceVO;
import com.spacehub.www.vo.ReviewSpaceVO;
import com.spacehub.www.vo.ReviewVO;
import com.spacehub.www.vo.SmemberVO;

public class ReviewListCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		
		ReviewDAO dao = new ReviewDAO();
		HttpSession session = req.getSession();
		
		SmemberVO memberVO = (SmemberVO)session.getAttribute("member");
//		System.out.println(memberVO.getMemno());
		
		// 미로그인 상태일 경우 null 리턴
		if( memberVO==null ) {
			return "/sign/login.jsp";
		}else {
		ArrayList<ReviewSpaceVO> list = dao.getSall(memberVO.getMemno());
		req.setAttribute("list", list);		
		dao.close();
		}
		
		return "review/reviewList.jsp";
	}

}
