package com.shortener.common.enums;

public enum GenenrateType {
    HASH,
    RANDOM,
    CUSTOM;


    public static GenenrateType getDefaultType() {
        return RANDOM;
    }
}
