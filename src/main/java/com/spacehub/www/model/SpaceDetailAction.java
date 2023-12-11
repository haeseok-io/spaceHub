package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.DiscountDAO;
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
		int reviewCount = reviewDAO.getSpaceTotal(spaceno);
		float avgRating = Math.round(reviewDAO.getAvgRating(spaceno)*100)/100.0f;
		ArrayList<ReviewListVO> reviewList = new ArrayList<ReviewListVO>();
		
		// 회원정보 가공
		if( hostData.getNickname()==null ) 		hostData.setNickname(hostData.getName());
		if( hostData.getProfileImg()==null )	hostData.setProfileImg("/spaceHub/upload/profile_empty.jpeg");
		
		// 리뷰 가공
		for(ReviewListVO reviewData : reviewDAO.getSpaceAll(spaceno)) {
			
			// 회원 프로필사진이 없을경우 대체이미지로 변경
			if( reviewData.getProfileImg()==null ) {
				reviewData.setProfileImg("/spaceHub/upload/profile_empty.jpeg");
			}
			
			// 닉네임이 없을경우 이름으로 대체
			if( reviewData.getNickname()==null ) {
				reviewData.setNickname(reviewData.getName());
			}
			
			reviewList.add(reviewData);
		}

		
		req.setAttribute("spaceData", spaceData);
		req.setAttribute("hostData", hostData);
		req.setAttribute("detailData", detailData);
		req.setAttribute("factoryData", factoryData);
		req.setAttribute("imageList", imageList);
		req.setAttribute("reviewList", reviewList);
		req.setAttribute("reviewCount", reviewCount);
		req.setAttribute("avgRating", avgRating);
		
		
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
