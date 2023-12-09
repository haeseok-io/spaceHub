package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.PaymentDAO;
import com.spacehub.www.vo.PaymentVO;

public class OrderOkCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		  String orderno = req.getParameter("orderno");
		  String p = req.getParameter("amount");
		  String cardConfirmno = req.getParameter("cardConfirmno");
		  String name = req.getParameter("name");
		  String email = req.getParameter("email");
		  String addr = req.getParameter("addr");
		  String postcode = req.getParameter("postcode");
		  String cardnum = req.getParameter("cardnum");
		  
		  if(orderno!= null || p != null || cardConfirmno != null || name != null || email != null || addr != null || postcode != null || cardnum != null){
			  int price = Integer.parseInt(p);
			  PaymentDAO dao = new PaymentDAO();
			  PaymentVO vo = new PaymentVO();
			  vo.setApprovalNum(orderno);
			  vo.setCardNum(cardnum);
			  vo.setName(name);
			  vo.setEmail(email);
			  vo.setPrice(price);
			  vo.setReservno(0);
			  
			  dao.addOne(vo);
			  dao.close();
			 }
		 
		
		return "main.jsp";
	}

}
