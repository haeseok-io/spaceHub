package com.spacehub.www.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentVO {
	private int paymentno;
	private String approvalNum;
	private String cardNum;
	private String name;
	private String email;
	private int price;
	private String payDate;
	private int status;
	private int reservno;
}
