package com.spacehub.www.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.model.Action;
import com.spacehub.www.model.AdminLoginOkCommand;
import com.spacehub.www.model.AuthOkCommand;
import com.spacehub.www.model.LoginOkCommand;
import com.spacehub.www.model.LogoutCommand;
import com.spacehub.www.model.PwfindOkCommand;
import com.spacehub.www.model.ReservationListCommand;
import com.spacehub.www.model.SignupOkCommand;

@WebServlet("/admin")
public class AdminControl extends HttpServlet{
	
	protected void doPro(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		String cmd = req.getParameter("cmd");
		
		boolean isRedirect = false;
		String url = "";
		
		if(cmd == null || cmd.equals("admin")) {
			url = "/admin/adminLogin.jsp";
		} else if(cmd.equals("loginOk")) {
			Action ac = new AdminLoginOkCommand();
			url = ac.execute(req, resp);
			
			isRedirect = true;
		} else if(cmd.equals("logoutOk")) {
			Action ac = new LogoutCommand();
			url = ac.execute(req, resp);
			isRedirect = true;
		} else if(cmd.equals("reservationList")) {
			Action ac = new ReservationListCommand();
			url = ac.execute(req, resp);
		} 
		

		if( isRedirect ) {
			resp.sendRedirect(url);
		} else {
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, resp);			
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
