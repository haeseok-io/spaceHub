package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportVO {
	int reportno;
	String subject;
	String contents;
	String regdate;
	String ip;
	int spaceno;
	int memno;
	String spaceSubject;
}
