package com.spacehub.www.model;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.vo.SmemberVO;

public class MessageListAction implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		SmemberVO smemberData = (SmemberVO)session.getAttribute("member");
		
		// 미로그인 상태일 경우 null 리턴
		if( smemberData==null ) {
			return null;
		}
		
		
		return "/message/list.jsp";
	}
	
}
