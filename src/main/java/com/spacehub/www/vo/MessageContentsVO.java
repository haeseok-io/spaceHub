package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageContentsVO {
	private int messageno;
	private int bno;
	private String contents;
	private String regdate;
	private int status;
	private String ip;
	private int spaceno;
	private int memno;
	private String mname;
	private String mnickname;
	private String mprofileImg;
}
