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
import com.spacehub.www.model.ActionCommand;
import com.spacehub.www.model.HostMainAction;
import com.spacehub.www.model.ImageDeleteOkAction;
import com.spacehub.www.model.JsonAction;
import com.spacehub.www.model.ReservCalenderCommand;
import com.spacehub.www.model.ReservListCommand;
import com.spacehub.www.model.SpaceModifyAction;
import com.spacehub.www.model.SpaceModifyOkAction;

@WebServlet("/mypage/host")
public class MypageHostController extends HttpServlet{

	private void doPro(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		String cmd = req.getParameter("cmd");
		boolean isRedirect = false;
		JSONObject jsonObject = new JSONObject();
		String url = "";
		
		if(cmd == null || cmd.equals("hostMain")) {
			Action ac = new HostMainAction();
			url = ac.execute(req, resp);
		}else if(cmd.equals("reservCalender")) {
			ActionCommand ac = new  ReservCalenderCommand();
			url = ac.execute(req, resp);
		}else if(cmd.equals("reservList")) {
			ActionCommand ac = new  ReservListCommand();
			url = ac.execute(req, resp);
		} else if(cmd.equals("spaceModify")) {
			Action ac = new SpaceModifyAction();
			url = ac.execute(req, resp);
		} else if(cmd.equals("spaceModifyOk")) {
			System.out.println("spaceModifyOk 연결 성공");
			Action ac = new SpaceModifyOkAction();
			url = ac.execute(req, resp);	
			isRedirect = true;
		} else if(cmd.equals("imgDeleteOk")) {
			JsonAction ac = new ImageDeleteOkAction();
			jsonObject = ac.execute(req, resp);
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
