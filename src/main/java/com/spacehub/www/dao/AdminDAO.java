package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.AdminVO;
import com.spacehub.www.vo.SmemberVO;

public class AdminDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	
	public AdminDAO() {
		conn = DBConnection.getConnection();
	}
	
	// 전체 조회
		public ArrayList<AdminVO> getAll() {
			ArrayList<AdminVO> list = new ArrayList<AdminVO>();
			
			sb.setLength(0);
			sb.append("Select * From amember");
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				rs = pstmt.executeQuery();
				
				if( rs.next() ) {
					list.add(new AdminVO(
						rs.getInt("adminno"),
						rs.getString("id"),
						rs.getString("pw"),
						rs.getString("name"),
						rs.getString("duty"),
						rs.getString("regdate")
					));
				}
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			return list;
		}
		
		// 아이디 비밀번호로 정보 조회
		public AdminVO getOne(String id, String pw) {
			AdminVO vo = null;

			sb.setLength(0);
			sb.append("Select adminno, id, pw, name, duty, regdate ");
			sb.append("From amember ");
			sb.append("Where id=? and pw=?");
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, id);
				pstmt.setString(2, pw);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int adminno = rs.getInt("adminno");
					String name = rs.getString("name");
					String duty = rs.getString("duty");
					String regdate = rs.getString("regdate");
					
					vo = new AdminVO(adminno, id, pw, name, duty, regdate);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return vo;
		}
		
		// 삭제
		public void deleteOne(String id) {
			sb.setLength(0);
			sb.append("delete from amember ");
			sb.append("where id=?");
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, id);
				
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
