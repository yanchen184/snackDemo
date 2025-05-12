package com.yc.snackoverflow.service.impl;

import com.yc.snackoverflow.data.MemberDto;
import com.yc.snackoverflow.data.auth.RegisterReq;
import com.yc.snackoverflow.enums.Role;
import com.yc.snackoverflow.enums.UpsertStatusEnum;
import com.yc.snackoverflow.enums.VipEnum;
import com.yc.snackoverflow.exception.WebErrorEnum;
import com.yc.snackoverflow.model.Member;
import com.yc.snackoverflow.reposity.MemberDao;
import com.yc.snackoverflow.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UpsertStatusEnum createOrUpdate(MemberDto memberDto) {
        List<Member> members = memberDao.list(List.of(memberDto.getName()));

        Member memberToSave = members.stream().findFirst()
                .map(m -> {
                    if (memberDto.getVip() != null) {
                        m.setVip(memberDto.getVip());
                    }
                    if (memberDto.getPassword() != null) {
                        m.setPassword(passwordEncoder.encode(memberDto.getPassword()));
                    }
                    if (memberDto.getAlive() != null) {
                        m.setAlive(memberDto.getAlive());
                    }
                    return m;
                })
                .orElseGet(() -> {
                    RegisterReq registerReq = RegisterReq.registerReqBuilder()
                            .username(memberDto.getName())
                            .email(memberDto.getEmail())
                            .password(memberDto.getPassword())
                            .build();
                    return generateMember(registerReq);
                });

        memberDao.save(memberToSave);

        return UpsertStatusEnum.CREATE_OR_NO_CHANGE;

//        return UpsertStatusEnum.lookup(createOrUpdate)
//                .orElseThrow(WebErrorEnum.UPSERT_FAILED::exception);
    }

    @Override
    public List<Member> list(List<String> memberNameList) {
        return Optional.ofNullable(memberDao.list(memberNameList))
                .filter(members -> !members.isEmpty())
                .orElseThrow(() -> WebErrorEnum.MEMBER_NOT_FOUND.exception(memberNameList));
    }

    @Override
    public Member generateMember(RegisterReq registerReq) {
        return Member.builder()
                .name(registerReq.getUsername())
                .alive(true)
                .vip(VipEnum.VIP1)
                .email(registerReq.getEmail())
                .password(passwordEncoder.encode(registerReq.getPassword()))
                .role(Role.USER)
                .build();
    }
    
    @Override
    public Member getCurrentAuthenticatedMember() {
        // Get authentication from security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw WebErrorEnum.UNAUTHORIZED.exception();
        }
        
        // Get principal (username/email) from authentication
        String email = authentication.getName();
        
        // Find and return member
        return memberDao.findByEmail(email)
                .orElseThrow(WebErrorEnum.USER_NOT_FOUND::exception);
    }
}