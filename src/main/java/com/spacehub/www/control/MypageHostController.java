package com.spacehub.www.control;

import com.spacehub.www.model.*;
import org.json.simple.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/mypage/host")
public class MypageHostController extends HttpServlet {

	private void doPro(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		String cmd = req.getParameter("cmd");
		boolean isRedirect = false;
		JSONObject jsonObject = new JSONObject();
		String url = "";

		if (cmd == null || cmd.equals("hostMain")) {
			Action ac = new HostMainAction();
			url = ac.execute(req, resp);
		} else if (cmd.equals("spaceModify")) {
			Action ac = new SpaceModifyAction();
			url = ac.execute(req, resp);
		} else if (cmd.equals("sapceDelete")) {
			Action ac = new SpaceDeleteAction();
			url = ac.execute(req, resp);
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
		doPro(req, resp);
	}
	
}
