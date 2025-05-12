package com.yc.snackoverflow.config.web.converter;

import com.yc.snackoverflow.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;

/**
 * @author meow
 */
public class StringToEnumConverterFactory implements ConverterFactory<String, BaseEnum<?>> {

    @Override
    @NonNull
    public <T extends BaseEnum<?>> Converter<String, T> getConverter(@NonNull Class<T> targetType) {
        return new EnumConverter<>(targetType);
    }

    static final class EnumConverter<T extends BaseEnum<?>> implements Converter<String, T> {

        private final Class<T> enumType;

        EnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(@NonNull String source) {
            T[] enums = enumType.getEnumConstants();
            for (T e : enums) {
                if (e.getValue().equals(source)) {
                    return e;
                }
            }
            throw new UnsupportedOperationException("No enum constant " + enumType.getCanonicalName() + "." + source + " found.");
        }
    }
}
