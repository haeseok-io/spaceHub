package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JjimDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private StringBuffer sb = new StringBuffer();
	
	public JjimDAO() {
		conn = DBConnection.getConnection();
	}
	
	// 찜 등록
	public void addOne(int spaceno, int memno) {
		sb.setLength(0);
		sb.append("Insert Into jjim (spaceno, memno) ");
		sb.append("Values (?, ?)");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, spaceno);
			pstmt.setInt(2, memno);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 찜 삭제
	public void deleteOne(int spaceno, int memno) {
		sb.setLength(0);
		sb.append("Delete From jjim Where spaceno=? And memno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, spaceno);
			pstmt.setInt(2, memno);
			
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 회원 찜 여부 확인
	public String checkUser(int spaceno, int memno) {
		String checkStatus = "N";
		
		sb.setLength(0);
		sb.append("Select jjimno From jjim Where spaceno=? And memno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, spaceno);
			pstmt.setInt(2, memno);
			rs = pstmt.executeQuery();
			
			if( rs.next() ) {
				checkStatus = "Y";
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return checkStatus;
	}
	
	// 종료
	public void close() {
		try {
			if(rs != null) 		rs.close();
			if(pstmt != null) 	pstmt.close();
			if(conn != null) 	conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
