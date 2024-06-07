package com.example.demo.controller;

import com.example.demo.dto.request.ForgetPasswordRequest;
import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.OtpRequest;
import com.example.demo.dto.request.SignupRequest;
import com.example.demo.dto.response.JwtResponse;
import com.example.demo.entity.Otp;
import com.example.demo.entity.User;
import com.example.demo.sercurity.JwtUtils;
import com.example.demo.service.ImageService;
import com.example.demo.service.OtpService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    OtpService otpService;

    @Autowired
    ImageService imageService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            User user = userService.findUserByEmail(loginRequest.getEmail());
            Otp otp = otpService.createOtp(user.getEmail());
            otp.setCreateDate(new Date());
            otp.setCreateUserName(user.getUserName());
            otpService.saveOtp(otp);
            return ResponseEntity.ok(otp.getOtpKey());
        } catch(Exception e){
            return ResponseEntity.badRequest()
                    .body("Incorrect password or email");
        }
    }

    @PostMapping("/otp")
    public ResponseEntity<?> authenticateUserByOtp(@Valid @RequestBody OtpRequest otpRequest) {
        Optional<Otp> otp = otpService.findByEmail(otpRequest.getEmail());
        if(otp.isPresent() && (otp.get().getDate().getTime()+300000) > new Date().getTime() && Objects.equals(otp.get().getOtpKey(), otpRequest.getKey())){
            User user = userService.findUserByEmail(otpRequest.getEmail());
            JwtResponse jwtResponse = new JwtResponse(jwtUtils.generateJwtToken(user.getEmail()),
                    user.getId(),
                    user.getUserName(),
                    user.getEmail());
            return ResponseEntity.ok().body(jwtResponse);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Error: OTP is expired!");
        }
    }

    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerUser(@Valid @ModelAttribute SignupRequest signUpRequest) {
        try{
            if (userService.checkIfExistByEmail(signUpRequest.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body("Error: Email is already in use!");
            }

            // Create new user's account
            User user = new User(signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()),
                    signUpRequest.getUsername(),
                    signUpRequest.getBirthday());

            user.setCreateDate(new Date());

            if(signUpRequest.getMultipartFile() != null){
                user.setUserAvatarLink(imageService.uploadImage(signUpRequest.getMultipartFile()));
            }
            user.setUserJob(signUpRequest.getJob());

            userService.saveUser(user);

            return ResponseEntity.ok().body("User registered successfully!");
        }catch(Exception e){
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/forgetP")
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody ForgetPasswordRequest forgetPasswordRequest) {
        if(userService.checkIfExistByEmail(forgetPasswordRequest.getEmail())){
            User user = userService.findUserByEmail(forgetPasswordRequest.getEmail());
            return ResponseEntity.ok(new JwtResponse(jwtUtils.generateJwtToken(user.getEmail()),
                    user.getId(),
                    user.getUserName(),
                    user.getEmail()));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is not exist!");
        }
    }

    @PutMapping("/changeP")
    public ResponseEntity<?> changePassword(@Valid @RequestBody LoginRequest loginRequest) {
        try{
            User user = userService.findUserByEmail(loginRequest.getEmail());
            user.setPassword(loginRequest.getPassword());
            user.setPassword(encoder.encode(user.getPassword()));
            user.setUpdateDate(new Date());
            user.setUpdateUserName(user.getUserName());
            userService.saveUser(user);
            return ResponseEntity.ok().body("Password change successfully!");
        } catch (Exception e){
            return ResponseEntity.badRequest()
                    .body("Server Error");
        }
    }
}