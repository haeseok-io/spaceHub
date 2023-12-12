package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.spacehub.www.dao.DiscountDAO;
import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.dao.SpaceDetailDAO;
import com.spacehub.www.dao.SpaceFacDAO;
import com.spacehub.www.dao.SpaceImageDAO;
import com.spacehub.www.vo.SpaceDetailVO;
import com.spacehub.www.vo.SpaceFacVO;
import com.spacehub.www.vo.SpaceVO;

public class SpaceModifyOkAction implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		SpaceDAO spaceDao = new SpaceDAO();
		SpaceDetailDAO spcaeDetailDao = new SpaceDetailDAO();
		SpaceImageDAO spcaeImageDao = new SpaceImageDAO();
		SpaceFacDAO spcaeFacDao = new SpaceFacDAO();
		DiscountDAO discountDao = new DiscountDAO();
		SpaceVO spaceVo = new SpaceVO();
		SpaceDetailVO spaceDetailVo = new SpaceDetailVO();
		SpaceFacVO spaceFacVo = new SpaceFacVO();
		
		
		
		
		
		
		
		return null;
	}

}
