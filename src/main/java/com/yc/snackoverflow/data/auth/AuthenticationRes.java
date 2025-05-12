package com.yc.snackoverflow.data.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yc
 * @create 2024 - 07 - 23 - 上午 10:38
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRes {
    private String token;
}
