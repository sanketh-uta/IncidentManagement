package com.sanbro.AuthenticationService.controller;

import com.sanbro.AuthenticationService.dto.LoginDto;
import com.sanbro.AuthenticationService.dto.UserDto;
import com.sanbro.AuthenticationService.service.JwtUtility;
import com.sanbro.AuthenticationService.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtility jwtUtility;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto){
        log.info("registering the details");
        return userService.registerUser(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto){
        log.info("Request is hittting "+loginDto);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword());

        Authentication auth = authenticationManager.authenticate(authentication);

        // if authentication is success then create the JWT token here after extracting the user from the authentication object
        String token = jwtUtility.createJwtToken(auth);
        return ResponseEntity.ok(token);

    }

    @GetMapping("/test")
    public String checkLoginWithJwt(){
        return "success";
    }
}
