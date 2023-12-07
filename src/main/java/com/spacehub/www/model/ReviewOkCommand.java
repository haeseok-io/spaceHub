package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.ReservationDAO;
import com.spacehub.www.dao.ReviewDAO;
import com.spacehub.www.vo.ReservationVO;
import com.spacehub.www.vo.ReviewVO;

public class ReviewOkCommand implements ActionCommand {

	private String $REMOTE_ADDR;

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		String subject = req.getParameter("title");
		String contents = req.getParameter("contents");
		String r = req.getParameter("rating");
		String re = req.getParameter("reservno");
		String ip = req.getRemoteAddr();
		
		if(subject != null || contents != null || r != null || re != null) {
			
			int rating = Integer.parseInt(r);
			int reservno = Integer.parseInt(re);
			
			ReservationDAO rdao = new ReservationDAO();
			ReservationVO rvo = rdao.getOne(reservno);
			
			ReviewVO vo = new ReviewVO();
			vo.setSubject(subject);
			vo.setContents(contents);
			vo.setRating(rating);
			vo.setIp(ip);
			vo.setMemno(rvo.getMemno());
			vo.setReservno(reservno);
			
			ReviewDAO dao = new ReviewDAO();
			dao.addOne(vo);
			dao.close();
		}
		
		return "/review?cmd=reviewList";
	}

}
