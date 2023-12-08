package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.MemCouponDAO;
import com.spacehub.www.vo.MCouponVO;
import com.spacehub.www.vo.SmemberVO;

public class CouponCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		MemCouponDAO dao = new MemCouponDAO();
		HttpSession session = req.getSession();
		
		SmemberVO memberVO = (SmemberVO)session.getAttribute("member");
//		System.out.println(memberVO.getMemno());
		ArrayList<MCouponVO> list = dao.getMem(memberVO.getMemno());
		
		ArrayList<MCouponVO> slist = dao.getCMem(1, memberVO.getMemno());
		ArrayList<MCouponVO> elist = dao.getCMem(2, memberVO.getMemno());
		
		req.setAttribute("list", list);
		req.setAttribute("slist", slist);
		req.setAttribute("elist", elist);
		dao.close();
		
		return "guest/couponList.jsp";
	}

}
