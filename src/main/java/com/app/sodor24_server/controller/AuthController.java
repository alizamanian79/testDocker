package com.app.sodor24_server.controller;

import com.app.sodor24_server.dto.request.LoginDto;
import com.app.sodor24_server.dto.request.RegisterDto;
import com.app.sodor24_server.model.User;
import com.app.sodor24_server.service.UserServiceImpl;
import com.app.sodor24_server.util.Jwt.JwtResponseDto;
import com.app.sodor24_server.util.Jwt.JwtService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // Register user
    @PostMapping("/auth/register")
    public User register(@RequestBody RegisterDto req) {
        return userService.registerUser(req);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto req) {
       Authentication auth =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                req.getUsername(), req.getPassword()
        ));
       SecurityContextHolder.getContext().setAuthentication(auth);
       JwtResponseDto res = jwtService.generateToken(auth.getName(),auth.getAuthorities());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }





}
