package com.spacehub.www.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.spacehub.www.model.JsonAction;
import com.spacehub.www.model.MessageContentAction;
import com.spacehub.www.model.MessageListAction;

@WebServlet("/message")
public class MessageControl extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		String cmd = req.getParameter("cmd");
		
		boolean isRedirect = false;
		JSONObject jsonObject = new JSONObject();
		String url = "";
		
		if( cmd==null ) 	cmd = "list";
		
		if( cmd.equals("list") ) {
			url = "/message/list.jsp";
		} else if( cmd.equals("listData") ) {
			JsonAction ac = new MessageListAction();
			jsonObject = ac.execute(req, resp);
		} else if( cmd.equals("contentData") ) {
			JsonAction ac = new MessageContentAction();
			jsonObject = ac.execute(req, resp);
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
