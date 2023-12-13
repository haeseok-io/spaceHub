package com.spacehub.www.model;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.spacehub.www.dao.SmemberDAO;
import com.spacehub.www.vo.SmemberVO;

public class EmailCheckAction implements JsonAction {

	@Override
	public JSONObject execute(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject jsonObject = new JSONObject();
		String emailParam = req.getParameter("email");
		
		// Check
		if( emailParam==null || emailParam.equals("") ) {
			jsonObject.put("errorCode", "param empty");
			jsonObject.put("errorMsg", "필수 전달 데이터가 없습니다.");
			return jsonObject;
		}
		
		// Data
		// - 중복 확인
		SmemberDAO smemberDAO = new SmemberDAO();
		SmemberVO smemberVO = smemberDAO.getOne(emailParam);
		smemberDAO.close();
		
		if( smemberVO!=null ) {
			jsonObject.put("errorCode", "email overlap");
			jsonObject.put("errorMsg", "이미 가입된 이메일 입니다.");
			return jsonObject;
		}
		
		// Result
		return jsonObject;
	}

}
