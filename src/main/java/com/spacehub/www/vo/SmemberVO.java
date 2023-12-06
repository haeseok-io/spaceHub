package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmemberVO {
	int memno;
	String email;
	String password;
	String name;
	String post;
	String addr;
	String accountNum;
	String regdate;
	int credits;
	int status;
}
