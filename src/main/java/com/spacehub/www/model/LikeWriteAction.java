package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.spacehub.www.dao.JjimDAO;

public class LikeWriteAction implements JsonAction {

	@Override
	public JSONObject execute(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject data = new JSONObject();
		String spacenoParam = req.getParameter("spaceno");
		
		// Check
		if( spacenoParam==null || spacenoParam.isEmpty() ) {
			data.put("error", "공간 번호가 존재하지 않습니다.");
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return data;
		}
		
		// Data
		int spaceno = Integer.parseInt(spacenoParam);
		
		// Process
		JjimDAO dao = new JjimDAO();
//		dao.addOne(spaceno, 1);
		dao.close();
		
		// Result
		resp.setStatus(HttpServletResponse.SC_OK);
		return data;
	}
	
}
