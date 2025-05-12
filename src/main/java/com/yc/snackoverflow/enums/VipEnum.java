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
public enum VipEnum implements BaseEnum<String> {

    NORMAL("normal"),
    VIP1("vip1"),
    VIP2("vip2"),
    VIP3("vip3"),
    VIP4("yc"),
    ;

    @JsonValue // 這是轉value的重點
    private final String value;

    private static final Map<String, VipEnum> ENUMS = Arrays.stream(values()).collect(Collectors.toUnmodifiableMap(VipEnum::getValue, Function.identity()));

    public static Optional<VipEnum> lookup(final String value) {
        return Optional.ofNullable(ENUMS.get(value));
    }
}
