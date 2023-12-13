package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacehub.www.dao.SmemberDAO;
import com.spacehub.www.vo.SmemberVO;

public class SignupOkCommand implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String emailParam = req.getParameter("email");
		String passwordParam = req.getParameter("password");
		String nameParam = req.getParameter("name");
		String nicknameParam = req.getParameter("nickname");
		String postParam = req.getParameter("post");
		String addrParam = req.getParameter("addr");
		String addrDetailParam = req.getParameter("addr_detail");
		String bankNameParam = req.getParameter("bank_name");
		String accountNumParam = req.getParameter("account_num");
		
		// Check
		if( emailParam==null || passwordParam==null || nameParam==null || postParam==null || addrParam==null || addrDetailParam==null || emailParam.equals("") || passwordParam.equals("") || nameParam.equals("") || postParam.equals("") || addrParam.equals("") ) {
			return "/spaceHub/sign?cmd=signup";
		}

		// Data
		SmemberDAO smemberDAO = new SmemberDAO();
		SmemberVO smemberVO = new SmemberVO();
		
		// - 주소 가공
		if( addrDetailParam==null ) addrDetailParam = "";
		addrParam += " "+addrDetailParam;
		
		// - 데이터 담기
		smemberVO.setEmail(emailParam);
		smemberVO.setPassword(passwordParam);
		smemberVO.setName(nameParam);
		smemberVO.setNickname(nicknameParam);
		smemberVO.setPost(postParam);
		smemberVO.setAddr(addrParam);
		smemberVO.setBankName(bankNameParam);
		smemberVO.setAccountNum(accountNumParam);
		
		// Process
		smemberDAO.addOne(smemberVO);
		smemberDAO.close();
		
		// Result
		return "/spaceHub/sign";
	}

}
