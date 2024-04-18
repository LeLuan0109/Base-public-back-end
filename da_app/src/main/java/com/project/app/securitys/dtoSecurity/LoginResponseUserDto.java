package com.project.app.securitys.dtoSecurity;

import com.project.app.api.domain.Organization;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseUserDto {
	private Long id;
	private String fullName;
	private String userName;
	private Integer gender;
	private String birthday;
	private String email;
	private String phone;
	private String address;
	private String role;
	private List<Organization> organizations = new ArrayList<>();
}
