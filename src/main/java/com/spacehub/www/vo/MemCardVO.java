package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemCardVO {
	int mcardno;
	String cardNum;
	String eDate;
	int cvc;
	String post;
	String loc;
	int memno;
}
