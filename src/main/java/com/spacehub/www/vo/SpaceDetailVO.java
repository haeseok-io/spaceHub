package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceDetailVO {
	private int spaceno;
	private String type;
	private String detail;
	private int maxGuest;
	private int bed;
	private int bathroom;
	private String inDate;
	private String outDate;
	private String x;
	private String y;
}
