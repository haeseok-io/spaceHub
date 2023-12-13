package com.spacehub.www.control;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.spacehub.www.dao.DiscountDAO;
import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.dao.SpaceDetailDAO;
import com.spacehub.www.dao.SpaceFacDAO;
import com.spacehub.www.dao.SpaceImageDAO;
import com.spacehub.www.vo.DiscountVO;
import com.spacehub.www.vo.SmemberVO;
import com.spacehub.www.vo.SpaceDetailVO;
import com.spacehub.www.vo.SpaceFacVO;
import com.spacehub.www.vo.SpaceImageVO;
import com.spacehub.www.vo.SpaceVO;

@WebServlet("/mypage/hostControl")
public class SpaceModifyOkControl extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session =  req.getSession(true);
		SmemberVO memberData = (SmemberVO) session.getAttribute("member");
		session.setAttribute("member", memberData);
		
		SpaceDAO spaceDao = new SpaceDAO();
		SpaceDetailDAO spcaeDetailDao = new SpaceDetailDAO();
		SpaceImageDAO spcaeImageDao = new SpaceImageDAO();
		SpaceFacDAO spcaeFacDao = new SpaceFacDAO();
		DiscountDAO discountDao = new DiscountDAO();
		
		SpaceVO spaceVo = new SpaceVO();
		SpaceDetailVO spaceDetailVo = new SpaceDetailVO();
		SpaceFacVO spaceFacVo = new SpaceFacVO();
		
		//업로드할 경로
		String saveFolder = req.getRealPath("/upload");
		System.out.println(saveFolder);
		//첨부파일 크기
		int size = 1024*1024*100;
		
		MultipartRequest mr;
		try {
			mr = new MultipartRequest(req, saveFolder, size, "UTF-8", new DefaultFileRenamePolicy());
			
			//공간 정보(space)
			String spaceno = mr.getParameter("spaceno");
			String type = mr.getParameter("type");	
			//지역 앞부분만 잘라오기
			String loc = mr.getParameter("address");	
			String[] locSplit = loc.split(" ");
			//String loc1 = "경기 성남시 도곡동";
			//String[] locSplit = loc1.split(" ");
			String subject = mr.getParameter("subject");		
			String post = mr.getParameter("post");		
			String addr = mr.getParameter("address");		
			String price = mr.getParameter("price");	
			String vstatus	= mr.getParameter("vstatus");
			String memno = mr.getParameter("memno");
			//String regdate = req.getParameter("regdate");	
			System.out.println("spacetype:"+type);
			System.out.println("spaceloc:"+locSplit[0]);
			System.out.println("spacesubject:"+subject);
			System.out.println("spacepost:"+post);
			System.out.println("spacetype:"+type);
			System.out.println("spaceaddr:"+addr);
			System.out.println("spaceprice:"+price);
			System.out.println("vstatus:"+vstatus);
			System.out.println("memno:"+memno);
			try {
				String ip = Inet4Address.getLocalHost().getHostAddress();
				System.out.println("spaceip:"+ip);
			} catch (UnknownHostException e) {
				System.out.println("space정보 가져오기 실패");
				e.printStackTrace();
			}
			
			//공간 상세정보(space_detail)
			String detailType = mr.getParameter("detailType"); // 집,방,다인실 선택
			String detail = mr.getParameter("detail");
			String maxGuest = mr.getParameter("maxGuest");
			String bed = mr.getParameter("bed");
			String bathroom = mr.getParameter("bathroom");
			String inOutDate = mr.getParameter("inOutDate"); //날짜 자르기
			int idx = inOutDate.indexOf("~");
			String inDate = inOutDate.substring(0,idx);
			String outDate = inOutDate.substring(idx+1);
			String x = mr.getParameter("x");
			String y = mr.getParameter("y");
			System.out.println("spacedetailType:"+detailType);
			System.out.println("detail:"+detail);
			System.out.println("maxGuest:"+maxGuest);
			System.out.println("bed:"+bed);
			System.out.println("bathroom:"+bathroom);
			System.out.println("inDate:"+inDate);
			System.out.println("outDate:"+outDate);
			System.out.println("x:"+x);
			System.out.println("y:"+y);
			
			//공간 편의시설(space_fac)
			String wifi = mr.getParameter("wifi");
			String tv = mr.getParameter("tv");
			String kitchen = mr.getParameter("kitchen");
			String wm = mr.getParameter("wm");
			String aircon = mr.getParameter("aircon");
			String canpark = mr.getParameter("canpark");
			String canfreepark = mr.getParameter("canfreepark");
			String swim = mr.getParameter("swim");
			String bbq = mr.getParameter("bbq");
			String pooltable = mr.getParameter("pooltable");
			String fireplace = mr.getParameter("fireplace");
			String firealarm = mr.getParameter("firealarm");
			String aidkit = mr.getParameter("aidkit");
			String firearm = mr.getParameter("firearm");
			System.out.println("wifi:"+wifi);
			System.out.println("tv:"+tv);
			System.out.println("kitchen:"+kitchen);
			System.out.println("wm:"+wm);
			System.out.println("aircon:"+aircon);
			System.out.println("canpark:"+canpark);
			System.out.println("canfreepark:"+canfreepark);
			System.out.println("swim:"+swim);
			System.out.println("bbq:"+bbq);
			System.out.println("pooltable:"+pooltable);
			System.out.println("fireplace:"+fireplace);
			System.out.println("firealarm:"+firealarm);
			System.out.println("aidkit:"+aidkit);
			System.out.println("firearm:"+firearm);
			
			//할인등록
			String[] dcRatio = mr.getParameterValues("dcRatio");
			String[] disName = mr.getParameterValues("disName");
			System.out.println("dcRatio:"+dcRatio);
			System.out.println("disName:"+disName);
///////////////////////vo.set, modifyOne/////////////////////////

			//공간 정보(space) 수정
			spaceVo.setSpaceno(Integer.parseInt(spaceno));
			spaceVo.setType(type);
			spaceVo.setLoc(locSplit[0]);
			spaceVo.setSubject(subject);
			spaceVo.setPost(post);
			spaceVo.setAddr(addr);
			spaceVo.setPrice(Integer.parseInt(price));
			//spaceVo.setRegdate(regdate);
			try {
				spaceVo.setIp(
				 Inet4Address.getLocalHost().getHostAddress());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			spaceVo.setVStatus(Integer.parseInt(vstatus));
			//spaceVo.setStatus(Integer.parseInt(status));
			spaceVo.setMemno(Integer.parseInt(memno));
			spaceDao.modifyOne(spaceVo);
			
			//공간 상세정보(space_detail)
			spaceDetailVo.setSpaceno(Integer.parseInt(spaceno));
			spaceDetailVo.setType(detailType);
			spaceDetailVo.setDetail(detail);
			spaceDetailVo.setMaxGuest(Integer.parseInt(maxGuest));
			spaceDetailVo.setBed(Integer.parseInt(bed));
			spaceDetailVo.setBathroom(Integer.parseInt(bathroom));
			spaceDetailVo.setInDate(inDate);
			spaceDetailVo.setOutDate(outDate);
			spaceDetailVo.setX(x);
			spaceDetailVo.setY(y);
			spcaeDetailDao.modifyOne(spaceDetailVo);
					
		
			// 공간 이미지(space_image)
			System.out.println(mr.getFileNames());
			System.out.println(mr.getFilesystemName("img"));
			System.out.println(mr.getOriginalFileName("img"));
			
			 // 첫 번째 파일 처리
		    Enumeration<String> fileInputNames = mr.getFileNames(); // 모든 파일 업로드 필드의 이름들을 가져옴
		    int seq = 1; // 순서를 지정하기 위한 변수
		    
		    while (fileInputNames.hasMoreElements()) {
		        String inputName = fileInputNames.nextElement();
		        String uploadedFilePath = mr.getFilesystemName(inputName);
		        System.out.println(" uploadedFilePath : " + uploadedFilePath);
		        // 파일 업로드가 실패한 경우, 파일 경로는 null일 수 있습니다.
		        if (uploadedFilePath != null) {
		            // 업로드된 파일의 경로를 DB에 저장하거나 다른 작업 수행
		            SpaceImageVO spaceImageVo = new SpaceImageVO(); // 파일 정보를 담을 객체 생성
		            spaceImageVo.setSpaceno(Integer.parseInt(spaceno)); // 공간 번호 설정
		            //spaceImageVo.setImgno(1); // 이미지 번호 설정
		            spaceImageVo.setPath("/spaceHub/upload/"+uploadedFilePath); // 파일 경로 설정
		            spaceImageVo.setSeq(seq++); // 순서 설정

		            // 해당 파일 정보를 DB에 삭제 후 추가
		            System.out.println("이미지 VO : " + spaceImageVo);
		            spcaeImageDao.deleteOne(Integer.parseInt(spaceno));
		            spcaeImageDao.addOne(spaceImageVo);
		            spcaeImageDao.close();
		            //System.out.println(seq);
		        }
		    }
		    
			//공간 편의시설(space_fac)
			spaceFacVo.setSpaceno(Integer.parseInt(spaceno));
			if(wifi!=null) spaceFacVo.setWifi(Integer.parseInt(wifi));
			if(tv!=null) spaceFacVo.setTv(Integer.parseInt(tv));
			if(kitchen!=null) spaceFacVo.setKitchen(Integer.parseInt(kitchen));
			if(wm!=null) spaceFacVo.setWm(Integer.parseInt(wm));
			if(aircon!=null) spaceFacVo.setAircon(Integer.parseInt(aircon));
			if(canpark!=null) spaceFacVo.setCanpark(Integer.parseInt(canpark));
			if(canfreepark!=null) spaceFacVo.setCanfreepark(Integer.parseInt(canfreepark));
			if(swim!=null) spaceFacVo.setSwim(Integer.parseInt(swim));
			if(bbq!=null) spaceFacVo.setBbq(Integer.parseInt(bbq));
			if(pooltable!=null) spaceFacVo.setPooltable(Integer.parseInt(pooltable));
			if(fireplace!=null) spaceFacVo.setFireplace(Integer.parseInt(fireplace));
			if(firealarm!=null) spaceFacVo.setFirealarm(Integer.parseInt(firealarm));
			if(aidkit!=null) spaceFacVo.setAidkit(Integer.parseInt(aidkit));
			if(firearm!=null) spaceFacVo.setFirearm(Integer.parseInt(firearm));
			spcaeFacDao.modifyOne(spaceFacVo);
			
			//할인등록(discount)
			for(int i=0; i<dcRatio.length; i++)	{
				DiscountVO discountVo = new DiscountVO();
				discountVo.setSpaceno(Integer.parseInt(spaceno));
				discountVo.setName(disName[i]);
				discountVo.setDcratio(Integer.parseInt(dcRatio[i]));
				discountDao.modifyOne(discountVo);
			}
			
			
		}  catch (IOException e) { //end of try
			e.printStackTrace();
			System.out.println("fail");
		}
		
		spaceDao.close();
		spcaeDetailDao.close();
		spcaeImageDao.close();
		spcaeFacDao.close();
		discountDao.close();
		
		// 처리가 끝난 후 /spaceHub/home 으로 이동
		RequestDispatcher dispatcher = req.getRequestDispatcher("/mypage/host?cmd=hostMain");
		dispatcher.forward(req, resp);
	}
}
	
