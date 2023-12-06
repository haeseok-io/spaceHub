package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.vo.SpaceVO;

public class OrderCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String s = req.getParameter("spaceno");
		
		if(s != null) {
			int Spaceno = Integer.parseInt(s);
			SpaceDAO dao = new SpaceDAO();
			SpaceVO vo = dao.getOne(Spaceno);
			req.setAttribute("vo", vo);
			dao.close();
		}
		
		return "/order/info.jsp";
	}

}
