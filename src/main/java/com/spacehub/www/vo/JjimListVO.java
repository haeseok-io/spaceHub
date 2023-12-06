package com.spacehub.www.vo;

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
	String path;
	String subject;
	int spaceno;
	int bed;
	String inDate;
	String outDate;
	int price;
	int jjimno;
}
