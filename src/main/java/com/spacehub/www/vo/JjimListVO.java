package com.spacehub.www.vo;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JjimListVO {
	int memno;
	String subject;
	int spaceno;
	int bed;
	String inDate;
	String outDate;
	int price;
	int jjimno;
	
	ArrayList<HostSpaceImageVO> imgList;
}
