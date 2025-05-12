package com.yc.snackoverflow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yc.snackoverflow.data.auth.RegisterReq;
import com.yc.snackoverflow.enums.Role;
import com.yc.snackoverflow.enums.VipEnum;
import com.yc.snackoverflow.model.baseAbstract.BaseLongIdDO;
import com.yc.snackoverflow.reposity.converter.VipEnumAttrConverter;
import jakarta.persistence.Access;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "MEMBER")
public class Member extends BaseLongIdDO implements UserDetails {

    private String name;

    private String password;

    private Boolean alive;

    @Convert(converter = VipEnumAttrConverter.class)
    private VipEnum vip;

    private String email;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "member",
            cascade = {CascadeType.PERSIST}
    )
    @JsonIgnore
    private Set<MemberLog> memberLogs;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "member",
            cascade = {CascadeType.PERSIST}
    )
    @JsonIgnore
    private Set<ProductCommit> productCommits;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}