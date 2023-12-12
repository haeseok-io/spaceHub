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
		HttpSession session = req.getSession(true);
		
		SmemberVO memberData = (SmemberVO) session.getAttribute("member");
		
		if (memberData == null) {
		    return "/spaceHub/sign?cmd=login";  // 로그인 페이지로 리다이렉트 또는 포워드
		}
		
		session.setAttribute("member", memberData);
		
		if(memberData != null) {
			int memno = memberData.getMemno();
			SpaceDAO dao = new SpaceDAO();
			SpaceImageDAO imgDao = new SpaceImageDAO();
			
			ArrayList<JjimListVO> list = dao.getJjimList(memno);
			
			for(JjimListVO jjimData : list) {
				int spaceno = jjimData.getSpaceno();
				
				
				jjimData.setImgList(imgDao.getSpaceImages(spaceno, memno));
			}
			
			session.setAttribute("list", list);
			imgDao.close();
			dao.close();
			
		}
		return "/mypage/host/hostMain.jsp";
	}
	
}