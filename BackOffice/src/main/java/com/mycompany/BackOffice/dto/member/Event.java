package com.mycompany.BackOffice.dto.member;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Event {
	private int eventSeq;
	private String name;
	private char eventType;
	private String content;
	private String image;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date regDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expDate;
	private char status;
	private String couponId;
}
