package com.spacehub.www.model;

import java.net.Inet4Address;
import java.net.UnknownHostException;

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
		
		HttpSession session = req.getSession();
		SmemberVO smemberData = (SmemberVO)session.getAttribute("member");
		
		// Check
		if( spacenoParam==null || spacenoParam.equals("") ) {
			jsonObject.put("errorCode", "param empty");
			jsonObject.put("errorMsg", "");
			return jsonObject;
		}
		if( contentParam==null || contentParam.equals("") ) {
			jsonObject.put("errorCode", "contents empty");
			jsonObject.put("errorMsg", "메시지 내용이 없습니다.");
			return jsonObject;
		}
		
		// - 로그인 정보가 없을 경우
		if( smemberData==null ) {
			jsonObject.put("errorCode", "member empty");
			jsonObject.put("errorMsg", "회원 로그인 후 작성 가능합니다.");
			return jsonObject;
		}
		
		// Data
		String ip = "";
		MessageDAO messageDAO = new MessageDAO();
		MessageVO messageData = new MessageVO();
		
		try {
			ip = Inet4Address.getLocalHost().getHostAddress();
			int spaceno = Integer.parseInt(spacenoParam);
			int memno = smemberData.getMemno();
			int bno = 0;
			
			// - bno 값이 없을 경우 spaceno 에 문의한 내역이 있는지 체크
			if( bnoParam==null || bnoParam.equals("") ) {
				bno = messageDAO.getMyMessageBno(spaceno, memno);
				if( bno==0 ) 	bno = messageDAO.getNextBno();
			} else {
				bno = Integer.parseInt(bnoParam);
			}
			
			messageData.setBno(bno);
			messageData.setContents(contentParam);
			messageData.setIp(ip);
			messageData.setSpaceno(spaceno);
			messageData.setMemno(memno);
			
			messageDAO.addOne(messageData);
			
		} catch (UnknownHostException e) {
			jsonObject.put("errorCode", "error");
			jsonObject.put("errorMsg", "메시지 작성 중 오류가 발생하였습니다.");
		} finally {
			messageDAO.close();
		}
		
		// Result
		return jsonObject;
	}

}
