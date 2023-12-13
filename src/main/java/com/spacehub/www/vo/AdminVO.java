package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminVO {
	private int adminno;
	private String id;
	private String pw;
	private String name;
	private String duty;
	private String regdate;
}
