package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.HostSpaceImageVO;
import com.spacehub.www.vo.SpaceImageVO;

public class SpaceImageDAO {
	Connection conn;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	private StringBuffer sb = new StringBuffer();
	
	public SpaceImageDAO() {
		conn = DBConnection.getConnection();
	}
	
	//공간 이미지 삭제
	public void deleteOne(int spaceno) {
		sb.setLength(0);
		sb.append("delete from space_image where spaceno=? ");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, spaceno);
			int result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("이미지 삭제 실패");
			e.printStackTrace();
		}
				
	}
	
	// 공간 이미지 수정
	public void modifyOne(SpaceImageVO vo) {
		sb.setLength(0);
		sb.append("update space_image ");
		sb.append("set path=?, seq=? ");
		sb.append("where spaceno=?");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1,vo.getPath());
			pstmt.setInt(2, vo.getSeq());
			pstmt.setInt(3, vo.getSpaceno());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SpaceImageDAO 업데이트 실패");
		}
		
	}
	
	// 공간 전체 이미지 조회
	public ArrayList<SpaceImageVO> getSpaceImages(int spaceno) {
		ArrayList<SpaceImageVO> list = new ArrayList<SpaceImageVO>();
		
		sb.setLength(0);
		sb.append("Select imgno, path, seq, spaceno From space_image Where spaceno=? order by seq asc");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, spaceno);
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				list.add(new SpaceImageVO(
					rs.getInt("imgno"),
					rs.getString("path"),
					rs.getInt("seq"),
					rs.getInt("spaceno")
				));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 공간 이미지 조회 (spceno, memno)
	public ArrayList<HostSpaceImageVO> getSpaceImages(int spaceno, int memno) {
		ArrayList<HostSpaceImageVO> list = new ArrayList<HostSpaceImageVO>();
		
		sb.setLength(0);
		sb.append("Select space_image.imgno, space_image.path, space_image.seq, space_image.spaceno, space.memno ");
		sb.append("From space_image ");
		sb.append("join space on space_image.spaceno = space.spaceno ");
		sb.append("where space_image.spaceno=? and space.memno=? oreder by seq asc");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, spaceno);
			pstmt.setInt(2, memno);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(new HostSpaceImageVO(
					rs.getInt("space_image.imgno"),
					rs.getString("space_image.path"),
					rs.getInt("space_image.seq"),
					spaceno,
					memno
				));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 데이터 추가
	public void addOne(SpaceImageVO vo) {
		sb.setLength(0);
		System.out.println("seq: " + vo.getSeq());
		sb.append("INSERT INTO space_image (path, seq, spaceno) VALUES (?, ?, ?)");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getPath());
			pstmt.setInt(2, vo.getSeq());
			pstmt.setInt(3, vo.getSpaceno());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 공간 대표 이미지 추출
	public String getMainPath(int spaceno) {
		String path = "";
		
		sb.setLength(0);
		sb.append("Select path From space_image Where spaceno=? Order By seq DESC");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, spaceno);
			rs = pstmt.executeQuery();
			
			if( rs.next() ) {
				path = rs.getString("path");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return path;
	}
	
	public void close() {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
