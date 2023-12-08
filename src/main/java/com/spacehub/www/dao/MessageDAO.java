package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.MessageListVO;
import com.spacehub.www.vo.MessageVO;

public class MessageDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	public MessageDAO() {
		conn = DBConnection.getConnection();
	}
	
	// 본인의 메시지 리스트 가져오기 (호스트, 게스트 모두)
	public ArrayList<MessageListVO> getList(int memno) {
		ArrayList<MessageListVO> list = new ArrayList<MessageListVO>();
		
		sb.setLength(0);
		sb.append("Select m.messageno, m.bno, m.contents, m.regdate, m.status, m.ip, m.spaceno, m.memno, ");
		sb.append("Case When m.memno=s.memno then 1 else 0 end as own_status ");
		sb.append("From message m ");
		sb.append("Join space s On s.memno=? ");
		sb.append("Where (m.spaceno=s.spaceno or m.memno=?) ");
		sb.append("And m.messageno=(Select max(messageno) From message Where bno=m.bno) ");
		sb.append("Group By m.bno ");
		sb.append("Order By m.messageno DESC");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, memno);
			pstmt.setInt(2, memno);
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				MessageListVO data = new MessageListVO();
				data.setMessageno(rs.getInt("messageno"));
				data.setBno(rs.getInt("bno"));
				data.setContents(rs.getString("contents"));
				data.setRegdate(rs.getString("regdate"));
				data.setStatus(rs.getInt("status"));
				data.setSpaceno(rs.getInt("spaceno"));
				data.setMemno(rs.getInt("memno"));
				data.setOwnStatus(rs.getInt("own_status"));
				
				list.add(data);
			}
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return list;
	}
	
	// 메세지 리스트 가져오기
	public ArrayList<MessageVO> getContents(int bno) {
		ArrayList<MessageVO> list = new ArrayList<MessageVO>();
		
		sb.setLength(0);
		sb.append("Select * From message Where bno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				list.add(new MessageVO(
					rs.getInt("messageno"),
					rs.getInt("bno"),
					rs.getString("contents"),
					rs.getString("regdate"),
					rs.getInt("status"),
					rs.getString("ip"),
					rs.getInt("spaceno"),
					rs.getInt("memno")
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
	
}//class end
