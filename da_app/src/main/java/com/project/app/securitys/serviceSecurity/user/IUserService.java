package com.project.app.securitys.serviceSecurity.user;

//import com.project.shopapp.dtos.SinginUserDTO;
//import com.project.shopapp.models.User;
import com.project.app.securitys.dtoSecurity.SinginUserDTO;
import com.project.app.securitys.modelSecurity.User;
import com.project.app.securitys.responseSecurity.LoginResponse;
import com.project.app.securitys.responseSecurity.UserResponse;
import jakarta.validation.Valid;

import java.util.Collection;
//import org.springframework.security.core.userdetails.User;

//import java.com.project.shopapp.dtos.SinginUserDTO;
//import java.com.project.shopapp.models.User;

public interface IUserService {
    User createUser(@Valid SinginUserDTO singinUserDTO) throws Exception;
    LoginResponse login(String phoneNumber, String password ) throws Exception;
    UserResponse getMe() throws Exception;
}
