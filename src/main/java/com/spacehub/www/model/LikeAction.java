package com.spacehub.www.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.dao.SpaceImageDAO;
import com.spacehub.www.vo.JjimListVO;
import com.spacehub.www.vo.SmemberVO;

public class LikeAction implements Action{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession(true);
		
		// 세션에 저장된 로그인 정보 가져오기
		SmemberVO memberData = (SmemberVO) session.getAttribute("member");
		
		// 로그인 정보가 세션에 없으면 로그인 페이지로 이동
		if (memberData == null) {
		    return "/spaceHub/sign?cmd=login";  // 로그인 페이지로 리다이렉트 또는 포워드
		}

		// 세션에 로그인 정보 저장
		session.setAttribute("member", memberData);

		if (memberData != null) {
		    int memno = memberData.getMemno();  // 로그인된 사용자의 memno 가져오기
		    SpaceDAO dao = new SpaceDAO();
		    SpaceImageDAO imgDao = new SpaceImageDAO();

		    ArrayList<JjimListVO> list = dao.getJjimList(memno);

		    for (JjimListVO jjimData : list) {
		        int spaceno = jjimData.getSpaceno();
		        
		        jjimData.setImgList(imgDao.getSpaceImages(spaceno, memno));
		    }
		    // 세션에 좋아요 목록 저장
		    session.setAttribute("likeList", list);
		    dao.close();
		}

		return "/mypage/likeList.jsp";
	}
	
}
