package com.shortener.shorturl.application.shortUrl.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UrlResponseListDto implements Serializable {
    private List<UrlResponseDto> urlResponseList;
}
