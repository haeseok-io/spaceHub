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
	private String contents;
	private String regdate;
	private int status;
	private int spaceno;
	private int memno;
	
	private int ownStatus; // 본인게시물 여부
}
