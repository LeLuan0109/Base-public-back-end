package com.project.app.api.utils.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.project.app.api.utils.DateUtils.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterTime {
	private String creator;
	private String name;
	private Long created;
	@DateTimeFormat(pattern = NORMAL_DATE)
	private LocalDate pubDate;
	private Integer week;
	private Integer month;
	private Integer year;
}
