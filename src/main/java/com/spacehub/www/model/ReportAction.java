package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.ReportDAO;
import com.spacehub.www.vo.ReportVO;

public class ReportAction implements Action{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		return "space?cmd=report";
	}

}
