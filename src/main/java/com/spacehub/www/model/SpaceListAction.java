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
import com.spacehub.www.dao.SpaceImageDAO;
import com.spacehub.www.vo.SmemberVO;
import com.spacehub.www.vo.SpaceImageVO;
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
		SpaceImageDAO spaceImageDAO = new SpaceImageDAO();
		JjimDAO jjimDAO = new JjimDAO();
		
		HttpSession session = req.getSession();
		
		String page = req.getParameter("page");
		String scale = req.getParameter("scale");
		
		// Check
		if( maxGuestParam!=null && !maxGuestParam.equals("") ) {
			maxGuest = Integer.parseInt(maxGuestParam);
		}
		
		if( page==null || page.equals("") ) 	page = "1";
		if( scale==null || scale.equals("") ) 	scale = "10";
		
		// Data
		// - 페이징
		int currentNum = Integer.parseInt(page); 	// 현재 페이지
		int scaleNum = Integer.parseInt(scale); 	// 노출 갯수
		int startNum = ((currentNum-1)*scaleNum); 	// 노출 시작점
		int endNum = currentNum*scaleNum; 			// 노출 종료점
		
		int totalCount = spaceDAO.getTotalCount(subjectParam, inDateParam, outDateParam, maxGuest);
		int seenCount = currentNum*scaleNum; 		// 노출된 데이터 갯수		
		int remainCount = totalCount-seenCount; 	// 남은 데이터 갯수
		
		if( totalCount<=0 ) 		seenCount = 0;
		if( seenCount>=totalCount )	seenCount = totalCount;
		if( remainCount<=0 ) 		remainCount = 0;
		
		HashMap<String, Integer> pageInfo = new HashMap<String, Integer>();
		pageInfo.put("totalCount", totalCount);
		pageInfo.put("seenCount", seenCount);
		pageInfo.put("remainCount", remainCount);
		
		// - 회원 정보
		SmemberVO memberData = (SmemberVO)session.getAttribute("member");
		
		// - 공간 정보
		ArrayList<HashMap<String, Object>> spaceList = new ArrayList<HashMap<String, Object>>();
		DecimalFormat formatter = new DecimalFormat("###,###");
		
		for(SpaceListVO data : spaceDAO.getList(startNum, endNum, subjectParam, inDateParam, outDateParam, maxGuest)) {
			
			// 이미지 리스트
			ArrayList<String> imgList = new ArrayList<String>();
			for(SpaceImageVO imgData : spaceImageDAO.getSpaceImages(data.getSpaceno())) {
				imgList.add(imgData.getPath());
			}
			
			// 찜 여부
			String jjimStatus = "N";
			if( memberData!=null ) {
				jjimStatus = jjimDAO.checkUser(data.getSpaceno(), memberData.getMemno());
			}
			data.setJjimStatus(jjimStatus);
			
			// 가공
			data.setPriceFormat(formatter.format(data.getPrice()));
			
			// 배열 담기
			HashMap<String, Object> obj = new HashMap<String, Object>();
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
			obj.put("imgList", imgList);
			obj.put("rating", ""+data.getRating());
			obj.put("userJjimStatus", data.getJjimStatus());
			
			spaceList.add(obj);
		}

		// Etc
		spaceDAO.close();
		spaceImageDAO.close();
		jjimDAO.close();
		
		// Result
		jsonObject.put("data", spaceList);
		jsonObject.put("paging", pageInfo);
		return jsonObject;
	}
	
	
}
