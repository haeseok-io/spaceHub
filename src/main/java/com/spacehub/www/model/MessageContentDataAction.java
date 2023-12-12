package com.spacehub.www.model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.spacehub.www.dao.MessageDAO;
import com.spacehub.www.vo.MessageContentsVO;
import com.spacehub.www.vo.SmemberVO;

public class MessageContentDataAction implements JsonAction {

	@Override
	public JSONObject execute(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject jsonObject = new JSONObject();
		String bnoParam = req.getParameter("bno");
		
		
		// Check
		if( bnoParam==null || bnoParam.equals("") ) {
			jsonObject.put("errorCode", "param empty");
			jsonObject.put("errorMsg", "게시물 고유번호가 존재하지 않습니다");
			return jsonObject;
		}
		
		// Data
		Integer bno = Integer.parseInt(bnoParam);
		
		// - 회원정보
		HttpSession session = req.getSession();
		SmemberVO smemberData = (SmemberVO)session.getAttribute("member");
		
		// Process
		MessageDAO messageDAO = new MessageDAO();
		
		// - 게시글 읽음 처리
		messageDAO.readMessage(bno, smemberData.getMemno());

		// - 본문 내용
		ArrayList<HashMap> contentList = new ArrayList<HashMap>();
		for(MessageContentsVO data : messageDAO.getContents(bno)) {
			if( data.getMnickname()==null ) 	data.setMnickname(data.getMname());
			if( data.getMprofileImg()==null ) 	data.setMprofileImg("/spaceHub/upload/profile_empty.jpeg");
			
			// {	
			//	bno: "",
			//	spaceno: "",
			//	data: {[
			//        0 : {
			//        	"memno" : "",
			//        	"nickname" : "",
			//        	"profileImg" : "",
			//        	"msgList" : {[
	        //	              0 : {messageno: "", contents: "", regdate: "", status: "", ip: ""},
	        //	              1 : {messageno: "", contents: "", regdate: "", status: "", ip: ""}
    	    //          	]}
			//        }, 
			//        1 : {
			//        	
			//        }
		    //    ]}
			// }
			
			
			
			// 데이터 담기
			HashMap<String, String> obj = new HashMap<String, String>();
			obj.put("messageno", ""+data.getMessageno());
			obj.put("bno", ""+data.getBno());
			obj.put("contents", data.getContents());
			obj.put("regdate", data.getRegdate());
			obj.put("status", ""+data.getStatus());
			obj.put("ip", data.getIp());
			obj.put("spaceno", ""+data.getSpaceno());
			obj.put("memno", ""+data.getMemno());
			obj.put("wnickname", data.getMnickname());
			obj.put("wprofileImg", data.getMprofileImg());
			contentList.add(obj);
		}
		messageDAO.close();
		
		// Result
		jsonObject.put("data", contentList);
		return jsonObject;
	}

}
