package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.JjimListVO;
import com.spacehub.www.vo.SpaceListVO;
import com.spacehub.www.vo.SpaceVO;

public class SpaceDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	public SpaceDAO() {
		conn = DBConnection.getConnection();
	}
	
	// 전체 갯수 가져오기
	public int getTotalCount() {
		int total = 0;
		
		sb.setLength(0);
		sb.append("select count(s.spaceno) as total ");
		sb.append("From ( ");
		sb.append("Select s.spaceno ");
		sb.append("From space s, space_detail d, space_image i, reservation r, review rv ");
		sb.append("Where s.spaceno=d.spaceno And s.spaceno=i.spaceno And s.spaceno=r.spaceno And r.reservno=rv.reservno ");
		sb.append("And i.seq=(Select seq From space_image Where spaceno=i.spaceno Order By seq ASC Limit 1) ");
		sb.append("Group By rv.reservno");
		sb.append(") as s");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			if( rs.next() ) {
				total = rs.getInt("total");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return total;
	}
	
	// 전체 갯수 가져오기
	public int getTotalCount(String addr, String inDate, String outDate, int maxGuest) {
		int total = 0;
		
		sb.setLength(0);
		sb.append("select count(s.spaceno) as total ");
		sb.append("From ( ");
		sb.append("Select s.spaceno ");
		sb.append("From space s, space_detail d, space_image i, reservation r, review rv ");
		sb.append("Where s.spaceno=d.spaceno And s.spaceno=i.spaceno And s.spaceno=r.spaceno And r.reservno=rv.reservno ");
		sb.append("And i.seq=(Select seq From space_image Where spaceno=i.spaceno Order By seq ASC Limit 1) ");
		
		if( addr!=null && !addr.equals("") ) 		sb.append("And addr Like '%"+addr+"%' ");
		if( inDate!=null && !inDate.equals("") ) 	sb.append("And in_date>='"+inDate+"' ");
		if( outDate!=null && !outDate.equals("") ) 	sb.append("And out_date<='"+outDate+"' ");
		if( maxGuest!=0 )							sb.append("And max_guest>="+maxGuest+" ");
		
		sb.append("Group By rv.reservno");
		sb.append(") as s");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			if( rs.next() ) {
				total = rs.getInt("total");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return total;
	}
	
	// 최근 Insert 한 PK값
	public int getLastInsert() {
		int lastInsertNo = 0;
		
		sb.setLength(0);
		sb.append("Select last_insert_id() as last_insert_no");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			if( rs.next() ) {
				lastInsertNo = rs.getInt("last_insert_no");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return lastInsertNo;
	}

	// 전체 리스트
	public ArrayList<SpaceListVO> getList() {
		ArrayList<SpaceListVO> list = new ArrayList<SpaceListVO>();
		
		sb.setLength(0);
		sb.append("Select s.spaceno, s.loc, s.subject, s.addr, s.price, s.memno, d.in_date, d.out_date, i.path, avg(rv.rating) as rating ");
		sb.append("From space s, space_detail d, space_image i, reservation r, review rv ");
		sb.append("Where s.spaceno=d.spaceno And s.spaceno=i.spaceno And s.spaceno=r.spaceno And r.reservno=rv.reservno ");
		sb.append("And i.seq=(Select seq From space_image Where spaceno=i.spaceno Order By seq ASC Limit 1) ");
		sb.append("Group By rv.reservno");
		
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) 	list.add(setList(rs));
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 페이징 리스트
	public ArrayList<SpaceListVO> getList(int startNo, int endNo) {
		ArrayList<SpaceListVO> list = new ArrayList<SpaceListVO>();
		
		sb.setLength(0);
		sb.append("Select s.spaceno, s.loc, s.subject, s.addr, s.price, s.memno, d.in_date, d.out_date, i.path, avg(rv.rating) as rating ");
		sb.append("From space s, space_detail d, space_image i, reservation r, review rv ");
		sb.append("Where s.spaceno=d.spaceno And s.spaceno=i.spaceno And s.spaceno=r.spaceno And r.reservno=rv.reservno ");
		sb.append("And i.seq=(Select seq From space_image Where spaceno=i.spaceno Order By seq ASC Limit 1) ");
		sb.append("Group By rv.reservno ");
		sb.append("Limit "+startNo+", "+endNo);
		
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) 	list.add(setList(rs));
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 페이징 조건 검색
	public ArrayList<SpaceListVO> getList(int startNo, int endNo, String addr, String inDate, String outDate, int maxGuest) {
		ArrayList<SpaceListVO> list = new ArrayList<SpaceListVO>();
		
		sb.setLength(0);
		sb.append("Select s.spaceno, s.loc, s.subject, s.addr, s.price, s.memno, d.in_date, d.out_date, i.path, avg(rv.rating) as rating ");
		sb.append("From space s, space_detail d, space_image i, reservation r, review rv ");
		sb.append("Where s.spaceno=d.spaceno And s.spaceno=i.spaceno And s.spaceno=r.spaceno And r.reservno=rv.reservno ");
		sb.append("And i.seq=(Select seq From space_image Where spaceno=i.spaceno Order By seq ASC Limit 1) ");
		
		if( addr!=null && !addr.equals("") ) 		sb.append("And addr Like '%"+addr+"%' ");
		if( inDate!=null && !inDate.equals("") ) 	sb.append("And in_date>='"+inDate+"' ");
		if( outDate!=null && !outDate.equals("") ) 	sb.append("And out_date<='"+outDate+"' ");
		if( maxGuest!=0 )							sb.append("And max_guest>="+maxGuest+" ");
		
		sb.append("Group By rv.reservno ");
		sb.append("Limit "+startNo+", "+endNo);
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) 	list.add(setList(rs));
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	// 가져온값 SpaceListVO 에 담기
	public SpaceListVO setList(ResultSet rs) {
		SpaceListVO data = new SpaceListVO();
		
		try {
			data.setSpaceno(rs.getInt("spaceno"));
			data.setLoc(rs.getString("loc"));
			data.setSubject(rs.getString("subject"));
			data.setAddr(rs.getString("addr"));
			data.setPrice(rs.getInt("price"));
			data.setMemno(rs.getInt("memno"));
			data.setInDate(rs.getString("in_date"));
			data.setOutDate(rs.getString("out_date"));
			data.setPath(rs.getString("path"));
			data.setRating(rs.getFloat("rating"));
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	//하나 가져오기
		public SpaceVO getOne(int spaceno) {
			SpaceVO data = null;
			sb.setLength(0);
			sb.append("select spaceno, type, loc, subject, post, addr, price, regdate, ip, v_status, status, memno from space where spaceno = ? ");
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, spaceno);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					String type = rs.getString("type");
					String loc = rs.getString("loc");
					String subject = rs.getString("subject");
					String post = rs.getString("post");
					String addr = rs.getString("addr");
					int price = rs.getInt("price");
					String regdate = rs.getString("regdate");
					String ip = rs.getString("ip");
					int vStatus = rs.getInt("v_status");
					int status = rs.getInt("status");
					int memno = rs.getInt("memno");
					data = new SpaceVO(spaceno, type, loc, subject, post, addr, price, regdate, ip, vStatus, status, memno);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return data;
		}
	
	// 찜 리스트
	public ArrayList<JjimListVO> getJjimList(int memno) {
		ArrayList<JjimListVO> list = new ArrayList<JjimListVO>();

		sb.setLength(0);
		sb.append("select smember.memno, space.subject, space.spaceno, space_detail.bed, "
				+ "space_detail.in_date, space_detail.out_date, space.price, jjim.jjimno ");
		sb.append("from space ");
		sb.append("join space_detail on space.spaceno = space_detail.spaceno ");
		sb.append("join jjim on space.spaceno = jjim.spaceno ");
		sb.append("join smember on space.memno = smember.memno ");
		sb.append("where smember.memno=?");
		
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {;
				String subject = rs.getString("space.subject");
				int spaceno = rs.getInt("space.spaceno");
				int bed = rs.getInt("space_detail.bed");
				String inDate = rs.getString("space_detail.in_date");
				String outDate = rs.getString("space_detail.out_date");
				int price = rs.getInt("space.price");
				int jjimno = rs.getInt("jjim.jjimno");
				
				JjimListVO vo = new JjimListVO(memno, subject, spaceno, bed, inDate, outDate, price, jjimno, null);
				list.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	//추가
	public void addOne(SpaceVO vo) {
		sb.setLength(0);
		sb.append("insert into space(type, loc, subject, post, addr, price, regdate, ip, v_status, status, memno) ");
		sb.append("values(?,?,?,?,?,?,now(),?,1,1,?) ");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getType());
			pstmt.setString(2, vo.getLoc());
			pstmt.setString(3, vo.getSubject());
			pstmt.setString(4, vo.getPost());
			pstmt.setString(5, vo.getAddr());
			pstmt.setInt(6, vo.getPrice());
			pstmt.setString(7, vo.getIp());
			pstmt.setInt(8, vo.getMemno());
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

}// class end
