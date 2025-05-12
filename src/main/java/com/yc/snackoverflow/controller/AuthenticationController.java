package com.yc.snackoverflow.controller;

import com.yc.snackoverflow.data.auth.AuthenticationRequest;
import com.yc.snackoverflow.data.auth.AuthenticationRes;
import com.yc.snackoverflow.data.auth.RegisterReq;
import com.yc.snackoverflow.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yc
 * @create 2024 - 07 - 23 - 上午 10:35
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthenticationRes login(@RequestBody RegisterReq registerRequestData) {
        return authenticationService.register(registerRequestData);
    }

    @PostMapping("/authenticate")
    public AuthenticationRes authenticate(@RequestBody AuthenticationRequest authenticationRequestData) {
        return authenticationService.authenticate(authenticationRequestData);
    }

    @GetMapping("/renew")
    public AuthenticationRes renewToken() {
        return authenticationService.renewToken();
    }
}
