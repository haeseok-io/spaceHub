package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.spacehub.www.vo.SmemberVO;

public class SmemberDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private StringBuffer sb = new StringBuffer();
	
	public SmemberDAO() {
		conn = DBConnection.getConnection();
	}
	
	// 회원번호로 정보 조회
	public SmemberVO getOne(int memno) {
		SmemberVO vo = null;

		sb.setLength(0);
		sb.append("Select memno, email, password, name, nickname, profile_img, post, addr, account_num, regdate, credits, status ");
		sb.append("From smember ");
		sb.append("Where memno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String email = rs.getString("email");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String nickname = rs.getString("nickname");
				String profileImg = rs.getString("profile_img");
				String post = rs.getString("post");
				String addr = rs.getString("addr");
				String accountNum = rs.getString("account_num");
				String regdate = rs.getString("regdate");
				int credits = rs.getInt("credits");
				int status = rs.getInt("status");
				
				vo = new SmemberVO(memno, email, password, name, nickname, profileImg, post, addr, accountNum, regdate, credits, status);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
	
	// 회원이메일로 정보 조회
	public SmemberVO getOne(String email) {
		SmemberVO vo = null;

		sb.setLength(0);
		sb.append("Select memno, email, password, name, nickname, profile_img, post, addr, account_num, regdate, credits, status ");
		sb.append("From smember ");
		sb.append("Where email=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int memno = rs.getInt("memno");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String nickname = rs.getString("nickname");
				String profileImg = rs.getString("profile_img");
				String post = rs.getString("post");
				String addr = rs.getString("addr");
				String accountNum = rs.getString("account_num");
				String regdate = rs.getString("regdate");
				int credits = rs.getInt("credits");
				int status = rs.getInt("status");
				
				vo = new SmemberVO(memno, email, password, name, nickname, profileImg, post, addr, accountNum, regdate, credits, status);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
	
	// 이메일과 비밀번호로 정보 조회
	public SmemberVO getOne(String email, String password) {
		SmemberVO vo = null;

		sb.setLength(0);
		sb.append("Select memno, email, password, name, nickname, profile_img, post, addr, account_num, regdate, credits, status ");
		sb.append("From smember ");
		sb.append("Where email=? and password=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int memno = rs.getInt("memno");
				String name = rs.getString("name");
				String nickname = rs.getString("nickname");
				String profileImg = rs.getString("profile_img");
				String post = rs.getString("post");
				String addr = rs.getString("addr");
				String accountNum = rs.getString("account_num");
				String regdate = rs.getString("regdate");
				int credits = rs.getInt("credits");
				int status = rs.getInt("status");
				
				vo = new SmemberVO(memno, email, password, name, nickname, profileImg, post, addr, accountNum, regdate, credits, status);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
	
	// 회원닉네임으로 정보 조회
		public SmemberVO getNick(String nickname) {
			SmemberVO vo = null;

			sb.setLength(0);
			sb.append("Select memno, email, password, name, nickname, profile_img, post, addr, account_num, regdate, credits, status ");
			sb.append("From smember ");
			sb.append("Where nickname=?");
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, nickname);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int memno = rs.getInt("memno");
					String password = rs.getString("password");
					String name = rs.getString("name");
					String email = rs.getString("email");
					String profileImg = rs.getString("profile_img");
					String post = rs.getString("post");
					String addr = rs.getString("addr");
					String accountNum = rs.getString("account_num");
					String regdate = rs.getString("regdate");
					int credits = rs.getInt("credits");
					int status = rs.getInt("status");
					
					vo = new SmemberVO(memno, email, password, name, nickname, profileImg, post, addr, accountNum, regdate, credits, status);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return vo;
		}
	
	// 추가
	public void addOne(SmemberVO vo) {
		sb.setLength(0);
		sb.append("insert into xe.smember(email, password, name, nickname, profile_img, post, addr, account_num, regdate, credits, status) ");
		sb.append("values(?, ?, ?, ?, null, ?, ?, ?, now(), ?, '1')");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, vo.getEmail());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getNickname());
			pstmt.setString(5, vo.getPost());
			pstmt.setString(6, vo.getAddr());
			pstmt.setString(7, vo.getAccountNum());
			pstmt.setInt(8, vo.getCredits());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 삭제
	public void deleteOne(int memno) {
		sb.setLength(0);
		sb.append("delete from smember ");
		sb.append("where memno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, memno);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 수정
	public void modifyOne(SmemberVO vo) {
		sb.setLength(0);
		sb.append("update smember ");
		sb.append("set password=?, post=?, addr=?, account_num=? ");
		sb.append("where memno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getPassword());
			pstmt.setString(2, vo.getPost());
			pstmt.setString(3, vo.getAddr());
			pstmt.setString(4, vo.getAccountNum());
			pstmt.setInt(5, vo.getMemno());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 종료
	public void close() {
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
