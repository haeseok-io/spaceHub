package com.spacehub.www.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.model.Action;
import com.spacehub.www.model.LoginOkCommand;
import com.spacehub.www.model.LogoutCommand;
import com.spacehub.www.model.SignupOkCommand;

@WebServlet("/sign")
public class SignController extends HttpServlet{
	
	protected void doPro(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		String cmd = req.getParameter("cmd");
		
		boolean isRedirect = false;
		String msg = "";
		String url = "";
		
		if(cmd == null || cmd.equals("login")) {
			url = "/sign/login.jsp";
		} else if(cmd.equals("loginOk")) {
			Action ac = new LoginOkCommand();
			url = ac.execute(req, resp);
			
			isRedirect = true;
		} else if(cmd.equals("logoutOk")) {
			Action ac = new LogoutCommand();
			url = ac.execute(req, resp);
			
			isRedirect = true;
		} else if(cmd.equals("signup")) {
			url = "/sign/signup.jsp";
		} else if(cmd.equals("signupOk")) {
			Action ac = new SignupOkCommand();
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
