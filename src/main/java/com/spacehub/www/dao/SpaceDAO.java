package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.HostMainVO;
import com.spacehub.www.vo.JjimListVO;
import com.spacehub.www.vo.SpaceDiscountVO;
import com.spacehub.www.vo.SpaceHostVO;
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
		sb.append("Group By r.spaceno");
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
	public int getTotalCount(String subject, String inDate, String outDate, int maxGuest) {
		int total = 0;
		
		sb.setLength(0);
		sb.append("select count(s.spaceno) as total ");
		sb.append("From ( ");
		sb.append("Select s.spaceno From space s ");
		sb.append("Join space_detail d On s.spaceno=d.spaceno ");
		sb.append("Left Join space_image i On s.spaceno=i.spaceno ");
		sb.append("Left Join reservation r On s.spaceno=r.spaceno ");
		sb.append("Left Join review rv On r.reservno=rv.reservno ");
		sb.append("Where i.seq=(Select seq From space_image Where spaceno=i.spaceno Order By seq ASC Limit 1) ");
		
		if( subject!=null && !subject.equals("") ) 	sb.append("And s.subject Like '%"+subject+"%' ");		
		if( inDate!=null && !inDate.equals("") ) 	sb.append("And d.in_date<='"+inDate+"' ");
		if( outDate!=null && !outDate.equals("") ) 	sb.append("And d.out_date>='"+outDate+"' ");
		if( maxGuest!=0 )							sb.append("And d.max_guest>="+maxGuest+" ");
		
		sb.append("Group By s.spaceno");
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

	// 등록한 방 가져오기
	public ArrayList<HostMainVO> getHostSpace(int memno) {
		ArrayList<HostMainVO> list = new ArrayList<HostMainVO>();
				
		sb.setLength(0);
		sb.append("Select spaceno, subject from space where memno=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, memno);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int spaceno = rs.getInt("spaceno");
				String subject = rs.getString("subject");
				
				HostMainVO vo = new HostMainVO(spaceno, subject, memno, null);
				list.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	// 전체 리스트
	public ArrayList<SpaceListVO> getList() {
		ArrayList<SpaceListVO> list = new ArrayList<SpaceListVO>();
		
		sb.setLength(0);
		sb.append("Select s.spaceno, s.loc, s.subject, s.addr, s.price, s.memno, d.in_date, d.out_date, i.path, avg(rv.rating) as rating ");
		sb.append("From space s ");
		sb.append("Join space_detail d On s.spaceno=d.spaceno ");
		sb.append("Left Join space_image i On s.spaceno=i.spaceno ");
		sb.append("Left Join reservation r On s.spaceno=r.spaceno ");
		sb.append("Left Join review rv On r.reservno=rv.reservno ");
		sb.append("Where i.seq=(Select seq From space_image Where spaceno=i.spaceno Order By seq ASC Limit 1) ");
		sb.append("Group By s.spaceno");
		
		
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
		sb.append("From space s ");
		sb.append("Join space_detail d On s.spaceno=d.spaceno ");
		sb.append("Left Join space_image i On s.spaceno=i.spaceno ");
		sb.append("Left Join reservation r On s.spaceno=r.spaceno ");
		sb.append("Left Join review rv On r.reservno=rv.reservno ");
		sb.append("Where i.seq=(Select seq From space_image Where spaceno=i.spaceno Order By seq ASC Limit 1) ");
		sb.append("Group By s.spaceno ");
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
	public ArrayList<SpaceListVO> getList(int startNo, int endNo, String subject, String inDate, String outDate, int maxGuest) {
		ArrayList<SpaceListVO> list = new ArrayList<SpaceListVO>();
		
		sb.setLength(0);
		sb.append("Select s.spaceno, s.loc, s.subject, s.addr, s.price, s.memno, d.in_date, d.out_date, i.path, avg(rv.rating) as rating ");
		sb.append("From space s ");
		sb.append("Join space_detail d On s.spaceno=d.spaceno ");
		sb.append("Left Join space_image i On i.spaceno=s.spaceno ");
		sb.append("Left Join reservation r On r.spaceno=s.spaceno ");
		sb.append("Left Join review rv On r.reservno=rv.reservno ");
		sb.append("Where i.seq=(Select seq From space_image Where spaceno=i.spaceno Order By seq ASC Limit 1) ");
		
		if( subject!=null && !subject.equals("") ) 	sb.append("And s.subject Like '%"+subject+"%' ");
		if( inDate!=null && !inDate.equals("") ) 	sb.append("And d.in_date<='"+inDate+"' ");
		if( outDate!=null && !outDate.equals("") ) 	sb.append("And d.out_date>='"+outDate+"' ");
		if( maxGuest!=0 )							sb.append("And d.max_guest>="+maxGuest+" ");
		
		sb.append("Group By s.spaceno ");
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
			sb.append("Select s.spaceno, s.type, s.loc, s.subject, s.post, s.addr, s.price, s.regdate, s.ip, s.v_status, s.status, s.memno, i.path ");
			sb.append("From space s, space_image i ");
			sb.append("Where s.spaceno=i.spaceno AND s.spaceno=? ");
			sb.append("AND i.seq=(Select min(seq) From space_image Where spaceno=s.spaceno)");
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, spaceno);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					data = new SpaceVO(
						spaceno,
						rs.getString("type"),
						rs.getString("loc"),
						rs.getString("subject"),
						rs.getString("post"),
						rs.getString("addr"),
						rs.getInt("price"),
						rs.getString("regdate"),
						rs.getString("ip"),
						rs.getInt("v_status"),
						rs.getInt("status"),
						rs.getInt("memno"),
						rs.getString("path")
					);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return data;
		}
		
		//예약한 하나 가져오기
				public SpaceDiscountVO getRes(int memno, int spaceno) {
					SpaceDiscountVO data = null;
					sb.setLength(0);
					sb.append("SELECT s.spaceno, s.type, s.loc, s.subject, s.post, s.addr, s.price, s.regdate, s.ip, s.v_status, s.status, r.memno, d.disno, d.name, d.dcratio, count(distinct r.reservno) as reservno, r.checkin, r.checkout, r.phone, r.guest from space s, discount d, reservation r where s.spaceno=d.spaceno AND s.spaceno=r.spaceno AND r.memno=? and s.spaceno=? ");
					try {
						pstmt = conn.prepareStatement(sb.toString());
						pstmt.setInt(1, memno);
						pstmt.setInt(2, spaceno);
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
							int disno = rs.getInt("disno");
							String name = rs.getString("name");
							int dcratio = rs.getInt("dcratio");
							int reservno = rs.getInt("reservno");
							String checkin = rs.getString("checkin");
							String checkout = rs.getString("checkout");
							String phone = rs.getString("phone");
							int guest = rs.getInt("guest");
							data = new SpaceDiscountVO(spaceno, type, loc, subject, post, addr, price, regdate, ip, vStatus, status, memno, disno, name, dcratio, reservno, checkin, checkout, phone, guest);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return data;
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
	
	//수정
	public void modifyOne(SpaceVO vo) {
		sb.setLength(0);
		sb.append("update space set ");
		sb.append("type=?, loc=?, subject=?, post=?, addr=?, price=?, regdate=now(), ip=?, v_status=? ");
		sb.append("where spaceno=? and memno=?");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getType());
			pstmt.setString(2, vo.getLoc());
			pstmt.setString(3, vo.getSubject());
			pstmt.setString(4, vo.getPost());
			pstmt.setString(5, vo.getAddr());
			pstmt.setInt(6, vo.getPrice());
			pstmt.setString(7, vo.getIp());
			pstmt.setInt(8, vo.getVStatus());
			pstmt.setInt(9, vo.getSpaceno());
			pstmt.setInt(10, vo.getMemno());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//삭제
	public void deleteOne(int spaceno) {
		sb.setLength(0);
		sb.append("delete from space where spaceno=?");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, spaceno);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
