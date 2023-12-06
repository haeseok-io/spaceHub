package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.ReportDAO;
import com.spacehub.www.vo.ReportVO;

public class ReportListAction implements Action{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		int memno = Integer.parseInt(req.getParameter("memno"));
		
		ReportDAO dao = new ReportDAO();
		
		ArrayList<ReportVO> list = dao.getAll(memno);
		
		for(ReportVO vo : list) {
			vo.getSubject();
			vo.getContents();
			vo.getRegdate();
			vo.getSpaceSubject();
		}
		return "/reportList.jsp";
	}
	
}
