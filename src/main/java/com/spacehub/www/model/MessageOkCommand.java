package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.MessageDAO;
import com.spacehub.www.vo.MessageVO;
import com.spacehub.www.vo.ReservMessageVO;
import com.spacehub.www.vo.ResevSpaceVO;
import com.spacehub.www.vo.SmemberVO;

public class MessageOkCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String mes = req.getParameter("mes");
		String s = req.getParameter("spaceno");
		String ip = req.getRemoteAddr();
		String r = req.getParameter("reservno");
		
		if(mes != null || s != null || r != null) {
			int spaceno = Integer.parseInt(s);
			int reservno = Integer.parseInt(r);

			HttpSession session = req.getSession();
			SmemberVO memberVO = (SmemberVO)session.getAttribute("vo");
			
			MessageDAO dao = new MessageDAO();
			ArrayList<ReservMessageVO> list = dao.getMC(reservno);
			
			ArrayList<MessageVO> mlist = dao.getAllBno();
			
			int bno = 0;
			
			for(ReservMessageVO vo : list) {
				if(vo.getBno() != 0) {
					bno = vo.getBno();
				}else {
					for(MessageVO mvo : mlist) {
						int a = mvo.getBno();
						bno = a+1;
					}
				}
			}
			
			MessageVO vo = new MessageVO();
			vo.setBno(bno);
			vo.setContents(mes);
			vo.setIp(ip);
			vo.setSpaceno(spaceno);
			vo.setMemno(memberVO.getMemno());
		
			dao.addOne(vo);
			dao.close();
		}
		
		return "/message?cmd=message";
	}

}
