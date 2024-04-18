package com.project.app.api.utils.filter;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterPost {

	private Integer orgId;
	private Long startDate;
	private Long endDate;
	private List<String> sources;
	private List<Integer> sentiments;
	private String docType;
	private String tag;
	private String keyword;
	private String profileIds;
	private String domainIds;
	private String companyName;
	private Integer spam;
	private Integer completed;

}
