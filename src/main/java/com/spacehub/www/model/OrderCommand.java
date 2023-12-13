package com.spacehub.www.model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.CreditsDAO;
import com.spacehub.www.dao.DiscountDAO;
import com.spacehub.www.dao.MemCouponDAO;
import com.spacehub.www.dao.SmemberDAO;
import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.dao.SpaceDetailDAO;
import com.spacehub.www.vo.CreditsVO;
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
		SmemberVO memberVO = (SmemberVO)session.getAttribute("member");
		
		if(memberVO != null) {
			if(checkin == "" || checkout == "" || guest == "") {
				return "main.jsp";
			}
			
			if(s != null || checkin != null || checkout != null || guest != null) {
				int Spaceno = Integer.parseInt(s);
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
				CreditsDAO crdao = new CreditsDAO();
				CreditsVO crvo = crdao.getSum(memberVO.getMemno());
				
				SpaceDetailDAO sddao = new SpaceDetailDAO();
				SpaceDetailVO sddvo = sddao.getOne(Spaceno);
				
				String bark = "";
				SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
				java.util.Date ckin = null;
				java.util.Date ckout = null;
				try {
					ckin = format.parse(checkin);
					ckout = format.parse(checkout);
					
					long b = (ckout.getTime()-ckin.getTime())/(24*60*60*1000);
					bark = Long.toString(b);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				int tax = (int)(vo.getPrice()*(Integer.parseInt(bark))*0.01);
				
				int dc = 0;
				String sna = "";
				if(dlist != null) {
					if(dvo.getName() != null) {
						
						if(sdvo.getReservno() < 3) {
							dc = dvo.getDcratio();
							sna = dvo.getName();
						}else if(dwvo.getName() != null) {
							
							if(Integer.parseInt(bark) > 6) {
								dc = dwvo.getDcratio();
								sna = dwvo.getName();
							}
							
						}else if(dmvo.getName() != null) {
							
							if(Integer.parseInt(bark) > 27) {
								dc = dmvo.getDcratio();
								sna = dmvo.getName();
							}
							
						}else {
							dc = 0;
							sna = "호스트의 할인";
						}
						
					}else if(dwvo.getName() != null) {
						
						if(Integer.parseInt(bark) > 6) {
							dc = dwvo.getDcratio();
							sna = dwvo.getName();
						}else if(dmvo.getName() != null) {
							
							if(Integer.parseInt(bark) > 27) {
								dc = dmvo.getDcratio();
								sna = dmvo.getName();
							}
							
						}else {
							dc = 0;
							sna = "호스트의 할인";
						}
						
					}else if(dmvo.getName() != null) {
						
						if(Integer.parseInt(bark) > 27) {
							dc = dmvo.getDcratio();
							sna = dmvo.getName();
						}else {
							dc = 0;
							sna = "호스트의 할인";
						}
						
					}else {
						dc = 0;
						sna = "호스트의 할인";
					}
				}else {
					dc = 0;
					sna = "호스트의 할인";
				}
				
				
				
				double disc = (100-dc)*0.01;
				int endprice = (int)((vo.getPrice()*Integer.parseInt(bark)+tax)*disc);
				
				req.setAttribute("endprice", endprice);
				req.setAttribute("sna", sna);
				req.setAttribute("crvo", crvo);
				req.setAttribute("tax", tax);
				req.setAttribute("bark", bark);
				req.setAttribute("checkin", checkin);
				req.setAttribute("checkout", checkout);
				req.setAttribute("guest", guest);
				req.setAttribute("sddvo", sddvo);
				req.setAttribute("dc", dc);
				req.setAttribute("vo", vo);
				req.setAttribute("smvo", smvo);
				req.setAttribute("list", list);
				dao.close();
				mdao.close();
				smdao.close();
				ddao.close();
				crdao.close();
				sddao.close();
			}
		}else {
			return "/sign/login.jsp";
		}
		
		return "/order/info.jsp";
	}

}
