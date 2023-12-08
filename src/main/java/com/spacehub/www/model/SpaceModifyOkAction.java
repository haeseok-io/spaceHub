package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.dao.SpaceImageDAO;
import com.spacehub.www.vo.SpaceVO;

public class SpaceModifyOkAction implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		SpaceDAO spaceDao = new SpaceDAO();
		SpaceImageDAO spcaeImageDao = new SpaceImageDAO();
		SpaceVO spaceVo = new SpaceVO();

		return null;
	}

}
