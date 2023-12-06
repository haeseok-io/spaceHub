package com.spacehub.www.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.MessageDAO;
import com.spacehub.www.vo.MessageMemSpaceVO;

public class MessageListCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String r = req.getParameter("reservno");
		
		if(r != null) {
			int reservno = Integer.parseInt(r);
			MessageDAO dao = new MessageDAO();
			ArrayList<MessageMemSpaceVO> list = dao.getMsg(reservno);
			req.setAttribute("list", list);		
			dao.close();
		}
		
		return "message/messageList.jsp";
	}

}
