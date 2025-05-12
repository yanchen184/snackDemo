package com.yc.snackoverflow.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum UpsertStatusEnum implements BaseEnum<Integer> {

    ERROR(0),

    CREATE_OR_NO_CHANGE(1),

    UPDATE(2),
    ;

    @JsonValue // 這是轉value的重點
    private final Integer value;

    private static final Map<Integer, UpsertStatusEnum> ENUMS = Arrays.stream(values()).collect(Collectors.toUnmodifiableMap(UpsertStatusEnum::getValue, Function.identity()));

    public static Optional<UpsertStatusEnum> lookup(final Integer value) {
        return Optional.ofNullable(ENUMS.get(value));
    }

}
