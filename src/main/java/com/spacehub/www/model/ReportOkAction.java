package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.ReportDAO;
import com.spacehub.www.vo.ReportVO;

public class ReportOkAction implements Action{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String m = req.getParameter("memno");
		
		if(m != null) {
			int memno = Integer.parseInt(m);
			
			ReportDAO dao = new ReportDAO();
			
			ArrayList<ReportVO> list = dao.getAll(memno);
			
			dao.close();
			req.setAttribute("list", list);
		}
		return "reportList.jsp";
	}

}
