package com.spacehub.www.model;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.vo.SpaceVO;

public class spaceDetailDataAction implements JsonAction {

	@Override
	public JSONObject execute(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject jsonObject = new JSONObject();
		String spacenoParam = req.getParameter("spaceno");
		
		// Check
		if( spacenoParam==null || spacenoParam.equals("") ) {
			jsonObject.put("errorCode", "param empty");
			jsonObject.put("errorMsg", "필수 전달 데이터가 누락되었습니다.");
			return jsonObject;
		}
		
		// Data
		SpaceDAO spaceDAO = new SpaceDAO();
		SpaceVO spaceVO = spaceDAO.getOne(Integer.parseInt(spacenoParam));
		
		// Process
		HashMap<String, String> spaceData = new HashMap<String, String>();
		spaceData.put("spaceno", ""+spaceVO.getSpaceno());
		spaceData.put("imgPath", spaceVO.getImgPath());
		spaceData.put("subject", spaceVO.getSubject());
		spaceData.put("addr", spaceVO.getAddr());
		spaceData.put("price", ""+spaceVO.getPrice());
		
		// Result
		spaceDAO.close();
		jsonObject.put("data", spaceData);
		return jsonObject;
	}

}
