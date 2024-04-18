package com.project.app.api.utils.filter;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.project.app.api.utils.DateUtils.*;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterBase<T> {

	private T input;
	private int pageIndex;
	private int pageSize;
	private String sort;
	private Integer orgId;
	private Integer id;
	private String creator;
	private String name;
	private Long created;
	@DateTimeFormat(pattern = NORMAL_DATE)
	private LocalDate pubDate;
	private Integer week;
	private Integer month;
	private Integer year;

}
