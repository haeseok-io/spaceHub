package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostSpaceImageVO {
	int imgno;
	String path;
	int seq;
	int spaceno;
	int memno;
}
