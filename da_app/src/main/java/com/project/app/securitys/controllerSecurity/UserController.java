package com.project.app.securitys.controllerSecurity;


import com.project.app.securitys.componentSecurity.LocalizationUtils;
import com.project.app.securitys.dtoSecurity.SinginUserDTO;
import com.project.app.securitys.dtoSecurity.UserLoginDTO;
import com.project.app.securitys.modelSecurity.User;
import com.project.app.securitys.responseSecurity.RegisterResponse;
import com.project.app.securitys.responseSecurity.Respon;
import com.project.app.securitys.responseSecurity.UserResponse;
import com.project.app.securitys.serviceSecurity.user.IUserService;
import com.project.app.securitys.utilSecurity.MessageKeys;
import com.project.app.securitys.responseSecurity.LoginResponse;
import io.swagger.models.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/author")
@RequiredArgsConstructor
//@CrossOrigin("*")

public class UserController {
    private final IUserService userService;
    private final LocalizationUtils localizationUtils;

    @PostMapping("/register")
    //can we register an "admin" user ?
    public ResponseEntity<RegisterResponse> createUser(
            @Valid @RequestBody SinginUserDTO singinUserDTO,
            BindingResult result
    ) {
        RegisterResponse registerResponse = new RegisterResponse();

        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();

            registerResponse.setMessage(errorMessages.toString());
            return ResponseEntity.badRequest().body(registerResponse);
        }

        if (!singinUserDTO.getPassword().equals(singinUserDTO.getRetypePassword())) {
            registerResponse.setMessage(this.localizationUtils.getLocalizedMessage(MessageKeys.PASSWORD_NOT_MATCH));
            return ResponseEntity.badRequest().body(registerResponse);
        }

        try {
            User user = this.userService.createUser(singinUserDTO);
            registerResponse.setMessage("Đăng ký tài khoản thành công");
            registerResponse.setAuthorization(user.getAuthorities());
            return ResponseEntity.ok(registerResponse);
        } catch (Exception e) {
            registerResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(registerResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Respon<LoginResponse>> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO
    ) throws Exception {
        // Kiểm tra thông tin đăng nhập và sinh token

            return ResponseEntity.ok(
                    Respon.<LoginResponse>builder()
                            .data(this.userService.login(
                                    userLoginDTO.getUserName(),
                                    userLoginDTO.getPassword()
                            ))
                            .build()
            );
    }

//    @PostMapping("/details")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
//    public ResponseEntity<UserResponse> getUserDetails(
//            @RequestHeader("Authorization") String authorizationHeader
//    ) {
//        try {
//            String extractedToken = authorizationHeader.substring(7); // Loại bỏ "Bearer " từ chuỗi token
//            User user = userService.getUserDetailsFromToken(extractedToken);
//            return ResponseEntity.ok(UserResponse.fromUser(user));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//    @PutMapping("/details/{userId}")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
//    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
//    public ResponseEntity<UserResponse> updateUserDetails(
//            @PathVariable Long userId,
//            @RequestBody UpdateUserDTO updatedUserDTO,
//            @RequestHeader("Authorization") String authorizationHeader
//    ) {
//        try {
//            String extractedToken = authorizationHeader.substring(7);
//            User user = userService.getUserDetailsFromToken(extractedToken);
//            // Ensure that the user making the request matches the user being updated
//            if (user.getId() != userId) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//            }
//            User updatedUser = userService.updateUser(userId, updatedUserDTO);
//            return ResponseEntity.ok(UserResponse.fromUser(updatedUser));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }

    @GetMapping("/getAuthor")
    public ResponseEntity<Respon<UserResponse>>  getMe() throws Exception {
        return ResponseEntity.ok(
                Respon.<UserResponse>builder()
                        .data( this.userService.getMe())
                        .build()
        );
    }
}
