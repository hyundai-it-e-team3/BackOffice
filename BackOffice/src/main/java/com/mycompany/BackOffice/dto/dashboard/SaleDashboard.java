package com.mycompany.BackOffice.dto.dashboard;

import java.util.Date;

import lombok.Data;

@Data
public class SaleDashboard {
	Date sdate;
	String brand;
	int sale;
	String squater;
}
