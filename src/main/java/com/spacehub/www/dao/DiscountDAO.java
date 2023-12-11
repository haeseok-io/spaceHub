package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.DiscountVO;

public class DiscountDAO {
	Connection conn;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	private StringBuffer sb = new StringBuffer();
	
	public DiscountDAO() {
		conn = DBConnection.getConnection();
	}
	
	
	// 공간 번호에 대한 할인 리스트 가져오기
	public ArrayList<DiscountVO> getSpaceAll(int spaceno) {
		ArrayList<DiscountVO> list = new ArrayList<DiscountVO>();
		
		sb.setLength(0);
		sb.append("Select * From discount Where spaceno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, spaceno);			
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				list.add(new DiscountVO(
					rs.getInt("disno"),
					rs.getString("name"),
					rs.getInt("dcratio"),
					rs.getInt("spaceno")
				));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	
	public void addOne(DiscountVO vo) {
		sb.setLength(0);
		sb.append("INSERT INTO discount (name, dcratio, spaceno) VALUES (?, ?, ?)");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getName());
			pstmt.setInt(2, vo.getDcratio());
			pstmt.setInt(3, vo.getSpaceno());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
