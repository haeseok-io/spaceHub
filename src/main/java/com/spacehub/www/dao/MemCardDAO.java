package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.MemCardVO;
import com.spacehub.www.vo.SmemberVO;

public class MemCardDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private StringBuffer sb = new StringBuffer();
	
	public MemCardDAO() {
		conn = DBConnection.getConnection();
	}
	// 전체조회
	public ArrayList<MemCardVO> getAll() {
		ArrayList<MemCardVO> list = new ArrayList<MemCardVO>(); 
		sb.setLength(0);
		sb.append("select mcardno, card_num, e_date, cvc, post, loc, mem_card.memno from mem_card ");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int mcardno = rs.getInt("mcardno");
				String cardNum = rs.getString("card_num");
				String eDate = rs.getString("e_date");
				int cvc = rs.getInt("cvc");
				String post = rs.getString("post");
				String loc = rs.getString("loc");		
				int memno = rs.getInt("memno");
				MemCardVO vo = new MemCardVO(mcardno, cardNum, eDate, cvc, post, loc, memno);
				list.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	// 회원 번호를 통한 전체 조회
	public ArrayList<MemCardVO> getAll(int memno) {
		ArrayList<MemCardVO> list = new ArrayList<MemCardVO>(); 
		sb.setLength(0);
		sb.append("select mcardno, card_num, e_date, cvc, post, loc, memno from mem_card ");
		sb.append("where memno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int mcardno = rs.getInt("mcardno");
				String cardNum = rs.getString("card_num");
				String eDate = rs.getString("e_date");
				int cvc = rs.getInt("cvc");
				String post = rs.getString("post");
				String loc = rs.getString("loc");		
				MemCardVO vo = new MemCardVO(mcardno, cardNum, eDate, cvc, post, loc, memno);
				list.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	// 추가
	public void addOne(MemCardVO vo) {
		sb.setLength(0);
		sb.append("insert into mem_card(card_num, e_date, cvc, post, loc, memno) ");
		sb.append("values(?, ?, ?, ?, ?, ?)" );
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getCardNum());
			pstmt.setString(2, vo.getEDate());
			pstmt.setInt(3, vo.getCvc());
			pstmt.setString(4, vo.getPost());
			pstmt.setString(5, vo.getLoc());
			pstmt.setInt(6, vo.getMemno());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// 삭제
	public void deleteOne(int memno) {
		sb.setLength(0);
		sb.append("delete from mem_card ");
		sb.append("where memno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, memno);
			
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
