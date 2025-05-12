package com.yc.snackoverflow.data;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Data
public class ProductCommitDto {

    @NonNull
    private String memberName;

    @NonNull
    private String productName;

    @NonNull
    private String content;

}
