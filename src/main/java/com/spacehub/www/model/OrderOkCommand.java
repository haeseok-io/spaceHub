package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.CouponDAO;
import com.spacehub.www.dao.CreditsDAO;
import com.spacehub.www.dao.MemCouponDAO;
import com.spacehub.www.dao.PaymentDAO;
import com.spacehub.www.dao.ReservationDAO;
import com.spacehub.www.vo.CouponVO;
import com.spacehub.www.vo.CreditsVO;
import com.spacehub.www.vo.MemCouponVO;
import com.spacehub.www.vo.PaymentVO;
import com.spacehub.www.vo.ReservationVO;
import com.spacehub.www.vo.SmemberVO;

public class OrderOkCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		HttpSession session = req.getSession();
		SmemberVO memberVO = (SmemberVO)session.getAttribute("member");
		
		  String s = req.getParameter("spaceno");
		  String orderno = req.getParameter("orderno");
		  String p = req.getParameter("amount");
		  String cardConfirmno = req.getParameter("cardConfirmno");
		  String name = req.getParameter("name");
		  String email = req.getParameter("email");
		  String addr = req.getParameter("addr");
		  String postcode = req.getParameter("postcode");
		  String cardnum = req.getParameter("cardnum");
		  String ip = req.getRemoteAddr();
		  String checkin = req.getParameter("checkin");
		  String checkout = req.getParameter("checkout");
		  String g = req.getParameter("guest");
		  String phone = req.getParameter("phone");
		  String c = req.getParameter("cprice");
		  String d = req.getParameter("dprice");
		  String couponname = req.getParameter("couponname");
		  String creditsprice = req.getParameter("creditsPrice");
		  
		  if(s != null || creditsprice != null || ip != null || checkin != null || checkout != null || g != null || c != null || d != null || couponname != null || phone != null || p != null || name != null || addr != null || postcode != null) {
			  int price = Integer.parseInt(p);
			  int spaceno = Integer.parseInt(s);
			  int guest = Integer.parseInt(g);
			  int cprice = Integer.parseInt(c);
			  int dprice = Integer.parseInt(d);
			  int credit = Integer.parseInt(creditsprice);
			  int dcratio = cprice+dprice;
			  
			  CouponDAO cdao = new CouponDAO();
			  CouponVO cvo = cdao.getOne(couponname);
			  
			  ReservationDAO rdao = new ReservationDAO();
			  ReservationVO rvo = new ReservationVO();
			  
			  rvo.setCheckin(checkin);
			  rvo.setCheckout(checkout);
			  rvo.setName(name);
			  rvo.setPhone(phone);
			  rvo.setPrice(price);
			  rvo.setGuest(guest);
			  rvo.setDcratio(dcratio);
			  rvo.setIp(ip);
			  rvo.setSpaceno(spaceno);
			  rvo.setMemno(memberVO.getMemno());
			  
			  rdao.addOne(rvo);
			  
			  ReservationVO rsvo = rdao.getSpaceOne(spaceno);
			  
			  CreditsDAO creditsdao = new CreditsDAO();
			  CreditsVO creditsvo = new CreditsVO();
			  creditsvo.setContents("크래딧 사용");
			  creditsvo.setPrice(-credit);
			  creditsvo.setMemno(memberVO.getMemno());
			  creditsdao.addOne(creditsvo);
			  creditsdao.close();
			  
			  if(cprice != 0) {
				  MemCouponDAO mdao = new MemCouponDAO();
				  MemCouponVO mvo = new MemCouponVO();
				  mvo.setStatus(2);
				  mvo.setReservno(rsvo.getReservno());
				  mvo.setMemno(memberVO.getMemno());
				  mvo.setCouponno(cvo.getCouponno());
				  
				  mdao.modifyStatus(mvo);
				  mdao.close();
			  }
			  
			  if(orderno!= null || cardConfirmno != null || email != null || cardnum != null){
				  PaymentDAO dao = new PaymentDAO();
				  PaymentVO vo = new PaymentVO();
				  vo.setApprovalNum(cardConfirmno);
				  vo.setCardNum(cardnum);
				  vo.setName(name);
				  vo.setEmail(email);
				  vo.setPrice(price);
				  vo.setReservno(rsvo.getReservno());
				  
				  dao.addOne(vo);
				  dao.close();
			  }
			  rdao.close();
		  }
		  String result = "y";
		
		return result;
	}

}
