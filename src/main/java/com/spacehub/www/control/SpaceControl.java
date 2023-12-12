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
import com.spacehub.www.model.LikeWriteOkAction;
import com.spacehub.www.model.SpaceDetailAction;
import com.spacehub.www.model.SpaceListAction;
import com.spacehub.www.model.SpaceWriteAction;
import com.spacehub.www.model.spaceDetailDataAction;

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
		} else if( cmd.equals("detailData") ) {
			JsonAction ac = new spaceDetailDataAction();
			jsonObject = ac.execute(req, resp);
		} else if( cmd.equals("likeWriteOk") ) {
			JsonAction ac = new LikeWriteOkAction();
			jsonObject = ac.execute(req, resp);
		} else if( cmd.equals("likeDeleteOk") ) {
			JsonAction ac = new LikeDeleteOkAction();
			jsonObject = ac.execute(req, resp);
		} else if(cmd.equals("write")) {
			Action ac = new SpaceWriteAction();
			url = ac.execute(req, resp);
		} 
		
		// url값이 null일 경우 메인페이지로 리다이렉트
		if( url==null ) {
			url = "/spaceHub/home";
			isRedirect = true;
		}
		
		// url이 있을경우 페이지 이동, 없을경우엔 json 데이터 리턴
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
