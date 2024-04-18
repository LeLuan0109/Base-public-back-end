package com.project.app.api.utils.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostReview  {
	private String id;
	private Integer detailId;
	private Long pubTime;
	private Long crawlTime;
}
