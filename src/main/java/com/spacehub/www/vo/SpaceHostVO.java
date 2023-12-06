package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpaceHostVO {
	private int reservno;
	private int spaceno;
	private String subject;
	private int price;
	private int memno;
	private String name;
}
