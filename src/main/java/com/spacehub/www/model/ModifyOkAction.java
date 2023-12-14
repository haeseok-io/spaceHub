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

			// 수정 성공 알림을 JSP에 전달
			req.setAttribute("showAlert", true);
			req.setAttribute("alertMessage", "수정이 성공적으로 완료되었습니다!");

	        memberData.setAccountNum(accountNum);
	        memberData.setPost(post);
	        memberData.setAddr(addr);
	        
	        dao.close();        
		}
		return "mypage/modifyForm.jsp";
	}
}
