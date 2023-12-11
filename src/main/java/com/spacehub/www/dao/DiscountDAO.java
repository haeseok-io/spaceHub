package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.CouponVO;
import com.spacehub.www.vo.DiscountVO;

public class DiscountDAO {
	Connection conn;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	private StringBuffer sb = new StringBuffer();
	
	public DiscountDAO() {
		conn = DBConnection.getConnection();
//		System.out.println("conn : "+ conn);
	}
	
	//방번호로 검색
		public ArrayList<DiscountVO> getOne(int spaceno) {
			ArrayList<DiscountVO> list = new ArrayList<DiscountVO>();
			
			sb.setLength(0);
			sb.append("SELECT * FROM discount WHERE spaceno=?");
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, spaceno);
				rs = pstmt.executeQuery();
				
				if( rs.next() ) {
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
		
		public DiscountVO getTwo(int spaceno) {
			DiscountVO data = new DiscountVO();
			
			sb.setLength(0);
			sb.append("SELECT * FROM discount WHERE dcratio=20 and spaceno=?");
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, spaceno);
				rs = pstmt.executeQuery();
				
				if( rs.next() ) {
					data.setDisno(rs.getInt("disno"));
					data.setName(rs.getString("name"));
					data.setDcratio(rs.getInt("dcratio"));
					data.setSpaceno(rs.getInt("spaceno"));
				}
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			return data;
		}
		
		public DiscountVO getWeek(int spaceno) {
			DiscountVO data = new DiscountVO();
			
			sb.setLength(0);
			sb.append("SELECT * FROM discount WHERE dcratio=10 and spaceno=?");
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, spaceno);
				rs = pstmt.executeQuery();
				
				if( rs.next() ) {
					data.setDisno(rs.getInt("disno"));
					data.setName(rs.getString("name"));
					data.setDcratio(rs.getInt("dcratio"));
					data.setSpaceno(rs.getInt("spaceno"));
				}
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			return data;
		}
		
		public DiscountVO getMonth(int spaceno) {
			DiscountVO data = new DiscountVO();
			
			sb.setLength(0);
			sb.append("SELECT * FROM discount WHERE dcratio=18 and spaceno=?");
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, spaceno);
				rs = pstmt.executeQuery();
				
				if( rs.next() ) {
					data.setDisno(rs.getInt("disno"));
					data.setName(rs.getString("name"));
					data.setDcratio(rs.getInt("dcratio"));
					data.setSpaceno(rs.getInt("spaceno"));
				}
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			return data;
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
