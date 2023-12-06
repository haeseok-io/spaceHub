package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpaceImageVO {
	private int imgno;
	private String path;
	private int seq;
	private int spaceno;
}
