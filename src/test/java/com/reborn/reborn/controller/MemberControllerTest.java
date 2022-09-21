package com.reborn.reborn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.reborn.reborn.dto.ChangePasswordDto;
import com.reborn.reborn.dto.MemberRequestDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.MemberRole;
import com.reborn.reborn.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MemberControllerTest extends ControllerConfig {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MemberService memberService;


    @Test
    @DisplayName("회원 가입 : POST /api/v1/members")
    void joinTest() throws Exception {
        MemberRequestDto memberRequestDto = new MemberRequestDto("email", "password", "name", "phone", "postcode", "address", "detailAddress");

        when(memberService.registerMember(memberRequestDto)).thenReturn(1L);

        mockMvc.perform(post("/api/v1/members")
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
    @DisplayName("이메일 중복 확인 : GET /api/v1/email-check")
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

    @Test
    @WithUserDetails(value = "email@naver.com")
    @DisplayName("비밀번호 변경 : PATCH /api/v1/change-password")
    void changePassword() throws Exception {
        Member member = Member.builder().email("user").password("a").memberRole(MemberRole.USER).build();
        ChangePasswordDto changePasswordDto = new ChangePasswordDto("a", "b");

        willDoNothing().given(memberService).updatePassword(member,changePasswordDto);

        mockMvc.perform(patch("/api/v1/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(changePasswordDto))
                        .header("Authorization", "Bearer " + getToken(member)))
                .andExpect(status().isNoContent())
                .andDo(document("change-password",
                        requestFields(
                                fieldWithPath("rawPassword").type(STRING).description("현재 비밀번호"),
                                fieldWithPath("changePassword").type(STRING).description("변경할 비밀번호")
                        )
                ));
    }

}