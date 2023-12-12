package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.ReservationVO;
import com.spacehub.www.vo.ResevSpaceVO;
import com.spacehub.www.vo.SpaceDetailVO;
import com.spacehub.www.vo.SpaceHostVO;

public class ReservationDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();
	
	public ReservationDAO() {
		conn = DBConnection.getConnection();
	}
	
	public ArrayList<ReservationVO> getSpace(int spaceno){
		ArrayList<ReservationVO> list = new ArrayList<ReservationVO>();
		
		sb.setLength(0);
		sb.append("Select * From reservation where spaceno=? Order By checkin asc");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1,spaceno);
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				int reservno = rs.getInt("reservno");
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
				int memno = rs.getInt("memno");
				ReservationVO vo = new ReservationVO(reservno, checkin, checkout, name, phone, price, guest, dcratio, regdate, status, ip, spaceno, memno);
				list.add(vo);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ReservationVO getSpaceOne(int spaceno) {
			
			sb.setLength(0);
			sb.append("SELECT max(reservno) AS reservno, checkin, checkout, name, phone, price, guest, dcratio, regdate, status, ip, spaceno, memno From reservation where spaceno=?");
			ReservationVO vo = null;
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, spaceno);
				rs = pstmt.executeQuery();
				
				while( rs.next() ) {
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
					int reservno = rs.getInt("reservno");
					int memno = rs.getInt("memno");
					vo = new ReservationVO(reservno, checkin, checkout, name, phone, price, guest, dcratio, regdate, status, ip, spaceno, memno);
				}
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			return vo;
		}
	
	//상태1
	public ArrayList<ResevSpaceVO> getAll(int memno){
		ArrayList<ResevSpaceVO> list = new ArrayList<ResevSpaceVO>();
		
		sb.setLength(0);
		sb.append("select r.reservno, r.checkin, r.checkout, r.name, r.phone, r.price, r.guest, r.dcratio, r.regdate, r.status, r.ip, r.spaceno, r.memno, s.type, s.loc, s.subject, s.post, s.addr, i.path, i.seq from reservation r, space s, space_image i where r.spaceno=s.spaceno and s.spaceno=i.spaceno and checkout >= date(NOW()) and r.status=1 and r.memno=? and i.seq=1 order by checkin asc");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int reservno = rs.getInt("reservno");
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
				String type = rs.getString("type");
				String loc = rs.getString("loc");
				String subject = rs.getString("subject");
				String post = rs.getString("post");
				String addr = rs.getString("addr");
				String path = rs.getString("path");
				int seq = rs.getInt("seq");
				ResevSpaceVO vo = new ResevSpaceVO(reservno, checkin, checkout, name, phone, price,guest,dcratio,regdate,status,ip,spaceno,memno,type,loc,subject,post,addr,path,seq);
				list.add(vo);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	//상태4
	public ArrayList<ResevSpaceVO> getTwo(int memno){
		ArrayList<ResevSpaceVO> list = new ArrayList<ResevSpaceVO>();
		
		sb.setLength(0);
		sb.append("select r.reservno, r.checkin, r.checkout, r.name, r.phone, r.price, r.guest, r.dcratio, r.regdate, r.status, r.ip, r.spaceno, r.memno, s.type, s.loc, s.subject, s.post, s.addr, i.path, i.seq from reservation r, space s, space_image i where r.spaceno=s.spaceno and s.spaceno=i.spaceno and r.status=4 and r.memno=? and i.seq=1 order by checkin asc");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int reservno = rs.getInt("reservno");
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
				String type = rs.getString("type");
				String loc = rs.getString("loc");
				String subject = rs.getString("subject");
				String post = rs.getString("post");
				String addr = rs.getString("addr");
				String path = rs.getString("path");
				int seq = rs.getInt("seq");
				ResevSpaceVO vo = new ResevSpaceVO(reservno, checkin, checkout, name, phone, price,guest,dcratio,regdate,status,ip,spaceno,memno,type,loc,subject,post,addr,path,seq);
				list.add(vo);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<ResevSpaceVO> getEnd(int memno){
		ArrayList<ResevSpaceVO> list = new ArrayList<ResevSpaceVO>();
		
		sb.setLength(0);
		sb.append("select r.reservno, r.checkin, r.checkout, r.name, r.phone, r.price, r.guest, r.dcratio, r.regdate, r.status, r.ip, r.spaceno, r.memno, s.type, s.loc, s.subject, s.post, s.addr, i.path, i.seq from reservation r, space s, space_image i where r.spaceno=s.spaceno and s.spaceno=i.spaceno and r.status=1 and checkout < date(NOW()) and r.memno=? and i.seq=1 order by checkin asc");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int reservno = rs.getInt("reservno");
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
				String type = rs.getString("type");
				String loc = rs.getString("loc");
				String subject = rs.getString("subject");
				String post = rs.getString("post");
				String addr = rs.getString("addr");
				String path = rs.getString("path");
				int seq = rs.getInt("seq");
				ResevSpaceVO vo = new ResevSpaceVO(reservno, checkin, checkout, name, phone, price,guest,dcratio,regdate,status,ip,spaceno,memno,type,loc,subject,post,addr,path,seq);
				list.add(vo);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<SpaceHostVO> getHone(int reservno){
		ArrayList<SpaceHostVO> list = new ArrayList<SpaceHostVO>();
		
		sb.setLength(0);
		sb.append("select r.reservno, s.spaceno, s.subject, r.price, s.memno, m.name from reservation r, space s, smember m where r.spaceno=s.spaceno and s.memno=m.memno and r.reservno=?;");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, reservno);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int spaceno = rs.getInt("spaceno");
				String subject = rs.getString("subject");
				int price = rs.getInt("price");
				int memno = rs.getInt("memno");
				String name = rs.getString("name");
				SpaceHostVO vo = new SpaceHostVO(reservno, spaceno, subject, price, memno, name);
				list.add(vo);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 단일 조회
	public ReservationVO getOne(int reservno) {
		
		sb.setLength(0);
		sb.append("Select * From reservation Where reservno=?");
		ReservationVO vo = null;
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, reservno);
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
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
				vo = new ReservationVO(reservno, checkin, checkout, name, phone, price, guest, dcratio, regdate, status, ip, spaceno, memno);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return vo;
	}
	
	// 데이터 추가
	public void addOne(ReservationVO vo) {
		sb.setLength(0);
		sb.append("Insert Into ");
		sb.append("reservation (checkin, checkout, name, phone, price, guest, dcratio, regdate, status, ip, spaceno, memno) ");
		sb.append("Values (?, ?, ?, ?, ?, ?, ?, now(), 1, ?, ?, ?)");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getCheckin());
			pstmt.setString(2, vo.getCheckout());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getPhone());
			pstmt.setInt(5, vo.getPrice());
			pstmt.setInt(6, vo.getGuest());
			pstmt.setInt(7, vo.getDcratio());
			pstmt.setString(8, vo.getIp());
			pstmt.setInt(9, vo.getSpaceno());
			pstmt.setInt(10, vo.getMemno());
			
			
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 데이터 변경
	public void modifyOne(ReservationVO vo) {
		sb.setLength(0);
		sb.append("Update reservation Set ");
		sb.append("checkin=?, checkout=?, name=?, phone=?, price=?, guest=?, dcratio=?, status=? ");
		sb.append("Where reservno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getCheckin());
			pstmt.setString(2, vo.getCheckout());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getPhone());
			pstmt.setInt(5, vo.getPrice());
			pstmt.setInt(6, vo.getGuest());
			pstmt.setInt(7, vo.getDcratio());
			pstmt.setInt(8, vo.getStatus());
			
			pstmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 데이터 변경
		public void modifyStatus(ReservationVO vo) {
			sb.setLength(0);
			sb.append("Update reservation Set ");
			sb.append("status=? ");
			sb.append("Where reservno=?");
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, vo.getStatus());
				pstmt.setInt(2, vo.getReservno());
				
				pstmt.executeUpdate();
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	
	// 데이터 컬럼 변경	
	public void modifyOnePart(int reservno, String field, String value) {
		sb.setLength(0);
		sb.append("Update reservation Set ?=? Where reservno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, field);
			pstmt.setString(2, value);
			pstmt.setInt(3, reservno);
			
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// 데이터 삭제
	public void deleteOne(int reservno) {
		sb.setLength(0);
		sb.append("Delete From reservation Where reservno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, reservno);
			
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
