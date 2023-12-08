package com.spacehub.www.model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.spacehub.www.dao.MessageDAO;
import com.spacehub.www.vo.MessageListVO;
import com.spacehub.www.vo.SmemberVO;

public class MessageListAction implements JsonAction {

	@Override
	public JSONObject execute(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject result = new JSONObject();
		MessageDAO messageDAO = new MessageDAO();;
		
		HttpSession session = req.getSession();
		SmemberVO memberData = (SmemberVO)session.getAttribute("member");

		ArrayList<HashMap<String, String>> messageList = new ArrayList<HashMap<String, String>>();
		for(MessageListVO data : messageDAO.getList(memberData.getMemno())) {
			HashMap<String, String> messageData = new HashMap<String, String>();
			
			// 게시물 확인 상태
			
			
			// 데이터 담기
			messageData.put("messageno", ""+data.getMessageno());
			messageData.put("bno", ""+data.getBno());
			messageData.put("contents", data.getContents());
			messageData.put("regdate", data.getRegdate());
			messageData.put("status", ""+data.getStatus());
			messageData.put("spaceno", ""+data.getSpaceno());
			messageData.put("memno", ""+data.getMemno());
			messageData.put("ownStatus", ""+data.getOwnStatus());
			
			System.out.println(messageData);
		}
		
	
		
		
		
		messageDAO.close();
		
		result.put("test", "123");
		return result;
	}

}
