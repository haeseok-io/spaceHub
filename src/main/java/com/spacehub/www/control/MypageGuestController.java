package com.spacehub.www.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.spacehub.www.model.Action;
import com.spacehub.www.model.CardDeleteCommand;
import com.spacehub.www.model.CardListCommand;
import com.spacehub.www.model.CouponCommand;
import com.spacehub.www.model.GuestReservationListAction;
import com.spacehub.www.model.JsonAction;
import com.spacehub.www.model.GuestReservationDataAction;

@WebServlet("/mypage/guest")
public class MypageGuestController extends HttpServlet {

	private void doPro(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		String cmd = req.getParameter("cmd");
		
		boolean isRedirect = false;
		JSONObject jsonObject = new JSONObject();
		String url = "";
		
		// cmd값이 없으면 예약내역으로 고정
		if( cmd==null ) 	cmd = "reservationList";

		if( cmd.equals("reservationList") ) { 			// 회원 예약 페이지
			Action ac = new  GuestReservationListAction();
			url = ac.execute(req, resp);
		} else if( cmd.equals("reservationData")) { 	// 회원 예약 데이터
			JsonAction ac = new GuestReservationDataAction();
			jsonObject = ac.execute(req, resp);
		} else if(cmd.equals("coupon")) {
			Action ac = new  CouponCommand();
			url = ac.execute(req, resp);
		} else if(cmd.equals("cardList")) {
			Action ac = new  CardListCommand();
			url = ac.execute(req, resp);
		} else if(cmd.equals("cardDelete")) {
			Action ac = new  CardDeleteCommand();
			url = ac.execute(req, resp);
		}
		
		// url 이 null 일경우 메인페이지로 이동
		if( url==null ) {
			url = "/spaceHub/home";
			isRedirect = true;
		}
		
		// Result
		if( !url.equals("") ) {
			if( isRedirect ) {
				resp.sendRedirect(url);							
			} else {
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req, resp);
			}
		} else {
			resp.getWriter().print(jsonObject.toJSONString());
            resp.getWriter().flush();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPro(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPro(req, resp);
	}
}
