package com.spacehub.www.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.spacehub.www.model.ActionCommand;
import com.spacehub.www.model.CancelCommand;
import com.spacehub.www.model.OrderCommand;
import com.spacehub.www.model.OrderOkCommand;

@WebServlet("/order")
public class OrderController extends HttpServlet{
	
	protected void doPro(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		String cmd = req.getParameter("cmd");
		boolean isRedirect = false;
		JSONObject jsonObject = new JSONObject();
		String url = "";
		
		if( cmd==null ) 	cmd = "info";
		
		if(cmd.equals("info")) {
			ActionCommand ac = new  OrderCommand();
			url = ac.execute(req, resp);
		}else if(cmd.equals("orderOk")) {
			ActionCommand ac = new  OrderOkCommand();
			url = ac.execute(req, resp);
			
			isRedirect = true;
		}else if(cmd.equals("cancel")) {
			ActionCommand ac = new  CancelCommand();
			url = ac.execute(req, resp);
		}
		
		// url값이 null일 경우 메인페이지로 리다이렉트
				if( url==null ) {
					url = "/spaceHub/home";
					isRedirect = true;
				}
		
		if (!url.equals("")) {
			if (isRedirect) {
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
