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

public class MessageListDataAction implements JsonAction {

	@Override
	public JSONObject execute(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject jsonObject = new JSONObject();
		MessageDAO messageDAO = new MessageDAO();;
		
		HttpSession session = req.getSession();
		SmemberVO memberData = (SmemberVO)session.getAttribute("member");
		ArrayList<HashMap<String, String>> messageList = new ArrayList<HashMap<String, String>>();
		
		for(MessageListVO data : messageDAO.getList(memberData.getMemno())) {

			// 회원 프로필 이미지 체크
			if( data.getMprofileImg()==null ) 	data.setMprofileImg("/spaceHub/upload/profile_empty.jpeg");
			if( data.getWprofileImg()==null ) 	data.setWprofileImg("/spaceHub/upload/profile_empty.jpeg");
			if( data.getHprofileImg()==null ) 	data.setHprofileImg("/spaceHub/upload/profile_empty.jpeg");
			
			// 데이터 담기
			HashMap<String, String> messageData = new HashMap<String, String>();
			
			messageData.put("messageno", ""+data.getMessageno());
			messageData.put("bno", ""+data.getBno());
			messageData.put("contents", data.getContents());
			messageData.put("regdate", data.getRegdate());
			messageData.put("status", ""+data.getStatus());
			
			messageData.put("spaceno", ""+data.getSpaceno());
			messageData.put("spaceName", ""+data.getSpaceName());
			messageData.put("spaceOwnStatus", ""+data.getSpaceOwnStatus());
			
			messageData.put("mmemno", ""+data.getMmemno());
			messageData.put("mname", data.getMname());
			messageData.put("mnickname", data.getMnickname());
			messageData.put("mprofileImg", data.getMprofileImg());
			
			messageData.put("wmemno", ""+data.getWmemno());
			messageData.put("wname", data.getWname());
			messageData.put("wnickname", data.getWnickname());
			messageData.put("wprofileImg", data.getWprofileImg());
			
			messageData.put("hmemno", ""+data.getHmemno());
			messageData.put("hname", data.getHname());
			messageData.put("hnickname", data.getHnickname());
			messageData.put("hprofileImg", data.getHprofileImg());
			
			messageList.add(messageData);
		}
		messageDAO.close();
		
		// Result
		jsonObject.put("data", messageList);
		return jsonObject;
	}

}
