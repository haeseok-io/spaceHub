package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.MessageListVO;

public class MessageDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	public MessageDAO() {
		conn = DBConnection.getConnection();
	}
	
	// 회원 메세지 리스트 가져오기
	public ArrayList<MessageListVO> getMessageList(int memno) {
		ArrayList<MessageListVO> list = new ArrayList<MessageListVO>();
		
		sb.setLength(0);
		sb.append("Select m.messageno, m.bno, m.contents, m.regdate, m.status, m.ip, m.spaceno, m.memno ");
		
		
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
	
}//class end
