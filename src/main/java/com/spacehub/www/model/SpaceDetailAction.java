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
		String s = req.getParameter("spaceno");
		
		// Check
		if( s==null || s.equals("") ) {
			return null;
		}
		
		// Data
		Integer spaceno = Integer.parseInt(s);
		SpaceDAO spaceDAO = new SpaceDAO();
		SmemberDAO smemberDAO = new SmemberDAO();
		SpaceImageDAO imageDAO = new SpaceImageDAO();
		SpaceDetailDAO spaceDetailDAO = new SpaceDetailDAO();
		SpaceFacDAO spaceFacDAO = new SpaceFacDAO();
		ReviewDAO reviewDAO = new ReviewDAO();
		
		// - 기본 정보
		SpaceVO spaceData = spaceDAO.getOne(spaceno);
		
		// - 호스트 정보
		SmemberVO hostData = smemberDAO.getOne(spaceData.getMemno());
		
		// - 상세정보
		SpaceDetailVO detailData = spaceDetailDAO.getOne(spaceno);
		
		// - 편의시설
		SpaceFacVO factoryData = spaceFacDAO.getOne(spaceno);
		
		// - 이미지
		ArrayList<SpaceImageVO> imageList = imageDAO.getSpaceImages(spaceno);
		
		// - 리뷰
		ArrayList<ReviewListVO> reviewList = reviewDAO.getSpaceAll(spaceno);
		
		
		
		
		spaceDAO.close();
		spaceDetailDAO.close();
		imageDAO.close();
		spaceFacDAO.close();
		spaceDetailDAO.close();
		spaceFacDAO.close();
		reviewDAO.close();
		
		
		// Result
		req.setAttribute("data", spaceData);
		req.setAttribute("host", hostData);
		req.setAttribute("detail", detailData);
		req.setAttribute("factory", factoryData);
		req.setAttribute("images", imageList);
		req.setAttribute("review", reviewList);
		return "/space/detail.jsp";
	}
	
}
