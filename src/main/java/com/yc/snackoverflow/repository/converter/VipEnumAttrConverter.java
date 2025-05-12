package com.yc.snackoverflow.reposity.converter;

import com.yc.snackoverflow.enums.VipEnum;
import jakarta.persistence.Converter;

/**
 * @author meow
 */
@Converter(autoApply = true)
public class VipEnumAttrConverter extends GenericAttributeConverter<VipEnum, String> {

    public VipEnumAttrConverter() {
        super(VipEnum.class);
    }
}
