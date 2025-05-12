package com.yc.snackoverflow.data.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Yc
 * @create 2024 - 07 - 23 - 上午 10:39
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RegisterReq extends AuthenticationRequest {
    private String username;

    @Builder(builderMethodName = "registerReqBuilder")
    public RegisterReq(String email, String password, String username) {
        super(email, password);
        this.username = username;
    }
}
