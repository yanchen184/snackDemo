package com.yc.snackoverflow.service;

import com.yc.snackoverflow.data.auth.AuthenticationRequest;
import com.yc.snackoverflow.data.auth.AuthenticationRes;
import com.yc.snackoverflow.data.auth.RegisterReq;
import com.yc.snackoverflow.exception.WebErrorEnum;
import com.yc.snackoverflow.model.Member;
import com.yc.snackoverflow.reposity.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Yc
 * @create 2024 - 07 - 23 - 上午 10:41
 */

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final MemberDao memberDao;
    private final MemberService memberService;
    private final JwtTokenProvider jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationRes register(RegisterReq registerRequestData) {
        Member member = memberService.generateMember(registerRequestData);
        checkUserExists(registerRequestData);
        memberDao.save(member);
        jwtService.generateToken(member);
        return AuthenticationRes.builder()
                .token(jwtService.generateToken(member))
                .build();
    }

    private void checkUserExists(RegisterReq registerRequestData) {
        if (!memberDao.list(List.of(registerRequestData.getUsername())).isEmpty()) {
            throw WebErrorEnum.USER_ALREADY_EXISTS.exception();
        }
        if (memberDao.findByEmail(registerRequestData.getEmail()).isPresent()) {
            throw WebErrorEnum.EMAIL_ALREADY_EXISTS.exception();
        }
    }

    public AuthenticationRes authenticate(AuthenticationRequest authenticationRequestData) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequestData.getEmail(),
                        authenticationRequestData.getPassword())
        );
        return AuthenticationRes.builder()
                .token(jwtService.generateToken(memberDao.findByEmail(authenticationRequestData.getEmail()).get()))
                .build();
    }

    public AuthenticationRes renewToken() {
        return AuthenticationRes.builder()
                .build();
    }
}
