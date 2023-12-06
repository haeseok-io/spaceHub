package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageVO {
	private int messageno;
	private int bno;
	private String contents;
	private String regdate;
	private int status;
	private String ip;
	private int spaceno;
	private int memno;
}
