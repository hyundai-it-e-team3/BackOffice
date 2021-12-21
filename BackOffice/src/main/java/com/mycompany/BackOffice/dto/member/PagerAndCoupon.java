package com.mycompany.BackOffice.dto.member;

import java.util.List;

import com.mycompany.BackOffice.dto.Pager;

import lombok.Data;

@Data
public class PagerAndCoupon {
	private Pager pager;
	private List<Coupon> coupon;
}
