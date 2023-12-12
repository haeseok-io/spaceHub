package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.dao.SpaceImageDAO;
import com.spacehub.www.vo.HostMainVO;
import com.spacehub.www.vo.SmemberVO;
import com.spacehub.www.vo.SpaceImageVO;

public class HostMainAction implements Action{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession(true);
		
		SmemberVO memberData = (SmemberVO) session.getAttribute("member");
		
		
		if (memberData == null) {
		    return "/spaceHub/sign?cmd=login";  // 로그인 페이지로 리다이렉트 또는 포워드
		}
		
		if (memberData != null) {
			int memno = memberData.getMemno();
			SpaceDAO hostDao = new SpaceDAO();
			SpaceImageDAO spaceImgDao = new SpaceImageDAO();
			
			ArrayList<HostMainVO> hostMainList = new ArrayList<HostMainVO>();
			
			for(HostMainVO vo : hostDao.getHostSpace(memno)) {
				ArrayList<SpaceImageVO> imgList = spaceImgDao.getSpaceImages(vo.getSpaceno());
				
				vo.setImgList(imgList);
				
				hostMainList.add(vo);
				
			}
			hostDao.close();
			spaceImgDao.close();
			
			req.setAttribute("hostList", hostMainList);
		}
		return "/mypage/host/hostMain.jsp";
	}
	
}
