package com.spacehub.www.model;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.DiscountDAO;
import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.dao.SpaceDetailDAO;
import com.spacehub.www.dao.SpaceFacDAO;
import com.spacehub.www.dao.SpaceImageDAO;
import com.spacehub.www.vo.DiscountVO;
import com.spacehub.www.vo.SmemberVO;
import com.spacehub.www.vo.SpaceDetailVO;
import com.spacehub.www.vo.SpaceFacVO;
import com.spacehub.www.vo.SpaceVO;


public class SpaceModifyAction implements Action {
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
			
			SpaceVO spaceVo = spaceDao.getOne(Integer.parseInt(spaceno));
			//System.out.println(spaceVo);
			
			SpaceDetailVO spaceDetailVo = spaceDetailDao.getOne(Integer.parseInt(spaceno));
			SpaceFacVO spaceFacVo = spaceFacDao.getOne(Integer.parseInt(spaceno));
			//ArrayList<HostSpaceImageVO> list = spaceImageDao.getSpaceImages(Integer.parseInt(spaceno),Integer.parseInt(memno));
			ArrayList<DiscountVO> dcList = discountDao.getOne(Integer.parseInt(spaceno));
			
			req.setAttribute("spaceVo", spaceVo);
			req.setAttribute("spaceDetailVo", spaceDetailVo);
			req.setAttribute("spaceFacVo", spaceFacVo);
			//req.setAttribute("list", list);
			req.setAttribute("dcList", dcList);
			
			spaceDao.close();
			spaceDetailDao.close();
			spaceImageDao.close();
			spaceFacDao.close();
			discountDao.close();
		}
		return "/space/spaceModifyForm.jsp";
	}
}
