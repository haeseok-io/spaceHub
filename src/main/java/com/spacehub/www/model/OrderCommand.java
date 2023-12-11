package com.spacehub.www.model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.DiscountDAO;
import com.spacehub.www.dao.MemCouponDAO;
import com.spacehub.www.dao.SmemberDAO;
import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.dao.SpaceDetailDAO;
import com.spacehub.www.vo.DiscountVO;
import com.spacehub.www.vo.MCouponVO;
import com.spacehub.www.vo.SmemberVO;
import com.spacehub.www.vo.SpaceDetailVO;
import com.spacehub.www.vo.SpaceDiscountVO;
import com.spacehub.www.vo.SpaceVO;

public class OrderCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String s = req.getParameter("spaceno");
		String checkin = req.getParameter("checkin");
		String checkout = req.getParameter("checkout");
		String guest = req.getParameter("guest");
		
		HttpSession session = req.getSession();
		
		if((SmemberVO)session.getAttribute("member") != null) {
			if(s != null || checkin != null || checkout != null || guest != null) {
				int Spaceno = Integer.parseInt(s);
				SmemberVO memberVO = (SmemberVO)session.getAttribute("member");
				MemCouponDAO mdao = new MemCouponDAO();
				ArrayList<MCouponVO> list = mdao.getCMem(1,memberVO.getMemno());
				SpaceDAO dao = new SpaceDAO();
				SpaceVO vo = dao.getOne(Spaceno);
				SpaceDiscountVO sdvo = dao.getRes(memberVO.getMemno(), Spaceno);
				SmemberDAO smdao = new SmemberDAO();
				SmemberVO smvo = smdao.getOne(memberVO.getMemno());
				DiscountDAO ddao = new DiscountDAO();
				ArrayList<DiscountVO> dlist = ddao.getOne(Spaceno);
				DiscountVO dvo = ddao.getTwo(Spaceno);
				DiscountVO dwvo = ddao.getWeek(Spaceno);
				DiscountVO dmvo = ddao.getMonth(Spaceno);
				
				SpaceDetailDAO sddao = new SpaceDetailDAO();
				SpaceDetailVO sddvo = sddao.getOne(Spaceno);
				
				int a = 0;
				int dc = 0;
				if(dlist != null) {
					if(dvo != null) {
						if(sdvo != null) {
							if(sdvo.getReservno() <= 2){
								dc = dvo.getDcratio();
							}
						}else {
							dc = dvo.getDcratio();
						}
					}else if(dwvo != null) {
						if(a>=7) {
							dc = dwvo.getDcratio();
						}
					}else if(dmvo != null) {
						if(a>=28) {
							dc = dmvo.getDcratio();
						}
					}else {
						dc = 0;
					}
				}else {
					dc = 0;
				}
				
				req.setAttribute("checkin", checkin);
				req.setAttribute("checkout", checkout);
				req.setAttribute("guest", guest);
				req.setAttribute("sddvo", sddvo);
				req.setAttribute("dc", dc);
				req.setAttribute("vo", vo);
				req.setAttribute("smvo", smvo);
				req.setAttribute("list", list);
				dao.close();
			}
		}else {
			return "/sign/login.jsp";
		}
		
		return "/order/info.jsp";
	}

}
