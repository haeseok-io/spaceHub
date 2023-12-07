package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageListVO {
	private int messageno;
	private int bno;
	private String content;
	private String regdate;
	private int status;
	private int spaceno;
	private int memno;
	
	private String userType;
	private String statusHan;
	private String regdateFormat;
}
