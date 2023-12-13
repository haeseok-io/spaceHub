package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.ReviewListVO;
import com.spacehub.www.vo.ReviewSpaceVO;
import com.spacehub.www.vo.ReviewVO;

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
		sb.append("Select rv.reviewno, rv.subject, rv.contents, rv.rating, rv.regdate, s.name, s.nickname, s.profile_img ");
		sb.append("From review rv, reservation r, smember s ");
		sb.append("Where rv.reservno=r.reservno And r.memno=s.memno And r.spaceno=?");
		
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
					rs.getString("name"),
					rs.getString("nickname"),
					rs.getString("profile_img")
				));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<ReviewVO> getAll(int memno){
		ArrayList<ReviewVO> list = new ArrayList<ReviewVO>();
		sb.setLength(0);
		sb.append("select reviewno, subject, contents, rating, regdate, status, ip, memno, reservno ");
		sb.append("from review where memno=?");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(new ReviewVO(
						rs.getInt("reviewno"), rs.getString("subject"), rs.getString("contents"),
						rs.getInt("rating"), rs.getString("regdate"), rs.getInt("status"),
						rs.getString("ip"), rs.getInt("memno"), rs.getInt("reservno")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<ReviewSpaceVO> getSall(int memno){
		ArrayList<ReviewSpaceVO> list = new ArrayList<ReviewSpaceVO>();
		sb.setLength(0);
		sb.append("SELECT r.reviewno, r.subject, r.contents, r.rating, r.regdate, r.status, r.ip, r.memno, r.reservno, v.spaceno, s.subject as spacename FROM review r, reservation v, space s WHERE r.reservno=v.reservno AND v.spaceno=s.spaceno AND r.memno=?;");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(new ReviewSpaceVO(
						rs.getInt("reviewno"), rs.getString("subject"), rs.getString("contents"),
						rs.getInt("rating"), rs.getString("regdate"), rs.getInt("status"),
						rs.getString("ip"), rs.getInt("memno"), rs.getInt("reservno"), rs.getInt("spaceno"), rs.getString("spacename")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//하나 가져오기
	public ReviewVO getOne(int reviewno) {
		ReviewVO data = null;
		sb.setLength(0);
		sb.append("select reviewno, subject, contents, rating, regdate, status, ip, memno, reservno ");
		sb.append("from review where reviewno = ? ");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, reviewno);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				data = (new ReviewVO(
						rs.getInt("reviewno"), rs.getString("subject"), rs.getString("contents"),
						rs.getInt("rating"), rs.getString("regdate"), rs.getInt("status"),
						rs.getString("ip"), rs.getInt("memno"), rs.getInt("reservno")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	// 추가
	public void addOne(ReviewVO vo) {
		sb.setLength(0);
		sb.append("insert into review(subject, contents, rating, regdate, status, ip, memno, reservno) ");
		sb.append("values(?,?,?,now(),1,?,?,?) ");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getSubject());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getRating());
			pstmt.setString(4, vo.getIp());
			pstmt.setInt(5, vo.getMemno());
			pstmt.setInt(6, vo.getReservno());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 공간 번호에 대한 후기 갯수 가져오기
	public Integer getSpaceTotal(int spaceno) {
		Integer total = 0;
		
		sb.setLength(0);
		sb.append("Select count(*) as total ");
		sb.append("From reservation r, review rv ");
		sb.append("Where r.reservno=rv.reservno And r.spaceno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, spaceno);
			rs = pstmt.executeQuery();
			
			if( rs.next() ) {
				total = rs.getInt("total");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return total;
	}
	
	// 공간 번호에 대한 평균후기 점수 가져오기
	public float getAvgRating(int spaceno) {
		float avgRating = 0;
		
		sb.setLength(0);
		sb.append("Select avg(rv.rating) as rating ");
		sb.append("From reservation r, review rv ");
		sb.append("Where r.reservno=rv.reservno And r.spaceno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, spaceno);
			rs = pstmt.executeQuery();
			
			if( rs.next() ) {
				avgRating = rs.getFloat("rating");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return avgRating;
	}
	
	// 종료
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
