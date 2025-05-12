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
public enum CommitActionEnum implements BaseEnum<String> {

    HAPPY("happy"),

    SAD("sad"),

    ANGRY("angry"),

    SURPRISED("surprised"),

    DISGUSTED("disgusted"),

    FEARFUL("fearful"),
    ;

    @JsonValue // 這是轉value的重點
    private final String value;

    private static final Map<String, CommitActionEnum> ENUMS = Arrays.stream(values()).collect(Collectors.toUnmodifiableMap(CommitActionEnum::getValue, Function.identity()));

    public static Optional<CommitActionEnum> lookup(final String value) {
        return Optional.ofNullable(ENUMS.get(value));
    }
}
