package com.project.app.securitys.responseSecurity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.app.securitys.dtoSecurity.LoginResponseUserDto;
import com.project.app.securitys.dtoSecurity.UserLoginDTO;
import lombok.*;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    @JsonProperty("token")
    private String token;
}
