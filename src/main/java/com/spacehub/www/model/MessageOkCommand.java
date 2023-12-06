package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MessageOkCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		return "message/messageList.jsp";
	}

}
