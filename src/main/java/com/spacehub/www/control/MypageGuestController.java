package com.spacehub.www.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.model.ActionCommand;
import com.spacehub.www.model.CardDeleteCommand;
import com.spacehub.www.model.CardListCommand;
import com.spacehub.www.model.CouponCommand;
import com.spacehub.www.model.SpaceCancelCommand;
import com.spacehub.www.model.SpaceEndCommand;
import com.spacehub.www.model.SpaceListCommand;

@WebServlet("/mypage/guest")
public class MypageGuestController extends HttpServlet{

	private void doPro(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		String cmd = req.getParameter("cmd");
		
		String msg = "";
		String url = "";
		

		if(cmd == null || cmd.equals("spaceList")) {
			ActionCommand ac = new  SpaceListCommand();
			url = ac.execute(req, resp);
		}else if(cmd.equals("coupon")) {
			ActionCommand ac = new  CouponCommand();
			url = ac.execute(req, resp);
		}else if(cmd.equals("spaceEnd")) {
			ActionCommand ac = new  SpaceEndCommand();
			url = ac.execute(req, resp);
		}else if(cmd.equals("spaceCancel")) {
			ActionCommand ac = new  SpaceCancelCommand();
			url = ac.execute(req, resp);
		} else if(cmd.equals("cardList")) {
			ActionCommand ac = new  CardListCommand();
			url = ac.execute(req, resp);
		} else if(cmd.equals("cardDelete")) {
			ActionCommand ac = new  CardDeleteCommand();
			url = ac.execute(req, resp);
		}
		
		RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.forward(req, resp);
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
