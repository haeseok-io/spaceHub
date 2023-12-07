package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MCouponVO {
	private int memno;
	private int couponno;
	private String cDate;
	private String eDate;
	private int status;
	private int reservno;
	private String name;
	private int dcratio;
}
