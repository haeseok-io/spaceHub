package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpaceListVO {
	private int spaceno;
	private String loc;
	private String subject;
	private String addr;
	private int price;
	private int memno;
	private String inDate;
	private String outDate;
	private	String path;
	private float rating;
	
	private String priceFormat;
	private boolean userJjimStatus;
}
