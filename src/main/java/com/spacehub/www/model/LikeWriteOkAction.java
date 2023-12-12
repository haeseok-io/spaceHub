package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.spacehub.www.dao.JjimDAO;
import com.spacehub.www.vo.SmemberVO;

public class LikeWriteOkAction implements JsonAction {

	@Override
	public JSONObject execute(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject jsonObject = new JSONObject();
		HttpSession session = req.getSession();

		String spacenoParam = req.getParameter("spaceno");
		SmemberVO memberData = (SmemberVO)session.getAttribute("member");
		
		// Check
		if( spacenoParam==null || spacenoParam.isEmpty() ) {
			jsonObject.put("errorCode", "param empty");
			jsonObject.put("errorMsg", "공간 번호가 존재하지 않습니다.");
			return jsonObject;
		}
		
		// - 회원일 경우에만 찜 등록 가능하도록
		if( memberData==null ) {
			jsonObject.put("errorCode", "member empty");
			jsonObject.put("errorMsg", "찜 기능은 로그인 후 가능합니다.");
			return jsonObject;
		}
		
		// Data
		int spaceno = Integer.parseInt(spacenoParam);
		JjimDAO jjimDAO = new JjimDAO();
		
		// Process
		jjimDAO.addOne(spaceno, memberData.getMemno());
		jjimDAO.close();
		
		// Result
		return jsonObject;
	}
	
}
