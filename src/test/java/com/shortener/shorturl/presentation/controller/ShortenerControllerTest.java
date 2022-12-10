package com.shortener.shorturl.presentation.controller;

import static com.shortener.shorturl.presentation.controller.util.ApiDocsUtil.getDocumentRequest;
import static com.shortener.shorturl.presentation.controller.util.ApiDocsUtil.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.shortener.shorturl.application.shortUrl.service.ShortenerFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(ShortenerController.class)
class ShortenerControllerTest {

    private MockMvc mockMvc;

    @MockBean
    ShortenerFacade facade;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    void redirect_request_success() throws Exception {

        // test short url request redirect to -> http://original.com
        given(facade.getRedirectUrl("test"))
            .willReturn("http://original.com");

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/{value}", "test"));

        resultActions
            .andExpect(status()
                .is3xxRedirection())
            .andDo(document("redirect-request",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(parameterWithName("value")
                    .description("Short URL"))));
    }
}