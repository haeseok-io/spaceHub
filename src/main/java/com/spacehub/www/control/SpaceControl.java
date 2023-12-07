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
import com.spacehub.www.model.LikeDeleteOkAction;
import com.spacehub.www.model.LikeWriteAction;
import com.spacehub.www.model.SpaceDetailAction;
import com.spacehub.www.model.SpaceListAction;
import com.spacehub.www.model.SpaceWriteAction;

@WebServlet("/space")
public class SpaceControl extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		String cmd = req.getParameter("cmd");
		
		boolean isRedirect = false;
		String url = "";
		JSONObject jsonObject = new JSONObject();
		
		if( cmd==null ) {
			url = "/spaceHub/home";
			isRedirect = true;
		}  else if( cmd.equals("list") ) {
			JsonAction ac = new SpaceListAction();
			jsonObject = ac.execute(req, resp); 
		} else if( cmd.equals("detail") ) {
			Action ac = new SpaceDetailAction();
			url = ac.execute(req, resp);
			
			if( url==null ) {
				url = "/spaceHub/home";
				isRedirect = true;
			}
		} else if( cmd.equals("likeWriteOk") ) {
			JsonAction ac = new LikeWriteAction();
			jsonObject = ac.execute(req, resp);
		} else if( cmd.equals("likeDeleteOk") ) {
			JsonAction ac = new LikeDeleteOkAction();
			jsonObject = ac.execute(req, resp);
		} else if(cmd.equals("write")) {
			Action ac = new SpaceWriteAction();
			url = ac.execute(req, resp);
		} 
		
		
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
