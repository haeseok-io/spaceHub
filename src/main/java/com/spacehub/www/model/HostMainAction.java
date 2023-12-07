package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.dao.SpaceImageDAO;
import com.spacehub.www.vo.JjimListVO;

public class HostMainAction implements Action{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String m = req.getParameter("memno");
		
		if(m != null) {
			int memno = Integer.parseInt(m);
			SpaceDAO dao = new SpaceDAO();
			SpaceImageDAO imgDao = new SpaceImageDAO();
			
			ArrayList<JjimListVO> list = dao.getJjimList(memno);
			
			for(JjimListVO jjimData : list) {
				int spaceno = jjimData.getSpaceno();
				
				
				jjimData.setImgList(imgDao.getSpaceImages(spaceno, memno));
			}
			
			
			
			req.setAttribute("list", list);
			dao.close();
			
		}
		return "/mypage/host/hostMain.jsp";
	}
	
}
