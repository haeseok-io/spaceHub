package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.spacehub.www.vo.SmemberVO;

public class SmemberDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private StringBuffer sb = new StringBuffer();
	
	public SmemberDAO() {
		conn = DBConnection.getConnection();
	}
	
	// 1건 조회
	public SmemberVO getOne(int memno) {
		SmemberVO vo = null;

		sb.setLength(0);
		sb.append("Select memno, email, name, post, addr, account_num, regdate, credits, status ");
		sb.append("From smember ");
		sb.append("Where memno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String email = rs.getString("email");
				String name = rs.getString("name");
				String post = rs.getString("post");
				String addr = rs.getString("addr");
				String accountNum = rs.getString("account_num");
				String regdate = rs.getString("regdate");
				int credits = rs.getInt("credits");
				int status = rs.getInt("status");
				
				vo = new SmemberVO(memno, email, null, name, post, addr, accountNum, regdate, credits, status);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
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
