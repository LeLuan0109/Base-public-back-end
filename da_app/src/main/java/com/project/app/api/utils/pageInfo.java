package com.project.app.api.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class pageInfo {
	private int pageSize;
	private int pageIndex;
	private int startCursor;
	private boolean hasNextPage;
	private int  TotalPage;
}
