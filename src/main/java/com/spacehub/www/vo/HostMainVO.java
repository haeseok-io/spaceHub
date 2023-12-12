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
public class HostMainVO {
	int spaceno;
	String subject;
	int memno;
	
	ArrayList<SpaceImageVO> imgList;
}
