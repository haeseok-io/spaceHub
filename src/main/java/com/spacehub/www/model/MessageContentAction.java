package com.spacehub.www.model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.spacehub.www.dao.MessageDAO;
import com.spacehub.www.dao.SmemberDAO;
import com.spacehub.www.vo.MessageVO;
import com.spacehub.www.vo.SmemberVO;

public class MessageContentAction implements JsonAction {

	@Override
	public JSONObject execute(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject jsonObject = new JSONObject();
		String bnoParam = req.getParameter("bno");
		
		if( bnoParam==null || bnoParam.equals("") ) {
			jsonObject.put("errorCode", "param empty");
			jsonObject.put("errorMsg", "게시물 고유번호가 존재하지 않습니다");
			return jsonObject;
		}
		
		
		Integer bno = Integer.parseInt(bnoParam);
		MessageDAO messageDAO = new MessageDAO();
		SmemberDAO smemberDAO = new SmemberDAO();
		
		ArrayList<HashMap> contentList = new ArrayList<HashMap>();
		for(MessageVO data : messageDAO.getContents(bno)) {
			
			// 메시지 회원 정보
			SmemberVO smemberData = smemberDAO.getOne(data.getMemno());
			
			
			HashMap<String, String> obj = new HashMap<String, String>();
			obj.put("messageno", ""+data.getMessageno());
			obj.put("bno", ""+data.getBno());
			obj.put("contents", data.getContents());
			obj.put("regdate", data.getRegdate());
			obj.put("status", ""+data.getStatus());
			obj.put("ip", data.getIp());
			obj.put("spaceno", ""+data.getSpaceno());
			obj.put("memno", ""+data.getMemno());
			obj.put("wname", smemberData.getName());
			
			contentList.add(obj);
		}
		
		smemberDAO.close();
		messageDAO.close();
		
		
		jsonObject.put("data", contentList);
		return jsonObject;
	}

}
