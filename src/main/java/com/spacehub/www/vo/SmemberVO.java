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
	String nickname;
	String profileImg;
	String post;
	String addr;
	String bankName;
	String accountNum;
	String regdate;
	int credits;
	int status;
}
