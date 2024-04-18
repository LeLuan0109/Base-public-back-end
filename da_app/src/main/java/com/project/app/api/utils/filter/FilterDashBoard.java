package com.project.app.api.utils.filter;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterDashBoard {

	private Long startDate;
	private Long endDate;
	private String tag;
	private Long day;
	private  Integer week;
	private  Integer month;
	private Integer year;
	private String profileType;
	private  Integer top;
	private LocalDate startDateByDate;
	private LocalDate endDateByDate;
	private LocalDate dayByDate;

}
