package com.yc.snackoverflow.enums;

import java.util.Optional;

/**
 * @author meow
 */
public interface BaseEnum<T> {

    T getValue();

    static <E extends Enum<E> & BaseEnum<T>, T> Optional<E> findByValue(Class<E> enumClass, T value) {
        for (E enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.getValue().equals(value)) {
                return Optional.of(enumConstant);
            }
        }
        return Optional.empty();
    }
}
