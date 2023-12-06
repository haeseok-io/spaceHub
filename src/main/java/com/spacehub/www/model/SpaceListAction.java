package com.spacehub.www.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.spacehub.www.dao.JjimDAO;
import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.vo.SmemberVO;
import com.spacehub.www.vo.SpaceListVO;

public class SpaceListAction implements JsonAction {

	@Override
	public JSONObject execute(HttpServletRequest req, HttpServletResponse resp) {
		// Val
		JSONObject jsonObject = new JSONObject();

		String subjectParam = req.getParameter("subject");
		String inDateParam = req.getParameter("in_date");
		String outDateParam = req.getParameter("out_date");
		String maxGuestParam = req.getParameter("max_guest");
		int maxGuest = 0;
		
		SpaceDAO spaceDAO = new SpaceDAO();
		JjimDAO jjimDAO = new JjimDAO();
		
		HttpSession session = req.getSession();
		DecimalFormat formatter = new DecimalFormat("###,###");
		
		String page = req.getParameter("page");
		String scale = req.getParameter("scale");
		String pageScale = req.getParameter("page_scale");
		
		// Check
		if( maxGuestParam!=null && !maxGuestParam.equals("") ) {
			maxGuest = Integer.parseInt(maxGuestParam);
		}
		
		int currentNum = 1;
		int scaleNum = 10;
		int pageScaleNum = 10;
		
		if( page!=null && !page.equals("") )			currentNum = Integer.parseInt(page);
		if( scale!=null && !scale.equals("") ) 			scaleNum = Integer.parseInt(scale);
		if( pageScale!=null && !pageScale.equals("") )	pageScaleNum = Integer.parseInt(pageScale);
		
		int totalCount = spaceDAO.getTotalCount(subjectParam, inDateParam, outDateParam, maxGuest);
		int totalPage = totalCount%scaleNum==0 ? totalCount/scaleNum : totalCount/scaleNum+1;
		
		int startNum = ((currentNum-1)*scaleNum);
		int endNum = currentNum*scaleNum;
		
		// Data
		// - 회원 정보
		SmemberVO memberData = (SmemberVO)session.getAttribute("member");
		
		// - 가공
		ArrayList<HashMap<String, String>> spaceList = new ArrayList<HashMap<String, String>>();
		for(SpaceListVO data : spaceDAO.getList(startNum, endNum, subjectParam, inDateParam, outDateParam, maxGuest)) {
			
			// 회원 찜
			String jjimStatus = "N";
			if( memberData!=null ) {
				jjimStatus = jjimDAO.checkUser(data.getSpaceno(), memberData.getMemno());
			}
			data.setJjimStatus(jjimStatus);
			
			// 가공
			data.setPriceFormat(formatter.format(data.getPrice()));
			
			// 배열 담기
			HashMap<String, String> obj = new HashMap<String, String>();
			obj.put("spaceno", ""+data.getSpaceno());
			obj.put("loc", data.getLoc());
			obj.put("subject", data.getSubject());
			obj.put("addr", data.getAddr());
			obj.put("price", ""+data.getPrice());
			obj.put("priceFormat", ""+data.getPriceFormat());
			obj.put("memno", ""+data.getMemno());
			obj.put("inDate", ""+data.getInDate());
			obj.put("outDate", ""+data.getOutDate());
			obj.put("path", ""+data.getPath());
			obj.put("rating", ""+data.getRating());
			obj.put("userJjimStatus", data.getJjimStatus());
			
			spaceList.add(obj);
		}

		// Etc
		spaceDAO.close();
		jjimDAO.close();
		
		// Result
		jsonObject.put("data", spaceList);
		return jsonObject;
	}
	
	
}
