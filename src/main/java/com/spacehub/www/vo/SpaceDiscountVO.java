package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SpaceDiscountVO {
	private int spaceno;
	private String type;
	private String loc;
	private String subject;
	private String post;
	private String addr;
	private int price;
	private String regdate;
	private String ip;
	private int vStatus;
	private int status;
	private int memno;
	private int disno;
	private String name;
	private int dcratio;
	private int reservno;
	private String checkin;
	private String checkout;
	private String phone;
	private int guest;
}
