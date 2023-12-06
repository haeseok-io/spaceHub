package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResevSpaceVO {
	private int reservno;
	private String checkin;
	private String checkout;
	private String name;
	private String phone;
	private int price;
	private int guest;
	private int dcratio;
	private String regdate;
	private int status;
	private String ip;
	private int spaceno;
	private int memno;
	private String type;
	private String loc;
	private String subject;
	private String post;
	private String addr;
	private String path;
	private int seq;
}
