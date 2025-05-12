package com.yc.snackoverflow.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColorResponseData {
    private String code;
    private String zhName;
    private String enName;
}
