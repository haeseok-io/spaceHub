package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.MessageMemSpaceVO;
import com.spacehub.www.vo.MessageVO;
import com.spacehub.www.vo.ReservMessageVO;

public class MessageDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	public MessageDAO() {
		conn = DBConnection.getConnection();
	}
	
	//전체 가져오기
	public ArrayList<MessageVO> getAll(){
		ArrayList<MessageVO> list = new ArrayList<MessageVO>();
		sb.setLength(0);
		sb.append("select messageno, bno, contents, regdate, status, ip, spaceno, memno from message ");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(new MessageVO(
						rs.getInt("messageno"),rs.getInt("bno"), rs.getString("contents"),
						rs.getString("regdate"), rs.getInt("status"), rs.getString("ip"),
						rs.getInt("spaceno"), rs.getInt("memno")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
		public ArrayList<MessageVO> getAllBno(){
			ArrayList<MessageVO> list = new ArrayList<MessageVO>();
			sb.setLength(0);
			sb.append("select messageno, max(bno) as bno, contents, regdate, status, ip, spaceno, memno from message");
			try {
				pstmt = conn.prepareStatement(sb.toString());
				rs = pstmt.executeQuery();
				while(rs.next()) {
					list.add(new MessageVO(
							rs.getInt("messageno"),rs.getInt("bno"), rs.getString("contents"),
							rs.getString("regdate"), rs.getInt("status"), rs.getString("ip"),
							rs.getInt("spaceno"), rs.getInt("memno")
							));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
		}
	
	public ArrayList<MessageMemSpaceVO> getMsg(int reservno){
		ArrayList<MessageMemSpaceVO> list = new ArrayList<MessageMemSpaceVO>();
		sb.setLength(0);
		sb.append("select m.memno, m.name, e.regdate, e.bno, r.spaceno, s.subject, r.reservno from smember m, message e, space s, reservation r where r.spaceno=s.spaceno and s.memno=m.memno and r.spaceno=e.spaceno  and reservno=? order by e.regdate desc limit 1");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, reservno);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int memno = rs.getInt("memno");
				String name = rs.getString("name");
				String regdate = rs.getString("regdate");
				int bno = rs.getInt("bno");
				int spaceno = rs.getInt("spaceno");
				String subject = rs.getString("subject");
				MessageMemSpaceVO vo = new MessageMemSpaceVO(memno, name, regdate, bno, spaceno, subject, reservno);
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<ReservMessageVO> getMC(int reservno){
		ArrayList<ReservMessageVO> list = new ArrayList<ReservMessageVO>();
		sb.setLength(0);
		sb.append("select r.reservno, r.checkin, r.checkout, m.memno, r.phone, r.price, r.guest, r.dcratio, r.regdate, r.status, r.ip, r.spaceno,s.name, m.messageno, m.bno, m.contents from reservation r, message m, smember s where r.spaceno=m.spaceno and m.memno=s.memno and r.reservno=? order by m.regdate");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, reservno);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String checkin = rs.getString("checkin");
				String checkout = rs.getString("checkout");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				int price = rs.getInt("price");
				int guest = rs.getInt("guest");
				int dcratio = rs.getInt("dcratio");
				String regdate = rs.getString("regdate");
				int status = rs.getInt("status");
				String ip = rs.getString("ip");
				int spaceno = rs.getInt("spaceno");
				int memno = rs.getInt("memno");
				int messageno = rs.getInt("messageno");
				int bno = rs.getInt("bno");
				String contents = rs.getString("contents");
				ReservMessageVO vo = new ReservMessageVO(reservno, checkin, checkout, name, phone, price, guest, dcratio, regdate, status, ip, spaceno, memno, messageno, bno, contents);
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//추가
	public void addOne(MessageVO vo) {
		sb.setLength(0);
		sb.append("insert into message(bno, contents, regdate, status, ip, spaceno, memno) ");
		sb.append("values(?,?,now(),1,?,?,?) ");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, vo.getBno());
			pstmt.setString(2, vo.getContents());
			pstmt.setString(3, vo.getIp());
			pstmt.setInt(4, vo.getSpaceno());
			pstmt.setInt(5, vo.getMemno());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//삭제 
	public void deleteOne(int messageno) {
		sb.setLength(0);
		sb.append("delete from message where messageno = ? ");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, messageno);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
