package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.DiscountDAO;
import com.spacehub.www.dao.MemCouponDAO;
import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.vo.DiscountVO;
import com.spacehub.www.vo.MCouponVO;
import com.spacehub.www.vo.SmemberVO;
import com.spacehub.www.vo.SpaceDiscountVO;
import com.spacehub.www.vo.SpaceVO;

public class OrderCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String s = req.getParameter("spaceno");
		
		HttpSession session = req.getSession();
		
		if(s != null) {
			int Spaceno = Integer.parseInt(s);
			SmemberVO memberVO = (SmemberVO)session.getAttribute("member");
			MemCouponDAO mdao = new MemCouponDAO();
			ArrayList<MCouponVO> list = mdao.getCMem(1,memberVO.getMemno());
			SpaceDAO dao = new SpaceDAO();
			SpaceVO vo = dao.getOne(Spaceno);
			ArrayList<SpaceDiscountVO> dlist = dao.getSD(Spaceno);
			SpaceDiscountVO sdvo = dao.getRes(memberVO.getMemno(), Spaceno);
			
			int dc = 0;
			if(sdvo != null) {
				if(sdvo.getReservno() <= 2){
					dc = 20;
				}else {
					dc = 10;
				}
				
			}else {
				dc = 20;
			}
			
			req.setAttribute("dc", dc);
			req.setAttribute("vo", vo);
			req.setAttribute("dlist", dlist);
			req.setAttribute("list", list);
			dao.close();
		}
		
		return "/order/info.jsp";
	}

}
