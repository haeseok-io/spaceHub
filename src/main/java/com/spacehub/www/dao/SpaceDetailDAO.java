package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.SpaceDetailVO;

public class SpaceDetailDAO {
	Connection conn;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	private StringBuffer sb = new StringBuffer();

	public SpaceDetailDAO() {
		conn = DBConnection.getConnection();
	}

	public SpaceDetailVO getOne(int spaceno) {
		sb.setLength(0);
		sb.append(
				"SELECT spaceno, type, detail, max_guest, bed, bathroom, in_date, out_date, x, y FROM space_detail WHERE spaceno=?");
		SpaceDetailVO vo = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, spaceno);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String type = rs.getString("type");
				String detail = rs.getString("detail");
				int max_guest = rs.getInt("max_guest");
				int bed = rs.getInt("bed");
				int bathroom = rs.getInt("bathroom");
				String in_date = rs.getString("in_date");
				String out_date = rs.getString("out_date");
				String x = rs.getString("x");
				String y = rs.getString("y");
				vo = new SpaceDetailVO(spaceno, type, detail, max_guest, bed, bathroom, in_date, out_date, x, y);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
	
	// 데이터 추가
		public void addOne(SpaceDetailVO vo) {
			sb.setLength(0);
			sb.setLength(0);
			sb.append(
					"INSERT INTO space_detail (spaceno, type, detail, max_guest, bed, bathroom, in_date, out_date, x, y) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, vo.getSpaceno());
				pstmt.setString(2, vo.getType());
				pstmt.setString(3, vo.getDetail());
				pstmt.setInt(4, vo.getMaxGuest());
				pstmt.setInt(5, vo.getBed());
				pstmt.setInt(6, vo.getBathroom());
				pstmt.setString(7, vo.getInDate());
				pstmt.setString(8, vo.getOutDate());
				pstmt.setString(9, vo.getX());
				pstmt.setString(10, vo.getY());
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
