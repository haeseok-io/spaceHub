package com.spacehub.www.model;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.dao.SpaceImageDAO;
import com.spacehub.www.vo.HostSpaceImageVO;
import com.spacehub.www.vo.SpaceImageVO;
import com.spacehub.www.vo.SpaceVO;

public class SpaceModifyAction implements Action {
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String memno = req.getParameter("memno");
		String spaceno = req.getParameter("spaceno");
		if(memno!=null && spaceno!=null) {
			SpaceDAO spaceDao = new SpaceDAO();
			SpaceImageDAO spaceImageDao = new SpaceImageDAO();
			SpaceVO spaecVo = spaceDao.getOne(Integer.parseInt(spaceno));
			ArrayList<HostSpaceImageVO> list = spaceImageDao.getSpaceImages(Integer.parseInt(spaceno),Integer.parseInt(memno));
			req.setAttribute("spaceVo", spaecVo);
			req.setAttribute("list", list);
			spaceDao.close();
		}
		return "/space/spaceModifyForm.jsp";
	}
}
