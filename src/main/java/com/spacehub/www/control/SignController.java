package com.spacehub.www.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.model.ActionCommand;
import com.spacehub.www.model.LoginCommand;
import com.spacehub.www.model.LoginOkCommand;
import com.spacehub.www.model.LogoutCommand;
import com.spacehub.www.model.SignupCommand;
import com.spacehub.www.model.SignupOkCommand;

@WebServlet("/sign")
public class SignController extends HttpServlet{
	
	protected void doPro(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		String cmd = req.getParameter("cmd");
		
		String msg = "";
		String url = "";
		
		if(cmd == null || cmd.equals("login")) {
			ActionCommand ac = new  LoginCommand();
			url = ac.execute(req, resp);
		}else if(cmd.equals("signupOk")) {
			ActionCommand ac = new SignupOkCommand();
			url = ac.execute(req, resp);
		}else if(cmd.equals("signup")) {
			ActionCommand ac = new SignupCommand();
			url = ac.execute(req, resp);
		}else if(cmd.equals("loginOk")) {
			ActionCommand ac = new LoginOkCommand();
			url = ac.execute(req, resp);
		}else if(cmd.equals("logout")) {
			ActionCommand ac = new LogoutCommand();
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
