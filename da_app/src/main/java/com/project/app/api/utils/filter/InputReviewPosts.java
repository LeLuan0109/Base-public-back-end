package com.project.app.api.utils.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputReviewPosts {
	private TransReview input;
	private List<PostReview> posts;
	private String creator;
	private Integer Status;
	private String approvedBy;
	private Integer orgId;

}
