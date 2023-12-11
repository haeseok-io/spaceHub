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
		JSONObject data = new JSONObject();
		HttpSession session = req.getSession();

		String spacenoParam = req.getParameter("spaceno");
		SmemberVO memberData = (SmemberVO)session.getAttribute("member");
		
		// Check
		if( spacenoParam==null || spacenoParam.isEmpty() ) {
			data.put("errorCode", "param empty");
			data.put("errorMsg", "공간 번호가 존재하지 않습니다.");
			return data;
		}
		
		// - 회원일 경우에만 찜 등록 가능하도록
		if( memberData==null ) {
			data.put("errorCode", "member empty");
			data.put("errorMsg", "찜 기능은 로그인 후 가능합니다.");
			return data;
		}
		
		// Data
		int spaceno = Integer.parseInt(spacenoParam);
		
		// Process
		JjimDAO jjimDAO = new JjimDAO();
		jjimDAO.addOne(spaceno, memberData.getMemno());
		jjimDAO.close();
		
		// Result
		return data;
	}
	
}
