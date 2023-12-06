package com.spacehub.www.model;

import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.JjimDAO;
import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.vo.SpaceListVO;

public class SpaceListAction implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		SpaceDAO spaceDAO = new SpaceDAO();
		JjimDAO jjimDAO = new JjimDAO();
		
		String addr = req.getParameter("addr");
		String inDate = req.getParameter("in_date");
		String outDate = req.getParameter("out_date");
		String guest = req.getParameter("max_guest");
		int maxGuest = 0;
		DecimalFormat formatter = new DecimalFormat("###,###");
		
		String page = req.getParameter("page");
		String scale = req.getParameter("scale");
		String pageScale = req.getParameter("page_scale");
		
		// Check
		if( guest!=null ) {
			maxGuest = Integer.parseInt(guest);
		}
		
		int currentNum = 1;
		int scaleNum = 10;
		int pageScaleNum = 10;
		
		if( page!=null && !page.equals("") )			currentNum = Integer.parseInt(page);
		if( scale!=null && !scale.equals("") ) 			scaleNum = Integer.parseInt(scale);
		if( pageScale!=null && !pageScale.equals("") )	pageScaleNum = Integer.parseInt(pageScale);
		
		int totalCount = spaceDAO.getTotalCount(addr, inDate, outDate, maxGuest);
		int totalPage = totalCount%scaleNum==0 ? totalCount/scaleNum : totalCount/scaleNum+1;
		
		int startNum = ((currentNum-1)*scaleNum);
		int endNum = currentNum*scaleNum;
		
		// Data
		ArrayList<SpaceListVO> list = new ArrayList<SpaceListVO>();
		for(SpaceListVO data : spaceDAO.getList(startNum, endNum, addr, inDate, outDate, maxGuest)) {
			
			// 회원 찜
			data.setUserJjimStatus(jjimDAO.checkUser(data.getSpaceno(), 1));
			
			// 가공
			data.setPriceFormat(formatter.format(data.getPrice()));
			
			// 배열 담기
			list.add(data);
		}
		
		req.setAttribute("list", list);

		// Etc
		spaceDAO.close();
		jjimDAO.close();
		
		// Result
		return "/main.jsp";
	}
	
	
}
