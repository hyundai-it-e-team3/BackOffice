package com.mycompany.BackOffice.dto.order;

import java.util.Date;

import lombok.Data;

@Data
public class OrderInfo {
	String orderId;
	Date orderDate;
	String memberId;
	String state;
	int totalProduct;
	int state1;
	int state2;
	int state3;
	int state4;
	int state5;
	int state6;
	int totalPrice;
	String payType;
}
