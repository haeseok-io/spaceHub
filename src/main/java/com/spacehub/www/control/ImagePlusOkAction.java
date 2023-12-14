package com.spacehub.www.control;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.spacehub.www.dao.SpaceImageDAO;
import com.spacehub.www.model.JsonAction;
import com.spacehub.www.vo.SmemberVO;
import com.spacehub.www.vo.SpaceImageVO;

public class ImagePlusOkAction implements JsonAction {

	@Override
	public JSONObject execute(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject data = new JSONObject();
		HttpSession session = req.getSession();
		SmemberVO memberData = (SmemberVO) session.getAttribute("member");


		// 업로드할 경로
		String saveFolder = req.getRealPath("/upload");
		System.out.println(saveFolder);
		// 첨부파일 크기
		int size = 1024 * 1024 * 100;
		MultipartRequest mr;
		try {
			mr = new MultipartRequest(req, saveFolder, size, "UTF-8", new DefaultFileRenamePolicy());
			String spacenoParam = mr.getParameter("spaceno"); // 수정된 변수명
			// String imgnoParam = req.getParameter("imgno"); // 이미지 번호 파라미터 추가
			String path = mr.getParameter("path");
			
			// Check
			if (spacenoParam == null || spacenoParam.isEmpty()) {
				data.put("errorCode", "param empty");
				data.put("errorMsg", "공간 번호가 존재하지 않습니다.");
				return data;
			}
			// - 회원일 경우에만 수정 가능하도록
			if (memberData == null) {
				data.put("errorCode", "member empty");
				data.put("errorMsg", "수정은 로그인 후 가능합니다.");
				return data;
			}

			// Data
			int spaceno = Integer.parseInt(spacenoParam);
			
			// Process
			 // 첫 번째 파일 처리
		    Enumeration<String> fileInputNames = mr.getFileNames(); // 모든 파일 업로드 필드의 이름들을 가져옴
		    int seq = 1; // 순서를 지정하기 위한 변수
		    while (fileInputNames.hasMoreElements()) {
		        String inputName = fileInputNames.nextElement();
		        String uploadedFilePath = mr.getFilesystemName(inputName);

		        // 파일 업로드가 실패한 경우, 파일 경로는 null일 수 있습니다.
		        if (uploadedFilePath != null) {
		            // 업로드된 파일의 경로를 DB에 저장하거나 다른 작업 수행
		        	SpaceImageDAO spaceImageDao = new SpaceImageDAO();
		            SpaceImageVO spaceImageVo = new SpaceImageVO(); // 파일 정보를 담을 객체 생성
		            spaceImageVo.setSpaceno(spaceno);// 공간 번호 설정
		            spaceImageVo.setPath("/spaceHub/upload/"+path); // 파일 경로 설정
		            spaceImageVo.setSeq(seq++); // 순서 설정

		            // 해당 파일 정보를 DB에 추가
		            spaceImageDao.addOne(spaceImageVo);
		            System.out.println("이미지 추가 성공");
		            spaceImageDao.close();
		        }
		    }


		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
		
	}
}
