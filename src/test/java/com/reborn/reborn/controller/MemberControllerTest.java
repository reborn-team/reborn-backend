package com.reborn.reborn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.reborn.reborn.dto.MemberRequestDto;
import com.reborn.reborn.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc // -> webAppContextSetup(webApplicationContext)
@AutoConfigureRestDocs // -> apply(documentationConfiguration(restDocumentation))
@SpringBootTest
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MemberService memberService;


    @Test
    @DisplayName("회원 가입 : /api/v1/join")
    void joinTest() throws Exception {
        MemberRequestDto memberRequestDto = new MemberRequestDto("email", "password", "name", "phone", "postcode", "address", "detailAddress");

        when(memberService.registerMember(memberRequestDto)).thenReturn(1L);

        mockMvc.perform(post("/api/v1/join")
                        .content(objectMapper.writeValueAsBytes(memberRequestDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("join",
                        requestFields(
                                fieldWithPath("email").type(STRING).description("이메일 주소"),
                                fieldWithPath("password").type(STRING).description("비밀번호"),
                                fieldWithPath("name").type(STRING).description("이름"),
                                fieldWithPath("phone").type(STRING).description("전화번호"),
                                fieldWithPath("zipcode").type(STRING).description("우편번호"),
                                fieldWithPath("roadName").type(STRING).description("주소"),
                                fieldWithPath("detailAddress").type(STRING).description("상세 주소")
                        )));
    }

    @Test
    @DisplayName("이메일 중복 확인 : /api/v1/email-check")
    void emailCheck() throws Exception {

        String email = "email";

        when(memberService.emailDuplicateCheck(email)).thenReturn(false);

        mockMvc.perform(get("/api/v1/email-check")
                        .queryParam("email", email))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("email-check",
                        requestParameters(
                                parameterWithName("email").description("중복 체크할 이메일")

                        )));
    }


}