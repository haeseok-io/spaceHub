package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.CreditsVO;
import com.spacehub.www.vo.DiscountVO;

public class CreditsDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private StringBuffer sb = new StringBuffer();
	
	public CreditsDAO() {
		conn = DBConnection.getConnection();
	}
	// 전체조회
	public ArrayList<CreditsVO> getAll() {
		ArrayList<CreditsVO> list = new ArrayList<CreditsVO>();
		sb.setLength(0);
		sb.append("select creditsno, contents, price, date, memno from credits ");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int creditsno = rs.getInt("creditsno");
				String contents = rs.getString("contents");
				int price = rs.getInt("price");
				String date = rs.getString("date");
				int memno = rs.getInt("memno");
				CreditsVO vo = new CreditsVO(creditsno, contents, price, date, memno);
				list.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	// 회원 정보를 통한 크레딧 조회
	public ArrayList<CreditsVO> getAll(int memno) {
		ArrayList<CreditsVO> list = new ArrayList<CreditsVO>();
		sb.setLength(0);
		sb.append("select creditsno, contents, price, date, credits.memno from credits ");
		sb.append("where memno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int creditsno = rs.getInt("creditsno");
				String contents = rs.getString("contents");
				int price = rs.getInt("price");
				String date = rs.getString("date");
				CreditsVO vo = new CreditsVO(creditsno, contents, price, date, memno);
				list.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	// 회원 정보를 통한 크레딧 합 조회
		public CreditsVO getSum(int memno) {
			CreditsVO data = new CreditsVO();
			sb.setLength(0);
			sb.append("SELECT creditsno, contents, sum(price) as price, date, memno FROM credits ");
			sb.append("where memno=?");
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, memno);
				rs = pstmt.executeQuery();
				
				if( rs.next() ) {
					data.setCreditsno(rs.getInt("creditsno"));
					data.setContents(rs.getString("contents"));
					data.setPrice(rs.getInt("price"));
					data.setDate(rs.getString("date"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return data;
		}
	// 추가
	public void addOne(CreditsVO vo) {
		sb.setLength(0);
		sb.append("insert into credits(contents, price, date, memno) ");
		sb.append("values(?, ?, now(), ?)" );
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getContents());
			pstmt.setInt(2, vo.getPrice());
			pstmt.setInt(3, vo.getMemno());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// 수정
	public void modifyOne(CreditsVO vo) {
		sb.setLength(0);
		sb.append("update credits ");
		sb.append("set contents=?, price=? ");
		sb.append("where memno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getContents());
			pstmt.setInt(2, vo.getPrice());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	// 삭제
	public void deleteOne(int memno) {
		sb.setLength(0);
		sb.append("delete from credits ");
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
