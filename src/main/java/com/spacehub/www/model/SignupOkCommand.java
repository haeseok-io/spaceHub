package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.SmemberDAO;
import com.spacehub.www.vo.SmemberVO;

public class SignupOkCommand implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		String email = req.getParameter("email");
		String pw = req.getParameter("pw");
		String name = req.getParameter("name");
		String post = req.getParameter("post");
		String a = req.getParameter("address");
		String da = req.getParameter("detailAddress");
		String ea = req.getParameter("extraAddress");
		String address = a+da+ea;
		String accountNum = req.getParameter("accountNum");
		
		if(email != null || pw != null || name != null || post != null || address != null) {
		
			SmemberVO vo = new SmemberVO();
			vo.setEmail(email);
			vo.setPassword(pw);
			vo.setName(name);
			vo.setPost(post);
			vo.setAddr(address);
			vo.setAccountNum(accountNum);
			vo.setCredits(0);
			
			SmemberDAO dao = new SmemberDAO();
			
			dao.addOne(vo);
			dao.close();
		
		}
		
		return "/sign/login.jsp";
	}

}
