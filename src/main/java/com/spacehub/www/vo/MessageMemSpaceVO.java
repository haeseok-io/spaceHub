package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageMemSpaceVO {
	private int memno;
	private String name;
	private String regdate;
	private int messageno;
	private int bno;
	private int spaceno;
	private String subject;
	private int reservno;

}
