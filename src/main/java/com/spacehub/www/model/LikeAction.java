package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.vo.JjimListVO;

public class LikeAction implements Action{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String m = req.getParameter("memno");
		
		if(m != null) {
			int memno = Integer.parseInt(m);
			
			SpaceDAO dao = new SpaceDAO();
			
			ArrayList<JjimListVO> list = dao.getJjimList(memno);
			
			dao.close();
			req.setAttribute("list", list);
			
		}
		return "likeList.jsp";
	}
	
}
