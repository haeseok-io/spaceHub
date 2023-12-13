package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.MessageContentsVO;
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
	
	// 본인의 메시지 리스트 가져오기
	// - 본인한테 온 메시지와 본인이 올린 공간에 온 메시지 모두 추출
	// - 최근 작성자 정보, 최초 작성자 정보, 호스트 정보 추출
	public ArrayList<MessageListVO> getList(int memno) {
		ArrayList<MessageListVO> list = new ArrayList<MessageListVO>();
		
		sb.setLength(0);
		sb.append("Select m.messageno, m.bno, m.contents, m.regdate, m.status, m.ip, m.spaceno, m.memno, hs.subject as space_name, ");
		sb.append("Case When s.memno=hm.memno Then 1 Else 0 End As space_own_status, ");
		sb.append("mm.memno as mmemno, mm.name as mname, mm.nickname as mnickname, mm.profile_img as mprofile_img, ");
		sb.append("wm.memno as wmemno, wm.name as wname, wm.nickname as wnickname, wm.profile_img as wprofile_img, ");
		sb.append("hm.memno as hmemno, hm.name as hname, hm.nickname as hnickname, hm.profile_img as hprofile_img ");
		sb.append("From message m ");
		sb.append("Join space s On s.memno=? ");
		sb.append("Join space ms On ms.memno=m.memno ");
		sb.append("Join smember mm On mm.memno=m.memno ");
		sb.append("Join smember wm On wm.memno=(Select memno From message Where bno=m.bno order by messageno asc limit 1) ");
		sb.append("Join space hs On hs.spaceno=m.spaceno ");
		sb.append("Join smember hm On hm.memno=hs.memno ");
		sb.append("Where (s.spaceno=m.spaceno or wm.memno=?) ");
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
				data.setSpaceName(rs.getString("space_name"));
				data.setSpaceOwnStatus(rs.getInt("space_own_status"));
				
				data.setMmemno(rs.getInt("mmemno"));
				data.setMname(rs.getString("mname"));
				data.setMnickname(rs.getString("mnickname"));
				data.setMprofileImg(rs.getString("mprofile_img"));
				
				data.setWmemno(rs.getInt("wmemno"));
				data.setWname(rs.getString("wname"));
				data.setWnickname(rs.getString("wnickname"));
				data.setWprofileImg(rs.getString("wprofile_img"));
				
				data.setHmemno(rs.getInt("hmemno"));
				data.setHname(rs.getString("hname"));
				data.setHnickname(rs.getString("hnickname"));
				data.setHprofileImg(rs.getString("hprofile_img"));
				
				list.add(data);
			}
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return list;
	}
	
	// 메세지 리스트 가져오기
	public ArrayList<MessageContentsVO> getContents(int bno) {
		ArrayList<MessageContentsVO> list = new ArrayList<MessageContentsVO>();
		
		sb.setLength(0);
		sb.append("Select m.messageno, m.bno, m.contents, m.regdate, m.status, m.ip, m.spaceno, m.memno, s.name, s.nickname, s.profile_img ");
		sb.append("From message m, smember s ");
		sb.append("Where m.memno=s.memno And bno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				list.add(new MessageContentsVO(
					rs.getInt("messageno"),
					rs.getInt("bno"),
					rs.getString("contents"),
					rs.getString("regdate"),
					rs.getInt("status"),
					rs.getString("ip"),
					rs.getInt("spaceno"),
					rs.getInt("memno"),
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
	
	// 메시지 추가하기
	public void addOne(MessageVO vo) {
		sb.setLength(0);
		sb.append("Insert Into message (bno, contents, regdate, status, ip, spaceno, memno) ");
		sb.append("Values (?, ?, now(), 1, ?, ?, ?)");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, vo.getBno());
			pstmt.setString(2, vo.getContents());
			pstmt.setString(3, vo.getIp());
			pstmt.setInt(4, vo.getSpaceno());
			pstmt.setInt(5, vo.getMemno());
			
			pstmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 회원이 문의한 bno 값 추출
	public int getMyMessageBno(int spaceno, int memno) {
		int bno = 0;
		
		sb.setLength(0);
		sb.append("SELECT bno ");
		sb.append("FROM message m ");
		sb.append("WHERE spaceno=? AND memno=? ");
		sb.append("And messageno In (SELECT MIN(messageno) FROM message WHERE spaceno=m.spaceno GROUP BY bno) ");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, spaceno);
			pstmt.setInt(2, memno);
			rs = pstmt.executeQuery();
			
			if( rs.next() ) {
				bno = rs.getInt("bno");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return bno;
	}
	
	// 상대방이 보낸 메시지 읽음 처리
	// - bno 게시물중 memno 가 일치하지 않는값 변경 (상대방이 보낸 메시지)
	public void readMessage(int bno, int memno) {
		sb.setLength(0);
		sb.append("Update message ");
		sb.append("Set status=2 ");
		sb.append("Where bno=? And memno!=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, bno);
			pstmt.setInt(2, memno);
			
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 다음 bno 리턴
	public int getNextBno() {
		int lastBno = 0;
		
		sb.setLength(0);
		sb.append("Select max(bno) as max_bno From message");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			if( rs.next() ) {
				lastBno = rs.getInt("max_bno")+1;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return lastBno;
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
