package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.PaymentDAO;
import com.spacehub.www.dao.ReservationDAO;

public class OrderOkCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		/*
		 * String orderno = req.getParameter("orderno"); String price =
		 * req.getParameter("amount"); String cardConfirmno =
		 * req.getParameter("cardConfirmno");
		 * 
		 * if(orderno != null){ PaymentDAO p = new PaymentDAO(); String result =
		 * p.addOne(cardConfirmno, price, orderno); System.out.println(result); }
		 */
		
		return "/order/ordercomplete.jsp";
	}

}
