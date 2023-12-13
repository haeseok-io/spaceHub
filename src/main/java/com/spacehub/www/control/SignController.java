package com.spacehub.www.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.spacehub.www.model.Action;
import com.spacehub.www.model.AuthOkCommand;
import com.spacehub.www.model.EmailCheckAction;
import com.spacehub.www.model.JsonAction;
import com.spacehub.www.model.LoginOkCommand;
import com.spacehub.www.model.LogoutCommand;
import com.spacehub.www.model.NicknameCheckAction;
import com.spacehub.www.model.PwfindOkCommand;
import com.spacehub.www.model.SignupOkCommand;

@WebServlet("/sign")
public class SignController extends HttpServlet{
	boolean isRedirect = false;
	JSONObject jsonObject = null;
	String url = "";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		String cmd = req.getParameter("cmd");
		
		// Init
		isRedirect = false;
		jsonObject = null;
		url = "";
		
		// Check
		if( cmd==null ) {
			cmd = "login";
		}
		
		// page
		if( cmd.equals("login") ) { // 로그인 페이지
			url = "/sign/login.jsp";
		} else if(cmd.equals("signup")) { // 회원가입 페이지
			url = "/sign/signup.jsp";
		} else if(cmd.equals("pwFind")) { // 비밀번호찾기 페이지
			url = "/sign/pwFind.jsp";
		}
		
		// Process
		if(cmd.equals("logoutOk")) { // 로그아웃 처리
			Action ac = new LogoutCommand();
			url = ac.execute(req, resp);
			isRedirect = true;
		} else if( cmd.equals("emailCheck") ) { // 이메일 중복확인
			JsonAction ac = new EmailCheckAction();
			jsonObject = ac.execute(req, resp);
		} else if( cmd.equals("nicknameCheck") ) { // 닉네임 중복확인
			JsonAction ac = new NicknameCheckAction();
			jsonObject = ac.execute(req, resp);
		}
		
		// Result
		doResult(req, resp);
	}
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		String cmd = req.getParameter("cmd");
		
		// Init
		isRedirect = true;
		jsonObject = null;
		url = "";
		
		// Check
		if( cmd==null ) 	url = null;
		
		// Data
		if(cmd.equals("loginOk")) { // 로그인
			Action ac = new LoginOkCommand();
			url = ac.execute(req, resp);
		} else if(cmd.equals("signupOk")) { // 회원가입
			Action ac = new SignupOkCommand();
			url = ac.execute(req, resp);
		} else if(cmd.equals("pwModifyOk")) { // 비밀번호 변경
			Action ac = new PwfindOkCommand();
			url = ac.execute(req, resp);
		} else if(cmd.equals("authOk")) {
			Action ac = new AuthOkCommand();
			url = ac.execute(req, resp);
		}
		
		// Result
		doResult(req, resp);
	}
	
	
	protected void doResult (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check
		// - url 이 null 로 리턴될 경우 메인페이지로 리다이렉트
		if( url==null ) {
			url = "/spaceHub/home";
			isRedirect = true;
		}
		
		// Result
		if( !url.equals("") ) {
			if( isRedirect ) {
				resp.sendRedirect(url);							
			} else {
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req, resp);
			}
		} else {
			resp.getWriter().print(jsonObject.toJSONString());
            resp.getWriter().flush();
		}
	}

}
