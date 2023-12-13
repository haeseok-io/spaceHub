package com.spacehub.www.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.model.Action;
import com.spacehub.www.model.EmailAuthAction;
import com.spacehub.www.model.LikeAction;
import com.spacehub.www.model.ModifyAction;
import com.spacehub.www.model.ModifyOkAction;
import com.spacehub.www.model.PwAuthAction;
import com.spacehub.www.model.pwAuthOkAction;

@WebServlet("/mypage")
public class MypageControl extends HttpServlet{
	
	private void doPro(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		String cmd = req.getParameter("cmd");
		String url = "";
		
		boolean isRedirect = false;
		
		if(cmd == null || cmd.equals("modify")) {
			Action action = new	ModifyAction();
			url = action.execute(req, resp);
		}else if(cmd.equals("modifyOk")) {
			Action action = new ModifyOkAction();
			url = action.execute(req, resp);
			isRedirect = true;
		}else if(cmd.equals("likeList")) {
			Action action = new LikeAction();
			url = action.execute(req, resp);
		}else if(cmd.equals("pwAuth")) {
			Action action = new PwAuthAction();
			url = action.execute(req, resp);
		}else if(cmd.equals("emailAuth")) {
			Action action = new EmailAuthAction();
			url = action.execute(req, resp);
		}else if(cmd.equals("pwAuthOk")) {
			Action action = new pwAuthOkAction();
			url = action.execute(req, resp);
			
			isRedirect = true;
		}
		if(isRedirect) {
			resp.sendRedirect(url);
		}else {
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, resp);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPro(req, resp);		
	}
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPro(req, resp);
	}
}
