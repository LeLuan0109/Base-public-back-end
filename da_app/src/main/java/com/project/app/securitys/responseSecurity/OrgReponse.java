package com.project.app.securitys.responseSecurity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.app.api.domain.Organization;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrgReponse {

		@JsonProperty("orgId")
		private Integer id;

		private String logo;
		private String companyName;
		private String website;
		private String phone;
		private String  email;
		private String address;
		private String taxCode;

	public static OrgReponse fromUser  (Organization org) {
		return OrgReponse.builder()
				.id(org.getId())
				.address(org.getAddress())
				.companyName(org.getCompanyName())
				.phone(org.getPhone())
				.email(org.getEmail())
				.logo(null)
				.taxCode(null)
				.website(null)
				.build();
	};

}
