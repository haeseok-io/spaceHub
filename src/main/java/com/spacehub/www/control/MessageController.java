package com.spacehub.www.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.model.ActionCommand;
import com.spacehub.www.model.MessageCommand;
import com.spacehub.www.model.MessageListCommand;
import com.spacehub.www.model.MessageOkCommand;
import com.spacehub.www.model.SpaceListCommand;

@WebServlet("/message")
public class MessageController  extends HttpServlet{

	private void doPro(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		String cmd = req.getParameter("cmd");
		
		String msg = "";
		String url = "";
		
		if(cmd == null || cmd.equals("messageList")) {
			ActionCommand ac = new  MessageListCommand();
			url = ac.execute(req, resp);
		}else if(cmd.equals("message")) {
			ActionCommand ac = new  MessageCommand();
			url = ac.execute(req, resp);
		}else if(cmd.equals("messageOk")) {
			ActionCommand ac = new  MessageOkCommand();
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
