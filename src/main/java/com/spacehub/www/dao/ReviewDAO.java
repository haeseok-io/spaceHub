package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.ReviewListVO;

public class ReviewDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	public ReviewDAO() {
		conn = DBConnection.getConnection();
	}
	
	// 공간에 대한 후기 가져오기
	public ArrayList<ReviewListVO> getSpaceAll(int spaceno) {
		ArrayList<ReviewListVO> list = new ArrayList<ReviewListVO>();
		
		sb.setLength(0);
		sb.append("Select rv.reviewno, rv.subject, rv.contents, rv.rating, rv.regdate, r.name ");
		sb.append("From review rv, reservation r ");
		sb.append("Where rv.reservno=r.reservno And r.spaceno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, spaceno);
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				list.add(new ReviewListVO(
					rs.getInt("reviewno"),
					rs.getString("subject"),
					rs.getString("contents"),
					rs.getInt("rating"),
					rs.getString("regdate"),
					rs.getString("name")
				));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	//종료
	public void close()	{
		try {
			if(conn!=null) conn.close();
			if(pstmt!=null) pstmt.close();
			if(rs!=null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}// class end
