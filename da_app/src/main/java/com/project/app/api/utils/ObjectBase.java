package com.project.app.api.utils;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ObjectBase implements Serializable {

	@Column(name = "org_id")
	private Integer orgId;

	@Column(name = "post_id")
	private Integer postId;

}
