package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.spacehub.www.dao.MessageDAO;
import com.spacehub.www.vo.MessageVO;
import com.spacehub.www.vo.SmemberVO;

public class MessageWriteOkAction implements JsonAction {

	@Override
	public JSONObject execute(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject jsonObject = new JSONObject();
		String bnoParam = req.getParameter("bno");
		String spacenoParam = req.getParameter("spaceno");
		String contentParam = req.getParameter("contents");
		
		// Check
		if( bnoParam==null || bnoParam.equals("") || spacenoParam==null || spacenoParam.equals("") ) {
			jsonObject.put("errorCode", "param empty");
			jsonObject.put("errorMsg", "필수 전달 값이 누락되었습니다.");
			return jsonObject;
		}
		if( contentParam==null || contentParam.equals("") ) {
			jsonObject.put("errorCode", "contents empty");
			jsonObject.put("errorMsg", "메시지 내용이 없습니다.");
			return jsonObject;
		}
		
		// Data
		HttpSession session = req.getSession();
		SmemberVO smemberData = (SmemberVO)session.getAttribute("member");
		MessageDAO messageDAO = new MessageDAO();
		MessageVO messageData = new MessageVO();
		
		messageData.setBno(Integer.parseInt(bnoParam));
		messageData.setContents(contentParam);
		messageData.setIp(req.getRemoteAddr());
		messageData.setSpaceno(Integer.parseInt(spacenoParam));
		messageData.setMemno(smemberData.getMemno());
		
		// Process
		messageDAO.addOne(messageData);
		messageDAO.close();
		
		// Result
		return jsonObject;
	}

}
