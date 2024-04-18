package com.project.app.securitys.responseSecurity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.app.api.domain.Organization;
import com.project.app.securitys.modelSecurity.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("fullName")
	private String fullName;

	@JsonProperty("username")
	private String username;

	@JsonProperty("email")
	private String email;

	@JsonProperty("phone")
	private String phoneNumber;

	@JsonProperty("address")
	private String address;

	@JsonProperty("birthday")
	private String birthday;

	@JsonProperty("active")
	private Integer active;

	@JsonProperty("gender")
	private String gender;

	@JsonProperty("avatar")
	private String avatar;

	@JsonProperty("roles")
	private String role;

	@JsonProperty("organization")
	private List<OrgReponse> organizations;


	public static UserResponse fromUser(User user) {
		List<OrgReponse> args = new ArrayList<OrgReponse>();
		for (Organization organization : user.getOrganizations()){
			args.add(OrgReponse.fromUser(organization));

		}

		return UserResponse.builder()
				.id(user.getId())
				.fullName(user.getFullName())
				.phoneNumber(user.getPhoneNumber())
				.address(user.getAddress())
				.active(user.isActive() == true ? 1 : 0)
				.email(user.getEmail())
				.role(user.getRole().getName())
				.username(user.getUsername())
				.birthday(user.getBirthday())
				.organizations(args)
                .gender(null)
                .avatar(null)
				.build();
	}
}
