package com.project.app.api.utils.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransReview {
	private Integer spam;
	private Integer completed;
	private Integer sentiment;
	private String tag1;
	private String tag2;
	private String tag3;
	private String tag4;
	private String tag5;
}
