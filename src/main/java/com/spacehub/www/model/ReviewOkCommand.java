package com.spacehub.www.model;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.ReservationDAO;
import com.spacehub.www.dao.ReviewDAO;
import com.spacehub.www.vo.ReservationVO;
import com.spacehub.www.vo.ReviewVO;
import com.spacehub.www.vo.SmemberVO;

public class ReviewOkCommand implements ActionCommand {

	private String $REMOTE_ADDR;

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		String subject = req.getParameter("title");
		String contents = req.getParameter("contents");
		String r = req.getParameter("rating");
		String re = req.getParameter("reservno");
		HttpSession session = req.getSession();
		SmemberVO smemberData = (SmemberVO)session.getAttribute("member");
		
		// 미로그인 상태일 경우 null 리턴
		if( smemberData==null ) {
			return "/sign/login.jsp";
		}
		if(smemberData!=null || subject != null || contents != null || r != null || re != null) {
			
			int rating = Integer.parseInt(r);
			int reservno = Integer.parseInt(re);
			
			ReservationDAO rdao = new ReservationDAO();
			ReservationVO rvo = rdao.getOne(reservno);
			
			ReviewVO vo = new ReviewVO();
			vo.setSubject(subject);
			vo.setContents(contents);
			vo.setRating(rating);
			try {
				vo.setIp(
				 Inet4Address.getLocalHost().getHostAddress());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			vo.setMemno(rvo.getMemno());
			vo.setReservno(reservno);
			
			ReviewDAO dao = new ReviewDAO();
			dao.addOne(vo);
			dao.close();
			rdao.close();
		}
		
		return "/review?cmd=reviewList";
	}

}
