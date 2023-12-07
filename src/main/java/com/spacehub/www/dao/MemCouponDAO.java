package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.MCouponVO;
import com.spacehub.www.vo.MemCouponVO;

public class MemCouponDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	
	public MemCouponDAO() {
		conn = DBConnection.getConnection();
	}
	
	// 전체 조회
	public ArrayList<MemCouponVO> getAll() {
		ArrayList<MemCouponVO> list = new ArrayList<MemCouponVO>();
		
		sb.setLength(0);
		sb.append("Select * From mem_coupon");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				list.add(new MemCouponVO(
					rs.getInt("memno"),
					rs.getInt("couponno"),
					rs.getString("c_date"),
					rs.getString("e_date"),
					rs.getInt("status"),
					rs.getInt("reservno")
				));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 회원 전체 조회
	public ArrayList<MemCouponVO> getAll(int memno) {
		ArrayList<MemCouponVO> list = new ArrayList<MemCouponVO>();
		
		sb.setLength(0);
		sb.append("Select * From mem_coupon where memno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, memno);
			
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				list.add(new MemCouponVO(
					rs.getInt("memno"),
					rs.getInt("couponno"),
					rs.getString("c_date"),
					rs.getString("e_date"),
					rs.getInt("status"),
					rs.getInt("reservno")
				));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<MCouponVO> getMem(int memno) {
		ArrayList<MCouponVO> list = new ArrayList<MCouponVO>();
		
		sb.setLength(0);
		sb.append("select m.memno, m.couponno, m.c_date, m.e_date, m.status, m.reservno, c.name, c.dcratio from coupon c, mem_coupon m where c.couponno=m.couponno and memno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, memno);
			
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				list.add(new MCouponVO(
					rs.getInt("memno"),
					rs.getInt("couponno"),
					rs.getString("c_date"),
					rs.getString("e_date"),
					rs.getInt("status"),
					rs.getInt("reservno"),
					rs.getString("name"),
					rs.getInt("dcratio")
				));
				System.out.println(list);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<MCouponVO> getCMem(int status, int memno) {
		ArrayList<MCouponVO> list = new ArrayList<MCouponVO>();
		
		sb.setLength(0);
		sb.append("select m.memno, m.couponno, m.c_date, m.e_date, m.status, m.reservno, c.name, c.dcratio from coupon c, mem_coupon m where c.couponno=m.couponno and m.status=? and memno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, status);
			pstmt.setInt(2, memno);
			
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				list.add(new MCouponVO(
					rs.getInt("memno"),
					rs.getInt("couponno"),
					rs.getString("c_date"),
					rs.getString("e_date"),
					rs.getInt("status"),
					rs.getInt("reservno"),
					rs.getString("name"),
					rs.getInt("dcratio")
				));
				System.out.println(list);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 추가
	public void addOne(MemCouponVO vo) {
		sb.setLength(0);
		sb.append("Insert Into mem_coupon ");
		sb.append("(memno, couponno, c_date, e_date, status, reservno) ");
		sb.append("Values (?, ?, now(), ?, 1, ?)");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, vo.getMemno());
			pstmt.setInt(2, vo.getCouponno());
			pstmt.setString(3, vo.getEDate());
			pstmt.setInt(4, vo.getReservno());
			
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 수정
	public void modifyOne(MemCouponVO vo) {
		sb.setLength(0);
		sb.append("Update mem_coupon Set ");
		sb.append("c_date=?, e_date=?, status=?, reservno=? ");
		sb.append("Where memno=? && couponno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getCDate());
			pstmt.setString(2, vo.getEDate());
			pstmt.setInt(3, vo.getStatus());
			pstmt.setInt(4, vo.getReservno());
			pstmt.setInt(5, vo.getMemno());
			pstmt.setInt(6, vo.getCouponno());
			
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 데이터 컬럼 수정
	public void modifyOnePart(int memno, int couponno, String field, String value) {
		sb.setLength(0);
		sb.append("Update payment Set ?=? Where memno=? && couponno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, field);
			pstmt.setString(2, value);
			pstmt.setInt(3, memno);
			pstmt.setInt(4, couponno);
			
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 데이터 삭제
	public void deleteOne(int memno, int couponno) {
		sb.setLength(0);
		sb.append("Delete From mem_coupon Where memno=? && couponno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, memno);
			pstmt.setInt(2, couponno);
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
