package com.shortener.shorturl.application.shortUrl.dto;

import com.shortener.common.request.MultiShortUrlRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class CreateShortUrlListCommand {
    private Map<String, String> urlList;

    private CreateShortUrlListCommand(Map<String, String> urlList) {
        this.urlList = urlList;
    }

    public static CreateShortUrlListCommand of(MultiShortUrlRequest request) {

        Map<String ,String> urlMap = new HashMap<>();

        request.getUrlList()
            .forEach(url -> urlMap.put(url.getId(), url.getOriginalUrl()));

        return new CreateShortUrlListCommand(urlMap);
    }

    public int getSize() {
        return this.urlList.size();
    }
}
