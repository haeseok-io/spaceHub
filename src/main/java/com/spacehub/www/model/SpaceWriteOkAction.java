package com.spacehub.www.model;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.spacehub.www.dao.DiscountDAO;
import com.spacehub.www.dao.SpaceDAO;
import com.spacehub.www.dao.SpaceDetailDAO;
import com.spacehub.www.dao.SpaceFacDAO;
import com.spacehub.www.dao.SpaceImageDAO;
import com.spacehub.www.vo.DiscountVO;
import com.spacehub.www.vo.SpaceDetailVO;
import com.spacehub.www.vo.SpaceFacVO;
import com.spacehub.www.vo.SpaceImageVO;
import com.spacehub.www.vo.SpaceVO;

public class SpaceWriteOkAction implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		SpaceDAO spaceDao = new SpaceDAO();
		SpaceDetailDAO spcaeDetailDao = new SpaceDetailDAO();
		SpaceImageDAO spcaeImageDao = new SpaceImageDAO();
		SpaceFacDAO spcaeFacDao = new SpaceFacDAO();
		DiscountDAO discountDao = new DiscountDAO();
		SpaceVO spaceVo = new SpaceVO();
		SpaceDetailVO spaceDetailVo = new SpaceDetailVO();
		SpaceImageVO spaceImageVo = new SpaceImageVO();
		SpaceFacVO spaceFacVo = new SpaceFacVO();
		
		//공간 정보(space)
		String type = req.getParameter("type");	
		//지역 앞부분만 잘라오기
		String loc = req.getParameter("address");	
		String[] locSplit = loc.split(" ");
		//String loc1 = "경기 성남시 도곡동";
		//String[] locSplit = loc1.split(" ");
		String subject = req.getParameter("subject");		
		String post = req.getParameter("post");		
		String addr = req.getParameter("address");		
		String price = req.getParameter("price");		
		//String regdate = req.getParameter("regdate");		
		try {
			String ip = req.getParameter( Inet4Address.getLocalHost().getHostAddress());
			System.out.println("spaceip:"+ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}		
		//String vStatus = req.getParameter("vStatus");		
		//String status = req.getParameter("status");
		System.out.println("spacetype:"+type);
		System.out.println("spaceloc:"+loc);
		System.out.println("spacesubject:"+subject);
		System.out.println("spacepost:"+post);
		System.out.println("spacetype:"+type);
		System.out.println("spaceaddr:"+addr);
		System.out.println("spaceprice:"+price);
		
		//////////////////////////////////////
		
		//공간 상세정보(space_detail)
		String detailType = req.getParameter("detailType"); // 집,방,다인실 선택
		String detail = req.getParameter("detail");
		String maxGuest = req.getParameter("maxGuest");
		String bed = req.getParameter("bed");
		String bathroom = req.getParameter("bathroom");
		String inOutDate = req.getParameter("inOutDate"); //날짜 자르기
		int idx = inOutDate.indexOf("~");
		String inDate = inOutDate.substring(0,idx);
		String outDate = inOutDate.substring(idx+1);
		String x = req.getParameter("x");
		String y = req.getParameter("y");
		System.out.println("spacedetailType:"+detailType);
		System.out.println("detail:"+detail);
		System.out.println("maxGuest:"+maxGuest);
		System.out.println("bed:"+bed);
		System.out.println("bathroom:"+bathroom);
		System.out.println("inDate:"+inDate);
		System.out.println("outDate:"+outDate);
		System.out.println("x:"+x);
		System.out.println("y:"+y);
		
		/////////////////////////////////////////
		
		//공간 편의시설(space_fac)
		String wifi = req.getParameter("wifi");
		String tv = req.getParameter("tv");
		String kitchen = req.getParameter("kitchen");
		String wm = req.getParameter("wm");
		String aircon = req.getParameter("aircon");
		String canpark = req.getParameter("canpark");
		String canfreepark = req.getParameter("canfreepark");
		String swim = req.getParameter("swim");
		String bbq = req.getParameter("bbq");
		String pooltable = req.getParameter("pooltable");
		String fireplace = req.getParameter("fireplace");
		String firealarm = req.getParameter("firealarm");
		String aidkit = req.getParameter("aidkit");
		String firearm = req.getParameter("firearm");
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
		
		//////////////////////////////////////////////
		
		//할인등록
		String[] dcRatio = req.getParameterValues("dcRatio");
		String[] disName = req.getParameterValues("disName");
		System.out.println("dcRatio:"+dcRatio);
		System.out.println("disName:"+disName);
		////////////////////////////////////////////////////
		
			System.out.println("ddddddddd");
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
			//spaceVo.setVStatus(Integer.parseInt(vStatus));
			//spaceVo.setStatus(Integer.parseInt(status));
			spaceVo.setMemno(11);
			spaceDao.addOne(spaceVo);
		
		//LastInsertId
		int lastInsert = spaceDao.getLastInsert();
		System.out.println("마지막 번호 : " + lastInsert);
//		
//		
//		if(detailType!=null&&detail!=null&&maxGuest!=null&&bed!=null&&bathroom!=null&&inOutDate!=null) {
//			System.out.println("ddddddddd");
//			spaceDetailVo.setSpaceno(lastInsert);
//			spaceDetailVo.setType(detailType);
//			spaceDetailVo.setDetail(detail);
//			spaceDetailVo.setMaxGuest(Integer.parseInt(maxGuest));
//			spaceDetailVo.setBed(Integer.parseInt(bed));
//			spaceDetailVo.setBathroom(Integer.parseInt(bathroom));
//			spaceDetailVo.setInDate(inDate);
//			spaceDetailVo.setOutDate(outDate);
//			spaceDetailVo.setX(x);
//			spaceDetailVo.setY(y);
//		}
//		spcaeDetailDao.addOne(spaceDetailVo); 
//		
//		//공간 이미지(space_image)
//		//업로드할 경로
//		String saveFolder = "C:\\Users\\User\\git\\spaceHub\\src\\main\\webapp\\upload";
//		//첨부파일 크기
//		int size = 1024*1024*30;
//		try {
//			MultipartRequest mr = new MultipartRequest(req, saveFolder, size, "UTF-8", new DefaultFileRenamePolicy());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		String[] path = req.getParameterValues("path");
//		if(path!=null) {
//			
//			spaceImageVo.setSpaceno(lastInsert);
//			spaceImageVo.setImgno(1);
//			for(int i=0; i<path.length; i++) {
//				spaceImageVo.setPath(path[i]);
//				spaceImageVo.setSeq(i);
//			}
//		}
//		spcaeImageDao.addOne(spaceImageVo);
//		
//		spaceFacVo.setSpaceno(lastInsert);
//		if(wifi!=null) spaceFacVo.setWifi(Integer.parseInt(wifi));
//		if(tv!=null) spaceFacVo.setTv(Integer.parseInt(tv));
//		if(kitchen!=null) spaceFacVo.setKitchen(Integer.parseInt(kitchen));
//		if(wm!=null) spaceFacVo.setWm(Integer.parseInt(wm));
//		if(aircon!=null) spaceFacVo.setAircon(Integer.parseInt(aircon));
//		if(canpark!=null) spaceFacVo.setCanpark(Integer.parseInt(canpark));
//		if(canfreepark!=null) spaceFacVo.setCanfreepark(Integer.parseInt(canfreepark));
//		if(swim!=null) spaceFacVo.setSwim(Integer.parseInt(swim));
//		if(bbq!=null) spaceFacVo.setBbq(Integer.parseInt(bbq));
//		if(pooltable!=null) spaceFacVo.setPooltable(Integer.parseInt(pooltable));
//		if(fireplace!=null) spaceFacVo.setFireplace(Integer.parseInt(fireplace));
//		if(firealarm!=null) spaceFacVo.setFirealarm(Integer.parseInt(firealarm));
//		if(aidkit!=null) spaceFacVo.setAidkit(Integer.parseInt(aidkit));
//		if(firearm!=null) spaceFacVo.setFirearm(Integer.parseInt(firearm));
//		spcaeFacDao.addOne(spaceFacVo);
//		
//		
//		//System.out.println(dcRatio);
//		if(dcRatio!=null&&disName!=null) {
//			for(int i=0; i<dcRatio.length; i++)	{
//				DiscountVO discountVo = new DiscountVO();
//				discountVo.setSpaceno(lastInsert);
//				discountVo.setName(disName[i]);
//				discountVo.setDcratio(Integer.parseInt(dcRatio[i]));
//				discountDao.addOne(discountVo);
//			}
//		}
//		
//		spaceDao.close();
//		spcaeDetailDao.close();
//		spcaeImageDao.close();
//		spcaeFacDao.close();
//		discountDao.close();
		
		return "/spaceHub/home";
	}
}// class end
