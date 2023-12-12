package com.spacehub.www.model;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.dao.SpaceImageDAO;
import com.spacehub.www.vo.HostSpaceImageVO;
import com.spacehub.www.vo.SmemberVO;
import com.spacehub.www.vo.SpaceImageVO;
import com.spacehub.www.vo.SpaceVO;

public class SpaceModifyAction implements Action {
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String memno = req.getParameter("memno");
		String spaceno = req.getParameter("spaceno");
		HttpSession session =  req.getSession();
		SmemberVO memberData = (SmemberVO) session.getAttribute("member");
		if(memno!=null && spaceno!=null && memberData!=null) {
			SpaceDAO spaceDao = new SpaceDAO();
			SpaceImageDAO spaceImageDao = new SpaceImageDAO();
			SpaceVO spaceVo = spaceDao.getOne(Integer.parseInt(spaceno));
			ArrayList<HostSpaceImageVO> list = spaceImageDao.getSpaceImages(Integer.parseInt(spaceno),Integer.parseInt(memno));
			req.setAttribute("spaceVo", spaceVo);
			req.setAttribute("list", list);
			session.setAttribute("member", memberData);
			spaceDao.close();
			spaceImageDao.close();
		}
		return "/space/spaceModifyForm.jsp";
	}
}
