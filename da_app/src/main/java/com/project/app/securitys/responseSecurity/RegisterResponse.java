package com.project.app.securitys.responseSecurity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.app.securitys.modelSecurity.User;
import lombok.*;

import java.util.Collection;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("authorization")
    private Collection<?>authorization;

}
