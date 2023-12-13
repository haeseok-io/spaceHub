package com.spacehub.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spacehub.www.vo.SpaceDetailVO;
import com.spacehub.www.vo.SpaceFacVO;

public class SpaceFacDAO {
	Connection conn;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	private StringBuffer sb = new StringBuffer();

	public SpaceFacDAO() {
		conn = DBConnection.getConnection();
	}

	public SpaceFacVO getOne(int spaceno) {
		sb.setLength(0);
		sb.append(
				"SELECT spaceno, wifi, tv, kitchen, wm, aircon, canpark, canfreepark, swim, bbq, pooltable, fireplace, firealarm, aidkit, firearm FROM space_fac WHERE spaceno=?");
		SpaceFacVO vo = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, spaceno);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int wifi = rs.getInt("wifi");
				int tv = rs.getInt("tv");
				int kitchen = rs.getInt("kitchen");
				int wm = rs.getInt("wm");
				int aircon = rs.getInt("aircon");
				int canpark = rs.getInt("canpark");
				int canfreepark = rs.getInt("canfreepark");
				int swim = rs.getInt("swim");
				int bbq = rs.getInt("bbq");
				int pooltable = rs.getInt("pooltable");
				int fireplace = rs.getInt("fireplace");
				int firealarm = rs.getInt("firealarm");
				int aidkit = rs.getInt("aidkit");
				int firearm = rs.getInt("firearm");
				vo = new SpaceFacVO(spaceno, wifi, tv, kitchen, wm, aircon, canpark, canfreepark, swim, bbq, pooltable,
						fireplace, firealarm, aidkit, firearm);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	// 데이터 추가
	public void addOne(SpaceFacVO vo) {
		sb.setLength(0);
		sb.setLength(0);
		sb.append(
				"INSERT INTO space_fac (spaceno, wifi, tv, kitchen, wm, aircon, canpark, canfreepark, swim, bbq, pooltable, fireplace, firealarm, aidkit, firearm) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, vo.getSpaceno());
			pstmt.setInt(2, vo.getWifi());
			pstmt.setInt(3, vo.getTv());
			pstmt.setInt(4, vo.getKitchen());
			pstmt.setInt(5, vo.getWm());
			pstmt.setInt(6, vo.getAircon());
			pstmt.setInt(7, vo.getCanpark());
			pstmt.setInt(8, vo.getCanfreepark());
			pstmt.setInt(9, vo.getSwim());
			pstmt.setInt(10, vo.getBbq());
			pstmt.setInt(11, vo.getPooltable());
			pstmt.setInt(12, vo.getFireplace());
			pstmt.setInt(13, vo.getFirealarm());
			pstmt.setInt(14, vo.getAidkit());
			pstmt.setInt(15, vo.getFirearm());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 수정
	public void modifyOne(SpaceFacVO vo) {
		sb.setLength(0);
		sb.append("update space_fac set ");
		sb.append("wifi=?, tv=?, kitchen=?, wm=?, aircon=?, canpark=?, ");
		sb.append("canfreepark=?, swim=?, bbq=?, pooltable=?, fireplace=?, ");
		sb.append("firealarm=?, aidkit=?, firearm=? ");
		sb.append("where spaceno=?");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, vo.getWifi());
			pstmt.setInt(2, vo.getTv());
			pstmt.setInt(3, vo.getKitchen());
			pstmt.setInt(4, vo.getWm());
			pstmt.setInt(5, vo.getAircon());
			pstmt.setInt(6, vo.getCanpark());
			pstmt.setInt(7, vo.getCanfreepark());
			pstmt.setInt(8, vo.getSwim());
			pstmt.setInt(9, vo.getBbq());
			pstmt.setInt(10, vo.getPooltable());
			pstmt.setInt(11, vo.getFireplace());
			pstmt.setInt(12, vo.getFirealarm());
			pstmt.setInt(13, vo.getAidkit());
			pstmt.setInt(14, vo.getFirearm());
			pstmt.setInt(15, vo.getSpaceno());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//삭제
	public void deleteOne(int spaceno) {
		sb.setLength(0);
		sb.append("delete from space_fac where spaceno=?");
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, spaceno);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
