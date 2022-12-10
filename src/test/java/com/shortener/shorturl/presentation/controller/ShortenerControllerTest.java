package com.shortener.shorturl.presentation.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.shortener.shorturl.presentation.controller.util.ApiDocsUtil.getDocumentRequest;
import static com.shortener.shorturl.presentation.controller.util.ApiDocsUtil.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.shortener.common.request.CreateShortUrlRequest;
import com.shortener.shorturl.application.shortUrl.dto.command.CreateCustomUrlCommand;
import com.shortener.shorturl.application.shortUrl.dto.command.CreateShortUrlCommand;
import com.shortener.shorturl.application.shortUrl.service.ShortenerFacade;
import com.shortener.shorturl.presentation.dto.CreateCustomUrlRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(ShortenerController.class)
@TestInstance(Lifecycle.PER_CLASS)
class ShortenerControllerTest {

    private MockMvc mockMvc;

    @MockBean
    ShortenerFacade facade;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeAll
    void init() {
        MockitoAnnotations.openMocks(this);
        objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
    }

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    void redirect_request_success() throws Exception {

        // test short url request redirect to -> http://original.com
        given(facade.getRedirectUrl("test"))
            .willReturn("http://your-original-url");

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/{value}", "test"));

        resultActions
            .andExpect(status()
                .is3xxRedirection())
            .andDo(document("redirect-request",
                getDocumentRequest(),
                getDocumentResponse(),
                resource(ResourceSnippetParameters.builder()
                    .pathParameters(
                        parameterWithName("value")
                        .description("Short URL"))
                        .build())
                ));
    }

    @Test
    void redirect_request_fail() throws Exception {

        given(facade.getRedirectUrl("invalid-URL"))
            .willReturn("http://error.page");

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/{value}", "invalid-URL"));

        resultActions
            .andExpect(status()
                .is3xxRedirection())
            .andDo(document("redirect-request",
                getDocumentRequest(),
                getDocumentResponse(),
                resource(ResourceSnippetParameters.builder()
                    .pathParameters(parameterWithName("value")
                    .description("Not valid Short URL"))
                    .build())));
    }

    @Test
    void create_short_url_success() throws Exception {
        CreateShortUrlRequest shortUrlRequest = CreateShortUrlRequest.builder()
            .originalUrl("http://google.com/too/long/url/which/is/not/easy/to/recognize/at/once")
            .build();

        given(facade.createShortUrl(CreateShortUrlCommand.of(shortUrlRequest)))
            .willReturn("abcdefg");

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.post("/urls")
            .accept(MediaType.ALL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(shortUrlRequest))
            .characterEncoding("UTF-8"));

        resultActions
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andDo(document("create-short-URL",
                getDocumentRequest(),
                getDocumentResponse(),
                resource(ResourceSnippetParameters.builder()
                        .requestFields(
                            fieldWithPath("originalUrl").description("URL to short"))
                        .responseFields(
                            fieldWithPath("code").description("status code"),
                            fieldWithPath("data").description("response data"),
                            fieldWithPath("data.originalUrl").description("URL requested to shorten"),
                            fieldWithPath("data.shortUrl").description("short URL which is created")).build()
                )));
    }

    @Test
    void create_custom_URL_success() throws Exception {
        final String originalURL = "http://google.com/too/long/url/which/is/not/easy/to/recognize/at/once";
        final String customURL = "custom-url";

        final CreateCustomUrlRequest customURLRequest = CreateCustomUrlRequest.builder()
            .originUrl(originalURL)
            .target(customURL)
            .build();

        given(facade.createCustomUrl(CreateCustomUrlCommand.of(originalURL, customURL)))
            .willReturn(customURL);

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.post("/urls/custom-url")
            .accept(MediaType.ALL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(customURLRequest))
            .characterEncoding("UTF-8"));

        resultActions
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(customURL))
            .andDo(document("create-custom-URL",
                getDocumentRequest(),
                getDocumentResponse(),
                resource(ResourceSnippetParameters.builder()
                    .requestFields(
                        fieldWithPath("originUrl").description("URL to short"),
                        fieldWithPath("target").description("Custom Short URL to make from")
                    ).responseFields(
                        fieldWithPath("code").description("status code"),
                        fieldWithPath("data").description("custom url")
                    ).build()
                )));
    }

}