package com.project.app.securitys.serviceSecurity.user;

//import com.project.shopapp.components.JwtTokenUtils;
//import com.project.shopapp.components.LocalizationUtils;
//import com.project.shopapp.dtos.SinginUserDTO;
//import com.project.shopapp.exceptions.DataNotFoundException;
//import com.project.shopapp.exceptions.PermissionDenyException;
//import com.project.shopapp.models.*;
//import com.project.shopapp.repositories.RoleRepository;
//import com.project.shopapp.repositories.UserRepository;
//import com.project.shopapp.utils.MessageKeys;

import com.project.app.api.domain.Organization;
import com.project.app.api.repository.OrganizationRepository;
import com.project.app.securitys.componentSecurity.LocalizationUtils;
import com.project.app.securitys.dtoSecurity.LoginResponseUserDto;
import com.project.app.securitys.modelSecurity.Role;
import com.project.app.securitys.repositorieSecurity.RoleRepository;
import com.project.app.securitys.componentSecurity.JwtTokenUtils;
import com.project.app.securitys.dtoSecurity.SinginUserDTO;
import com.project.app.securitys.exceptionSecurity.DataNotFoundException;
import com.project.app.securitys.modelSecurity.User;
import com.project.app.securitys.repositorieSecurity.UserRepository;
import com.project.app.securitys.responseSecurity.LoginResponse;
import com.project.app.securitys.responseSecurity.UserResponse;
import com.project.app.securitys.utilSecurity.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final OrganizationRepository orgRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final LocalizationUtils localizationUtils;
    @Override
    @Transactional
    public User createUser(@Valid SinginUserDTO singinUserDTO) throws Exception {
        //register user
//        String phoneNumber = singinUserDTO.getPhoneNumber();
        // Kiểm tra xem số điện thoại đã tồn tại hay chưa
//        if(this.userRepository.existsByPhoneNumber(phoneNumber)) {
//            throw new DataIntegrityViolationException("Số điện thoại đã tồn tại");
//        }
      Role role = this.roleRepository.findById(singinUserDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException(
                        this.localizationUtils.getLocalizedMessage(MessageKeys.ROLE_DOES_NOT_EXISTS)));
         /* if(role.getName().toUpperCase().equals(Role.ADMIN)) {
            throw new PermissionDenyException("Không được phép đăng ký tài khoản Admin");
        }*/
        //convert from singinUserDTO => user
        List<Organization> organizations = this.orgRepository.filterOrganization(singinUserDTO.getOrganization());
        User newUser = User.builder()
                .username(singinUserDTO.getUserName())
                .fullName(singinUserDTO.getFullName())
                .phoneNumber(singinUserDTO.getPhone())
                .address(singinUserDTO.getAddress())
                .email(singinUserDTO.getEmail())
                .organizations(organizations)
                .active(true)
                .build();

        newUser.setRole(role);

        // Kiểm tra nếu có accountId, không yêu cầu password
//        if (singinUserDTO.getFacebookAccountId() == 0 && singinUserDTO.getGoogleAccountId() == 0) {
            String password = singinUserDTO.getPassword();
            String encodedPassword = this.passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
//        }
        return this.userRepository.save(newUser);
    }

    @Override
    public LoginResponse login(
            String username,
            String password
    ) throws Exception {
        Optional<User> optionalUser = this.userRepository.findByUsername(username);
        if(optionalUser.isEmpty()) {
            throw new DataNotFoundException(this.localizationUtils.getLocalizedMessage(MessageKeys.USER_INVALID));
        }
        //return optionalUser.get();//muốn trả JWT token ?
        User existingUser = optionalUser.get();
        //check password
//        if (existingUser.getFacebookAccountId() == 0
//                && existingUser.getGoogleAccountId() == 0) {
//            if(!passwordEncoder.matches(password, existingUser.getPassword())) {
//                throw new BadCredentialsException(localizationUtils.getLocalizedMessage(MessageKeys.WRONG_PHONE_PASSWORD));
//            }
//        }
//        Optional<Role> optionalRole = roleRepository.findById(roleId);
//        if(optionalRole.isEmpty() || !roleId.equals(existingUser.getRole().getId())) {
//            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.ROLE_DOES_NOT_EXISTS));
//        }
//        if(!optionalUser.get().isActive()) {
//            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.USER_IS_LOCKED));
//        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password,
                existingUser.getAuthorities()
        );

        //authenticate with Java Spring security
        return  LoginResponse.builder()
                    .token(this.jwtTokenUtil.generateToken(existingUser))
                .build();
    }

    @Override
    public UserResponse getMe() throws Exception {
        Optional<User> optionalUser = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            optionalUser =     this.userRepository.findByUsername(authentication.getName()); // Trả về username của user hiện tại
        }
        return UserResponse.fromUser(optionalUser.get());
    }


//    @Transactional
//    @Override
//    public User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception {
//        // Find the existing user by userId
//        User existingUser = userRepository.findById(userId)
//                .orElseThrow(() -> new DataNotFoundException("User not found"));
//
//        // Check if the phone number is being changed and if it already exists for another user
//        String newPhoneNumber = updatedUserDTO.getPhoneNumber();
//        if (!existingUser.getPhoneNumber().equals(newPhoneNumber) &&
//                userRepository.existsByPhoneNumber(newPhoneNumber)) {
//            throw new DataIntegrityViolationException("Phone number already exists");
//        }
//
//        // Update user information based on the DTO
//        if (updatedUserDTO.getFullName() != null) {
//            existingUser.setFullName(updatedUserDTO.getFullName());
//        }
//        if (newPhoneNumber != null) {
//            existingUser.setPhoneNumber(newPhoneNumber);
//        }
//        if (updatedUserDTO.getAddress() != null) {
//            existingUser.setAddress(updatedUserDTO.getAddress());
//        }
//        if (updatedUserDTO.getDateOfBirth() != null) {
//            existingUser.setDateOfBirth(updatedUserDTO.getDateOfBirth());
//        }
//        if (updatedUserDTO.getFacebookAccountId() > 0) {
//            existingUser.setFacebookAccountId(updatedUserDTO.getFacebookAccountId());
//        }
//        if (updatedUserDTO.getGoogleAccountId() > 0) {
//            existingUser.setGoogleAccountId(updatedUserDTO.getGoogleAccountId());
//        }
//
//        // Update the password if it is provided in the DTO
//        if (updatedUserDTO.getPassword() != null
//                && !updatedUserDTO.getPassword().isEmpty()) {
//            if(!updatedUserDTO.getPassword().equals(updatedUserDTO.getRetypePassword())) {
//                throw new DataNotFoundException("Password and retype password not the same");
//            }
//            String newPassword = updatedUserDTO.getPassword();
//            String encodedPassword = passwordEncoder.encode(newPassword);
//            existingUser.setPassword(encodedPassword);
//        }
//        //existingUser.setRole(updatedRole);
//        // Save the updated user
//        return userRepository.save(existingUser);
//    }
//
//    @Override
//    public User getUserDetailsFromToken(String token) throws Exception {
//        if(this.jwtTokenUtil.isTokenExpired(token)) {
//            throw new Exception("Token is expired");
//        }
//        String phoneNumber = this.jwtTokenUtil.extractPhoneNumber(token);
//        Optional<User> user = this.userRepository.findByPhoneNumber(phoneNumber);
//
//        if (user.isPresent()) {
//            return user.get();
//        } else {
//            throw new Exception("User not found");
//        }
//    }
}








