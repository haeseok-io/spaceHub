package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.spacehub.www.dao.MessageDAO;
import com.spacehub.www.vo.SmemberVO;

public class MessageListAction implements JsonAction {

	@Override
	public JSONObject execute(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject data = new JSONObject();
		HttpSession session = req.getSession();
	
		MessageDAO messageDAO = new MessageDAO();

		SmemberVO memberData = (SmemberVO)session.getAttribute("member");
		
		
		
		messageDAO.close();
		
		data.put("test", "123");
		return data;
	}

}
