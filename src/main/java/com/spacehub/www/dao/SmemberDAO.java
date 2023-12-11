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
	
	// 1건 조회
		public SmemberVO getOne(int memno) {
			sb.setLength(0);
			sb.append("select memno,email,password,name,post,addr,account_num,regdate,credits,status from smember where memno=?");
			SmemberVO vo = null;
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, memno);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					String email = rs.getString("email");
					String password = rs.getString("password");
					String name = rs.getString("name");
					String post = rs.getString("post");
					String addr = rs.getString("addr");
					String accountNum = rs.getString("account_num");
					String regdate = rs.getString("regdate");
					int credits = rs.getInt("credits");
					int status = rs.getInt("status");
					
					vo = new SmemberVO(memno, email, password, name, post, addr, accountNum, regdate, credits, status);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return vo;
		}
		
		public SmemberVO getOne(String email) {
			sb.setLength(0);
			sb.append("select memno,email,password,name,post,addr,account_num,regdate,credits,status from xe.smember where email=?");
			SmemberVO vo = null;
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, email);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int memno = rs.getInt("memno");
					String password = rs.getString("password");
					String name = rs.getString("name");
					String post = rs.getString("post");
					String addr = rs.getString("addr");
					String accountNum = rs.getString("account_num");
					String regdate = rs.getString("regdate");
					int credits = rs.getInt("credits");
					int status = rs.getInt("status");
					
					vo = new SmemberVO(memno, email, password, name, post, addr, accountNum, regdate, credits, status);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return vo;
		}
		
		public SmemberVO getOne(String email, String password) {
			sb.setLength(0);
			sb.append("select memno,email,password,name,post,addr,account_num,regdate,credits,status from xe.smember where email=? and password=?");
			SmemberVO vo = null;
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, email);
				pstmt.setString(2, password);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int memno = rs.getInt("memno");
					String name = rs.getString("name");
					String post = rs.getString("post");
					String addr = rs.getString("addr");
					String accountNum = rs.getString("account_num");
					String regdate = rs.getString("regdate");
					int credits = rs.getInt("credits");
					int status = rs.getInt("status");
					
					vo = new SmemberVO(memno, email, password, name, post, addr, accountNum, regdate, credits, status);
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
			sb.append("insert into xe.smember(email, password, name, post, addr, account_num, regdate, credits, status) ");
			sb.append("values(?, ?, ?, ?, ?, ?, now(), ?, '1')");
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, vo.getEmail());
				pstmt.setString(2, vo.getPassword());
				pstmt.setString(3, vo.getName());
				pstmt.setString(4, vo.getPost());
				pstmt.setString(5, vo.getAddr());
				pstmt.setString(6, vo.getAccountNum());
				pstmt.setInt(7, vo.getCredits());
				
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
	
	// 비밀번호 변경
	public void modifyPw(SmemberVO vo) {
		sb.setLength(0);
		sb.append("update smember ");
		sb.append("set password=? ");
		sb.append("where memno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getPassword());
			pstmt.setInt(2, vo.getMemno());
			
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
