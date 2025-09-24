package com.app.sodor24_server.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {


    @PreAuthorize("hasRole('USER') or hasAuthority('EDIT')")
    @GetMapping
    public Authentication getAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth;
    }

}
