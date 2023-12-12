package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.spacehub.www.dao.SpaceImageDAO;
import com.spacehub.www.vo.SmemberVO;
import com.spacehub.www.vo.SpaceImageVO;

public class ImageDeleteOkAction implements JsonAction {

	@Override
	public JSONObject execute(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject data = new JSONObject();
		HttpSession session = req.getSession();
		
		String spacenoParam = req.getParameter("spaceno"); // 수정된 변수명
        String imgnoParam = req.getParameter("imgno"); // 이미지 번호 파라미터 추가

        SmemberVO memberData = (SmemberVO) session.getAttribute("memno");
		// Check
		if( spacenoParam==null || spacenoParam.isEmpty() ) {
			data.put("errorCode", "param empty");
			data.put("errorMsg", "공간 번호가 존재하지 않습니다.");
			return data;
		}
		// - 회원일 경우에만 수정 가능하도록
		if( memberData==null ) {
			data.put("errorCode", "member empty");
			data.put("errorMsg", "수정은 로그인 후 가능합니다.");
			return data;
		}
		
		 // Data
        int spaceno = Integer.parseInt(spacenoParam);
        int imgno = Integer.parseInt(imgnoParam); // 이미지 번호 파라미터를 정수형으로 변환
		
        // Process
        SpaceImageDAO spaceImageDao = new SpaceImageDAO();
        spaceImageDao.deleteOne(imgno); // 이미지 번호를 이용해 이미지 삭제
        spaceImageDao.close();
		
		return data;
	}

}
