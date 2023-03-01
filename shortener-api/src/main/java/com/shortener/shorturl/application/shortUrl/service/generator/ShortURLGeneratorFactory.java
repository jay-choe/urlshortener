package com.shortener.shorturl.application.shortUrl.service.generator;

import com.shortener.common.enums.GenerateType;
import java.util.EnumMap;

public final class ShortURLGeneratorFactory {

    private static final EnumMap<GenerateType, ShortURLGenerateStrategy> strategyMap;

    static {
        strategyMap = new EnumMap<>(GenerateType.class);
        strategyMap.put(GenerateType.RANDOM, new RandomShortURLGenerateStrategy());
        strategyMap.put(GenerateType.FIXED, new FixedShortURLGenerateStrategy());
    }

    public static ShortURLGenerateStrategy getStrategy(GenerateType generateType) {
        return strategyMap.get(generateType);
    }
}
