package com.yc.snackoverflow.data;

import com.yc.snackoverflow.enums.VipEnum;
import com.yc.snackoverflow.reposity.converter.VipEnumAttrConverter;
import jakarta.persistence.Convert;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Data
public class MemberDto {

    @NonNull
    private String name;

    private String password;

    private String email;

    private Boolean alive;

    @Convert(converter = VipEnumAttrConverter.class)
    private VipEnum vip;
}
