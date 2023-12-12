package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.dao.SpaceImageDAO;
import com.spacehub.www.vo.JjimListVO;
import com.spacehub.www.vo.SmemberVO;

public class HostMainAction implements Action{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String m = req.getParameter("memno");
		HttpSession session =  req.getSession();
		SmemberVO memberData = (SmemberVO) session.getAttribute("member");

		if(memberData != null) {
			int memno = Integer.parseInt(m);
			SpaceDAO dao = new SpaceDAO();
			SpaceImageDAO imgDao = new SpaceImageDAO();
			
			ArrayList<JjimListVO> list = dao.getJjimList(memno);
			
			for(JjimListVO jjimData : list) {
				int spaceno = jjimData.getSpaceno();
				
				
				jjimData.setImgList(imgDao.getSpaceImages(spaceno, memno));
			}
			
			
			session.setAttribute("member", memberData);
			req.setAttribute("list", list);
			imgDao.close();
			dao.close();
			
		}
		return "/mypage/host/hostMain.jsp";
	}
	
}
