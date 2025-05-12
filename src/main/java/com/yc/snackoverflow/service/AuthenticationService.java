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
        // Check if user exists before creating member
        checkUserExists(registerRequestData);
        
        // Generate and save member
        Member member = memberService.generateMember(registerRequestData);
        memberDao.save(member);
        
        // Generate token once and return
        String token = jwtService.generateToken(member);
        return AuthenticationRes.builder()
                .token(token)
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
        // Authenticate user credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequestData.getEmail(),
                        authenticationRequestData.getPassword())
        );
        
        // Find member by email - handle case when member is not found
        Member member = memberDao.findByEmail(authenticationRequestData.getEmail())
                .orElseThrow(() -> WebErrorEnum.USER_NOT_FOUND.exception());
        
        // Generate and return token
        String token = jwtService.generateToken(member);
        return AuthenticationRes.builder()
                .token(token)
                .build();
    }

    /**
     * Renew JWT token for the current authenticated user
     *
     * @return Authentication response with new token
     */
    public AuthenticationRes renewToken() {
        // Get current authenticated member from SecurityContextHolder
        Member currentMember = memberService.getCurrentAuthenticatedMember();
        
        // Generate new token
        String newToken = jwtService.generateToken(currentMember);
        
        return AuthenticationRes.builder()
                .token(newToken)
                .build();
    }
}
