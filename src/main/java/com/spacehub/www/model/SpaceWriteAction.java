package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.vo.SmemberVO;

public class SpaceWriteAction implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session =  req.getSession(true);
		SmemberVO memberData = (SmemberVO) session.getAttribute("member");
		
		if (memberData == null) {
		    return "/sign?cmd=login";  // 로그인 페이지로 리다이렉트 또는 포워드
		}
		
		session.setAttribute("member", memberData);
		
		return "space/write.jsp";
	}
	
}
