package com.spacehub.www.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.model.Action;
import com.spacehub.www.model.SpaceListAction;

@WebServlet("/home")
public class MainControl extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		boolean isRedirect = false;
		String url = "";
		String cmd = req.getParameter("cmd");
		
		if( cmd==null || cmd.equals("list") ) {
			Action ac = new SpaceListAction();
			url = ac.execute(req, resp);
		}
		
		
		RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.forward(req,  resp);
	}
}
