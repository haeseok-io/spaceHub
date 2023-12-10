package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.CouponVO;
import com.spacehub.www.vo.PaymentVO;

public class CouponDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	
	public CouponDAO() {
		conn = DBConnection.getConnection();
	}
	
	// 전체 조회
	public ArrayList<CouponVO> getAll() {
		ArrayList<CouponVO> list = new ArrayList<CouponVO>();
		
		sb.setLength(0);
		sb.append("Select * From coupon Where 1");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			if( rs.next() ) {
				list.add(new CouponVO(
					rs.getInt("couponno"),
					rs.getString("name"),
					rs.getInt("dcratio")
				));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	//이름으로 검색
	public CouponVO getOne(String name) {
		CouponVO data = new CouponVO();
		
		sb.setLength(0);
		sb.append("Select couponno, name, dcratio From coupon Where name=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			if( rs.next() ) {
				data.setCouponno(rs.getInt("couponno"));
				data.setName(rs.getString("name"));
				data.setDcratio(rs.getInt("dcratio"));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	// 데이터 추가
	public void addOne(CouponVO vo) {
		sb.setLength(0);
		sb.append("Insert Into coupon (name, dcratio) Values (?, ?)");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getName());
			pstmt.setInt(2, vo.getDcratio());
			
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 데이터 수정
	public void modifyOne(CouponVO vo) {
		sb.setLength(0);
		sb.append("Update coupon Set name=?, dcratio=? Where couponno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getName());
			pstmt.setInt(2, vo.getDcratio());
			pstmt.setInt(3, vo.getCouponno());
			
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 데이터 삭제
	public void deleteOne(int couponno) {
		sb.setLength(0);
		sb.append("Delete From coupon Where couponno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, couponno);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 자원반납
	public void close() {
		try {
			if( rs!=null ) 		rs.close();
			if( pstmt!=null )	pstmt.close();
			if( conn!=null ) 	conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
