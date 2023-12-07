package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.ReviewDAO;
import com.spacehub.www.dao.SmemberDAO;
import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.dao.SpaceDetailDAO;
import com.spacehub.www.dao.SpaceFacDAO;
import com.spacehub.www.dao.SpaceImageDAO;
import com.spacehub.www.vo.ReviewListVO;
import com.spacehub.www.vo.SmemberVO;
import com.spacehub.www.vo.SpaceDetailVO;
import com.spacehub.www.vo.SpaceFacVO;
import com.spacehub.www.vo.SpaceImageVO;
import com.spacehub.www.vo.SpaceVO;

public class SpaceDetailAction implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		// Val
		String spacenoParam = req.getParameter("spaceno");
		if( spacenoParam==null || spacenoParam.equals("") ) {
			return null;
		}
		
		// Data
		Integer spaceno = Integer.parseInt(spacenoParam);
		SpaceDAO spaceDAO = new SpaceDAO();
		SmemberDAO smemberDAO = new SmemberDAO();
		SpaceDetailDAO spaceDetailDAO = new SpaceDetailDAO();
		SpaceFacDAO spaceFacDAO = new SpaceFacDAO();
		SpaceImageDAO imageDAO = new SpaceImageDAO();
		ReviewDAO reviewDAO = new ReviewDAO();
		
		// Process
		SpaceVO spaceData = spaceDAO.getOne(spaceno);
		SmemberVO hostData = smemberDAO.getOne(spaceData.getMemno());
		SpaceDetailVO detailData = spaceDetailDAO.getOne(spaceno);
		SpaceFacVO factoryData = spaceFacDAO.getOne(spaceno);
		ArrayList<SpaceImageVO> imageList = imageDAO.getSpaceImages(spaceno);
		ArrayList<ReviewListVO> reviewList = reviewDAO.getSpaceAll(spaceno);
		
		req.setAttribute("data", spaceData);
		req.setAttribute("host", hostData);
		req.setAttribute("detail", detailData);
		req.setAttribute("factory", factoryData);
		req.setAttribute("images", imageList);
		req.setAttribute("review", reviewList);
		
		
		spaceDAO.close();
		smemberDAO.close();
		spaceDetailDAO.close();
		spaceFacDAO.close();
		imageDAO.close();
		reviewDAO.close();
		
		// Result
		return "/space/detail.jsp";
	}
	
}
