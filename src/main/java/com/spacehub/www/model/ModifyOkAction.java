package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.SmemberDAO;
import com.spacehub.www.vo.SmemberVO;

public class ModifyOkAction implements Action{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String m = req.getParameter("memno");
		
		if(m != null) {
			int memno = Integer.parseInt(m);
			String password = req.getParameter("password");
			String post = req.getParameter("post");
			String addr = req.getParameter("addr");
			String accountNum = req.getParameter("accountNum");
			
			SmemberDAO dao = new SmemberDAO();
			SmemberVO vo = new SmemberVO();
			
			vo.setMemno(memno);
			vo.setPassword(password);
			vo.setPost(post);
			vo.setAddr(addr);
			vo.setAccountNum(accountNum);
			
			dao.modifyOne(vo);
			dao.close();
		}
		return "/space?cmd=list";
	}

}
