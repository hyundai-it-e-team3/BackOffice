package com.mycompany.BackOffice.dto.member;

import lombok.Data;

@Data
public class SearchTypeEvent {
	private String searchName;
	private String searchStartDate;
	private String searchLastDate;
	private char searchType;
	private char searchStatus;
}
