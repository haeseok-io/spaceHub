package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageListVO {
	private int messageno;
	private int bno;
	private String contents;
	private String regdate;
	private int status;
	
	private int spaceno; // 공간번호
	private String spaceName; // 공간이름
	private int spaceOwnStatus; // 공간 소유상태
	
	private int mmemno; // 최근 작성자 회원번호
	private String mname; // 최근 작성자 이름
	private String mnickname; // 최근 작성자 닉네임
	private String mprofileImg; // 최근 작성자 프로필 이미지 경로
	
	private int wmemno; // 최초 작성자 회원번호
	private String wname; // 최초 작성자 이름
	private String wnickname; // 최초 작성자 닉네임
	private String wprofileImg; // 최초 작성자 프로필 이미지 경로
	
	private int hmemno; // 호스트 회원번호
	private String hname; // 호스트 이름
	private String hnickname; // 호스트 닉네임
	private String hprofileImg; // 호스트 프로필 이미지 경로
}
