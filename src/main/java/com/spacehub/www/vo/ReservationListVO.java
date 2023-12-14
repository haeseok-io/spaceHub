package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationListVO {
	private int reservno;
	private String checkin;
	private String checkout;
	private int price;
	private int guest;
	private int dcratio;
	private String regdate;
	private int status;
	private int spaceno;
	private String spaceType;
	private String spaceLoc;
	private String spaceSubject;
	private String spaceAddr;
	private int spacePrice;
	private String spaceImage;
}
