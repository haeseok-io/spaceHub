package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spacehub.www.dao.SmemberDAO;
import com.spacehub.www.vo.SmemberVO;

public class ModifyOkAction implements Action{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		
		SmemberVO memberData = (SmemberVO) session.getAttribute("member");
		
		if(memberData != null) {
			int memno = memberData.getMemno();
			String post = req.getParameter("post");
			String addr = req.getParameter("addr");
			String accountNum = req.getParameter("accountNum");
			
			SmemberDAO dao = new SmemberDAO();
			SmemberVO vo = new SmemberVO();
			
			vo.setMemno(memno);
			vo.setPost(post);
			vo.setAddr(addr);
			vo.setAccountNum(accountNum);
			
			dao.modifyOne(vo);
			dao.close();
	        
	        memberData.setAccountNum(accountNum);
	        memberData.setPost(post);
	        memberData.setAddr(addr);
	        
	        req.setAttribute("showAlert", true);
	        req.setAttribute("alertMessage", "회원 정보 수정이 완료되었습니다.");
		}
		return "/mypage/modifyForm.jsp";
	}

}
