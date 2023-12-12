package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpaceVO {
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
	private String imgPath;
}
