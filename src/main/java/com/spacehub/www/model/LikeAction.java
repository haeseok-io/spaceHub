package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.JjimDAO;
import com.spacehub.www.dao.SpaceImageDAO;
import com.spacehub.www.vo.JjimListVO;
import com.spacehub.www.vo.SmemberVO;
import com.spacehub.www.vo.SpaceImageVO;

public class LikeAction implements Action{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		
		// 세션에 저장된 로그인 정보 가져오기
		SmemberVO memberData = (SmemberVO) session.getAttribute("member");
		
		// 로그인 정보가 세션에 없으면 로그인 페이지로 이동
		if (memberData == null) {
		    return "/spaceHub/sign?cmd=login";  // 로그인 페이지로 리다이렉트 또는 포워드
		}

		if (memberData != null) {
		    int memno = memberData.getMemno();  // 로그인된 사용자의 memno 가져오기
		    JjimDAO jjimDao = new JjimDAO();
		    SpaceImageDAO spaceImgDao = new SpaceImageDAO();
		    ArrayList<JjimListVO> jjimList = new ArrayList<JjimListVO>();
		    
		    for(JjimListVO vo : jjimDao.getJjimList(memno)) {
		    	
		    	ArrayList<SpaceImageVO> imgList = spaceImgDao.getSpaceImages(vo.getSpaceno());
		 
		    	vo.setImgList(imgList);
		    	
		    	jjimList.add(vo);
		    	
		    }
		    jjimDao.close();
		    spaceImgDao.close();
		    
		    req.setAttribute("jjimList", jjimList);
		}

		return "/mypage/likeList.jsp";
	}
	
}
