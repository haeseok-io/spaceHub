package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewListVO {
	private int reviewno;
	private String subject;
	private String contents;
	private int rating;
	private String regdate;
	private String name;
}
