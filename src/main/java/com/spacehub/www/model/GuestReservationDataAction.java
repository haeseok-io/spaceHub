package com.spacehub.www.model;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacehub.www.dao.ReservationDAO;
import com.spacehub.www.vo.ReservationListVO;
import com.spacehub.www.vo.SmemberVO;

public class GuestReservationDataAction implements JsonAction {

	@Override
	public JSONObject execute(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject jsonObject = new JSONObject();
		HttpSession session = req.getSession();		
		
		
		SmemberVO memberData = (SmemberVO)session.getAttribute("member");
		String statusParam = req.getParameter("status");		
		ReservationDAO reserDAO = new ReservationDAO();
		
		// 예약 데이터 가져오기
		ArrayList<ReservationListVO> guestReservationData = null;
		if( statusParam==null || statusParam.equals("") ) {
			guestReservationData = reserDAO.getGuestAll(memberData.getMemno());
		} else {
			int status = Integer.parseInt(statusParam);
			guestReservationData = reserDAO.getGuestAll(memberData.getMemno(), status);
		}
		reserDAO.close();
		
		// 데이터 담기
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<Map<String, Object>> jsonData = new ArrayList<Map<String, Object>>();
		for(ReservationListVO vo : guestReservationData) {
			Map data = mapper.convertValue(vo, Map.class);
			jsonData.add(data);
		}
		
		jsonObject.put("data", jsonData);
		return jsonObject;
	}

}
