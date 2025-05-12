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
public class ProductDto {

    @NonNull
    private String name;

    @NonNull
    private int price;

    private String picture;

    private Boolean alive;

}
