package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.JjimListVO;

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
	
	// 찜 리스트
	public ArrayList<JjimListVO> getJjimList(int memno) {
		ArrayList<JjimListVO> list = new ArrayList<JjimListVO>();

		sb.setLength(0);
		sb.append("select space.spaceno, space.subject, space.price, space_detail.bed, ");
		sb.append("space_detail.in_date, space_detail.out_date, jjim.jjimno ");
		sb.append("FROM jjim ");
		sb.append("JOIN space_detail ON space_detail.spaceno = jjim.spaceno ");
		sb.append("JOIN space ON space.spaceno = jjim.spaceno ");
		sb.append("WHERE jjim.memno=?");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {;
				String subject = rs.getString("space.subject");
				int spaceno = rs.getInt("space.spaceno");
				int price = rs.getInt("space.price");
				int bed = rs.getInt("space_detail.bed");
				String inDate = rs.getString("space_detail.in_date");
				String outDate = rs.getString("space_detail.out_date");
				int jjimno = rs.getInt("jjim.jjimno");
				
				JjimListVO vo = new JjimListVO(memno, subject, spaceno, bed, inDate, outDate, price, jjimno, null);
				list.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
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
