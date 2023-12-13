package com.spacehub.www.model;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.DiscountDAO;
import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.dao.SpaceDetailDAO;
import com.spacehub.www.dao.SpaceFacDAO;
import com.spacehub.www.dao.SpaceImageDAO;
import com.spacehub.www.vo.SmemberVO;

public class SpaceDeleteAction implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String memno = req.getParameter("memno");
		String spaceno = req.getParameter("spaceno");
		HttpSession session =  req.getSession(true);
		SmemberVO memberData = (SmemberVO) session.getAttribute("member");
		
		if (memberData == null) {
		    return "/spaceHub/sign?cmd=login";  // 로그인 페이지로 리다이렉트 또는 포워드
		}
		
		session.setAttribute("member", memberData);
		
		if(memno!=null && spaceno!=null && memberData!=null) {
			SpaceDAO spaceDao = new SpaceDAO();
			SpaceDetailDAO spaceDetailDao = new SpaceDetailDAO();
			SpaceImageDAO spaceImageDao = new SpaceImageDAO();
			SpaceFacDAO spaceFacDao = new SpaceFacDAO();
			DiscountDAO discountDao = new DiscountDAO();
			
			spaceDao.deleteOne(Integer.parseInt(spaceno));
			spaceDetailDao.deleteOne(Integer.parseInt(spaceno));
			spaceImageDao.deleteOne(Integer.parseInt(spaceno));
			spaceFacDao.deleteOne(Integer.parseInt(spaceno));
			discountDao.deleteOne(Integer.parseInt(spaceno));
			
			spaceDao.close();
			spaceDetailDao.close();
			spaceImageDao.close();
			spaceFacDao.close();
			discountDao.close();
		}
		return "/spaceHub/mypage/host?cmd=hostMain";
	}

}
