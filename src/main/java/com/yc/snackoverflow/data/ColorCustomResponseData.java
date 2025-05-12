package com.yc.snackoverflow.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColorCustomResponseData {
    private String codeAndNameZh;
    private String codeAndNameEn;
}
