package com.yc.snackoverflow.reposity.converter;

import com.yc.snackoverflow.enums.BaseEnum;
import jakarta.persistence.AttributeConverter;

/**
 * @author meow
 */
public class GenericAttributeConverter<E extends BaseEnum<T>, T> implements AttributeConverter<BaseEnum<T>, T> {
    private final Class<E> enumClass;

    public GenericAttributeConverter(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public T convertToDatabaseColumn(BaseEnum<T> stringBaseEnum) {
        return stringBaseEnum.getValue();
    }

    @Override
    public BaseEnum<T> convertToEntityAttribute(T s) {
        E[] enums = enumClass.getEnumConstants();
        for (E e : enums) {
            if (e.getValue().equals(s)) {
                return e;
            }
        }
        return null;
    }
}
