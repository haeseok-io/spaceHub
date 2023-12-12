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
import com.spacehub.www.model.JsonAction;
import com.spacehub.www.model.MessageContentDataAction;
import com.spacehub.www.model.MessageListAction;
import com.spacehub.www.model.MessageListDataAction;
import com.spacehub.www.model.MessageWriteOkAction;

@WebServlet("/message")
public class MessageControl extends HttpServlet {
	boolean isRedirect;
	String url;
	JSONObject jsonObject;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Val
		String cmd = req.getParameter("cmd");
		url = "";
		jsonObject = null;
		isRedirect = false;
		
		// Init
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		// Check
		// - cmd 값이 없을경우 list 로 고정
		if( cmd==null ) 	cmd = "list";
		
		// Data
		if( cmd.equals("list") ) {
			Action ac = new MessageListAction();
			url = ac.execute(req, resp);
		} else if( cmd.equals("listData") ) {
			JsonAction ac = new MessageListDataAction();
			jsonObject = ac.execute(req, resp);			
		} else if( cmd.equals("contentData") ) {
			JsonAction ac = new MessageContentDataAction();
			jsonObject = ac.execute(req, resp);
		}
		
		// Result 
		doResult(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Val
		String cmd = req.getParameter("cmd");
		url = "";
		jsonObject = null;
		isRedirect = true;
		
		// Init
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		// Data
		if( cmd.equals("writeOk") ) {
			JsonAction ac = new MessageWriteOkAction();
			jsonObject = ac.execute(req, resp);
		}
		
		// Result
		doResult(req, resp);
	}
	
	protected void doResult (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check
		// - url 이 null 로 리턴될 경우 메인페이지로 리다이렉트
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
}
