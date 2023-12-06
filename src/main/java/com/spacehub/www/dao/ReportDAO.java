package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import com.spacehub.www.vo.ReportVO;

public class ReportDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private StringBuffer sb = new StringBuffer();
	
	public ReportDAO() {
		conn = DBConnection.getConnection();
	}

	// 회원 번호를 통한 신고내역 조회
	public ArrayList<ReportVO> getAll(int memno) {
		ArrayList<ReportVO> list = new ArrayList<ReportVO>();
		sb.setLength(0);
		sb.append("select report.reportno, report.subject, report.contents, "
				+ "report.regdate, report.ip, report.spaceno, report.memno, "
				+ "space.subject ");
		sb.append("from report ");
		sb.append("join space on report.memno = space.memno ");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int reportno = rs.getInt("report.reportno");
				String subject = rs.getString("report.subject");
				String contents = rs.getString("report.contents");
				String regdate = rs.getString("report.regdate");
				String ip = rs.getString("report.ip");
				int spaceno = rs.getInt("report.spaceno");
				String spaceSubject = rs.getString("space.subject");
				ReportVO vo = new ReportVO(reportno, subject, contents, regdate, ip, spaceno, memno, spaceSubject);
				list.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	// 추가
	public void addOne(ReportVO vo) {
		sb.setLength(0);
		sb.append("select report.subject, report.contents, report.regdate, "
				+ "report.ip, report.spaceno, report.memno, "
				+ "space.subject as space_subject ");
		sb.append("from report ");
		sb.append("join space on report.memno = space.memno ");
		sb.append("insert into report(subject, contents, regdate, ip, spaceno, memno, space_subject) ");
		sb.append("values(?, ?, now(), ?, ?, ?, ?)");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getSubject());
			pstmt.setString(2, vo.getContents());
			pstmt.setString(3, vo.getIp());
			pstmt.setInt(4, vo.getSpaceno());
			pstmt.setInt(5, vo.getMemno());
			pstmt.setString(6, vo.getSpaceSubject());
			
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
