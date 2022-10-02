package com.shortener.shorturl.application.shortUrl.service;

import com.shortener.shorturl.application.shortUrl.dto.CreateCustomUrlCommand;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShortenerFacade {
    private final ShortenerService service;

    @Transactional
    public String createCustomUrl(CreateCustomUrlCommand command) {
        String originUrl = command.getOriginUrl();
        String target = command.getTarget();

        service.checkAlreadyExistTarget(target);
        return service.createCustomUrl(originUrl, target);
    }


}
