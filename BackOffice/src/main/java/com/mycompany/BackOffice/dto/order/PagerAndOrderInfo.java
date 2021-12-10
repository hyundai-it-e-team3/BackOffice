package com.mycompany.BackOffice.dto.order;

import java.util.List;

import com.mycompany.BackOffice.dto.Pager;

import lombok.Data;

@Data
public class PagerAndOrderInfo {
	private Pager pager;
	private List<OrderInfo> orderInfos;
}
