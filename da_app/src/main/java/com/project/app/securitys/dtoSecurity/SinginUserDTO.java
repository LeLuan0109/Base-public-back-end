package com.project.app.securitys.dtoSecurity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SinginUserDTO {
    private String userName;
    private String password;
    private String retypePassword;
    private String avatar;
    private String email;
    private String address;
    private String fullName;
    private String phone;
    private Integer gender;
    private Integer birthday;
    private Long roleId;
    private List<Integer> organization;

}
