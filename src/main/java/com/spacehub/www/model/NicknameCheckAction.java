package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.spacehub.www.dao.SmemberDAO;
import com.spacehub.www.vo.SmemberVO;

public class NicknameCheckAction implements JsonAction {

	@Override
	public JSONObject execute(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject jsonObject = new JSONObject();
		String nicknameParam = req.getParameter("nickname");
		
		// Check
		if( nicknameParam==null || nicknameParam.equals("") ) {
			jsonObject.put("errorCode", "param empty");
			jsonObject.put("errorMsg", "필수 전달 데이터가 없습니다.");
			return jsonObject;
		}
		
		// Data
		// - 중복 확인
		SmemberDAO smemberDAO = new SmemberDAO();
		SmemberVO smemberVO = smemberDAO.getNick(nicknameParam);
		smemberDAO.close();
		
		if( smemberVO!=null ) {
			jsonObject.put("errorCode", "email overlap");
			jsonObject.put("errorMsg", "이미 존재하는 닉네임 입니다.");
			return jsonObject;
		}
		
		// Result
		return jsonObject;
	}

}
