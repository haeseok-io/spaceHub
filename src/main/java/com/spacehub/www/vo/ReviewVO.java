package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewVO {
	private int reviewno;
	private String subject;
	private String contents;
	private int rating;
	private String regdate;
	private int status;
	private String ip;
	private int memno;
	private int reservno;
}
