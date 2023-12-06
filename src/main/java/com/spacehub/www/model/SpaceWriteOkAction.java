package com.spacehub.www.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		//ip method
		public static String getClientIpAddr(HttpServletRequest request) {
		    String ip = request.getHeader("X-Forwarded-For");
		 
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		        ip = request.getHeader("Proxy-Client-IP");
		    }
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		        ip = request.getHeader("WL-Proxy-Client-IP");
		    }
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		        ip = request.getHeader("HTTP_CLIENT_IP");
		    }
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		    }
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		        ip = request.getRemoteAddr();
		    }
		 
		    return ip;
		}

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		SpaceDAO dao1 = new SpaceDAO();
		SpaceDetailDAO dao2 = new SpaceDetailDAO();
		SpaceImageDAO dao3 = new SpaceImageDAO();
		SpaceFacDAO dao4 = new SpaceFacDAO();
		DiscountDAO dao5 = new DiscountDAO();
		SpaceVO vo1 = new SpaceVO();
		SpaceDetailVO vo2 = new SpaceDetailVO();
		SpaceImageVO vo3 = new SpaceImageVO();
		SpaceFacVO vo4 = new SpaceFacVO();
		DiscountVO vo5 = new DiscountVO();
		
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
		//String ip = req.getParameter("ip");		
		//String vStatus = req.getParameter("vStatus");		
		//String status = req.getParameter("status");		
		
		
		if(type!=null&&loc!=null&&subject!=null&&post!=null&&addr!=null&&price!=null) {
			vo1.setType(type);
			vo1.setLoc(locSplit[0] + locSplit[1]);
			vo1.setSubject(subject);
			vo1.setPost(post);
			vo1.setAddr(addr);
			vo1.setPrice(Integer.parseInt(price));
			//vo1.setRegdate(regdate);
			vo1.setIp(getClientIpAddr(req));
			//vo1.setVStatus(Integer.parseInt(vStatus));
			//vo1.setStatus(Integer.parseInt(status));
		}
		dao1.addOne(vo1);
		
		//LastInsertId
		int lastInsert = dao1.getLastInsert();
		
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
		if(detailType!=null&&detail!=null&&maxGuest!=null&&bed!=null&&bathroom!=null&&inOutDate!=null) {
			vo2.setSpaceno(lastInsert);
			vo2.setType(detailType);
			vo2.setDetail(detail);
			vo2.setMaxGuest(Integer.parseInt(maxGuest));
			vo2.setBed(Integer.parseInt(bed));
			vo2.setBathroom(Integer.parseInt(bathroom));
			vo2.setInDate(inDate);
			vo2.setOutDate(outDate);
			vo2.setX(x);
			vo2.setY(y);
		}
		dao2.addOne(vo2);
		
		//공간 이미지(space_image)
		String[] path = req.getParameterValues("path");
		if(path!=null) {
			vo3.setImgno(1);
			for(int i=0; i<path.length; i++) {
				vo3.setPath(path[i]);
				vo3.setSeq(i);
			}
			vo3.setSpaceno(11);
		}
		dao3.addOne(vo3);
		
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
		
		if(wifi!=null&&tv!=null&&kitchen!=null&&wm!=null&&aircon!=null&&canpark!=null&&canfreepark!=null&&swim!=null&&bbq!=null&&pooltable!=null&&fireplace!=null&&firealarm!=null&&aidkit!=null&&firearm!=null) {
			vo4.setSpaceno(11);
			vo4.setWifi(Integer.parseInt(wifi));
			vo4.setTv(Integer.parseInt(tv));
			vo4.setKitchen(Integer.parseInt(kitchen));
			vo4.setWm(Integer.parseInt(wm));
			vo4.setAircon(Integer.parseInt(aircon));
			vo4.setCanpark(Integer.parseInt(canpark));
			vo4.setCanfreepark(Integer.parseInt(canfreepark));
			vo4.setSwim(Integer.parseInt(swim));
			vo4.setBbq(Integer.parseInt(bbq));
			vo4.setPooltable(Integer.parseInt(pooltable));
			vo4.setFireplace(Integer.parseInt(fireplace));
			vo4.setFirealarm(Integer.parseInt(firealarm));
			vo4.setAidkit(Integer.parseInt(aidkit));
			vo4.setFirearm(Integer.parseInt(firearm));
		} else if (wifi==null&&tv==null&&kitchen==null&&wm==null&&aircon==null&&canpark==null&&canfreepark==null&&swim==null&&bbq==null&&pooltable==null&&fireplace==null&&firealarm==null&&aidkit==null&&firearm==null) {
			vo4.setWifi(0);
			vo4.setTv(0);
			vo4.setKitchen(0);
			vo4.setWm(0);
			vo4.setAircon(0);
			vo4.setCanpark(0);
			vo4.setCanfreepark(0);
			vo4.setSwim(0);
			vo4.setBbq(0);
			vo4.setPooltable(0);
			vo4.setFireplace(0);
			vo4.setFirealarm(0);
			vo4.setAidkit(0);
			vo4.setFirearm(0);
		}
		dao4.addOne(vo4);
		
		//할인등록
		String[] dcRatio = req.getParameterValues("dcRatio");
		String[] disName = req.getParameterValues("disName");
		if(dcRatio!=null&&disName!=null) {
			vo5.setSpaceno(11);
			vo5.setDisno(11);
			for(int i=0; i<dcRatio.length; i++)	{
				vo5.setName(disName[i]);
				vo5.setDcratio(Integer.parseInt(dcRatio[i]));
			}
		}
		dao5.addOne(vo5);
		
		dao1.close();
		dao2.close();
		dao3.close();
		dao4.close();
		dao5.close();
		
		return "space?cmd=write";
	}
	
	
	
}// class end
