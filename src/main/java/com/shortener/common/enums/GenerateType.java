package com.shortener.common.enums;

public enum GenerateType {
    FIXED,
    RANDOM;

    public static GenerateType getDefaultType() {
        return RANDOM;
    }
}
