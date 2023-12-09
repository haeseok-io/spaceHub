package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.PaymentVO;

public class PaymentDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	
	public PaymentDAO() {
		conn = DBConnection.getConnection();
	}
	
	// 전체 조회
	public ArrayList<PaymentVO> getAll(int startNo, int endNo) {
		ArrayList<PaymentVO> list = new ArrayList<PaymentVO>();
		
		sb.setLength(0);
		sb.append("Select * From payment Order By pay_date DESC Limit "+startNo+", "+endNo);
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			if( rs.next() ) {
				list.add(new PaymentVO(
					rs.getInt("paymentno"),
					rs.getString("approval_num"),
					rs.getString("card_num"),
					rs.getString("name"),
					rs.getString("email"),
					rs.getInt("price"),
					rs.getString("pay_date"),
					rs.getInt("status"),
					rs.getInt("reservno")
				));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 단일 조회
	public PaymentVO getOne(int paymentno) {
		PaymentVO data = new PaymentVO();
		
		sb.setLength(0);
		sb.append("Select * From payment Where paymentno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, paymentno);
			rs = pstmt.executeQuery();
			
			if( rs.next() ) {
				data.setPaymentno(paymentno);
				data.setApprovalNum(rs.getString("approval_num"));
				data.setCardNum(rs.getString("card_num"));
				data.setName(rs.getString("name"));
				data.setEmail(rs.getString("email"));
				data.setPrice(rs.getInt("price"));
				data.setPayDate(rs.getString("pay_date"));
				data.setStatus(rs.getInt("status"));
				data.setReservno(rs.getInt("reservno"));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	// 데이터추가
	public void addOne(PaymentVO vo) {
		sb.setLength(0);
		sb.append("Insert Into payment ");
		sb.append("(approval_num, card_num, name, email, price, pay_date, status, reservno) ");
		sb.append("Values (?, ?, ?, ?, ?, now(), 1, ?)");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getApprovalNum());
			pstmt.setString(2, vo.getCardNum());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getEmail());
			pstmt.setInt(5, vo.getPrice());
			pstmt.setInt(6, vo.getReservno());
			
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 데이터 컬럼 수정
	public void modifyOnePart(int paymentno, String field, String value) {
		sb.setLength(0);
		sb.append("Update payment Set ?=? Where paymentno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, field);
			pstmt.setString(2, value);
			pstmt.setInt(3, paymentno);
			
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 데이터 삭제
	public void deleteOne(int paymentno) {
		sb.setLength(0);
		sb.append("Delete From payment Where paymentno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, paymentno);
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











