package com.reborn.reborn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reborn.reborn.dto.WorkoutResponseDto;
import com.reborn.reborn.dto.WorkoutRequestDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.MemberRole;
import com.reborn.reborn.entity.WorkoutCategory;
import com.reborn.reborn.repository.MemberRepository;
import com.reborn.reborn.security.jwt.AuthToken;
import com.reborn.reborn.security.jwt.TokenProvider;
import com.reborn.reborn.service.WorkoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc // -> webAppContextSetup(webApplicationContext)
@AutoConfigureRestDocs // -> apply(documentationConfiguration(restDocumentation))
@SpringBootTest
class WorkoutControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private WorkoutService workoutService;
    private TokenProvider tokenProvider;
    private AuthToken token;
    @Autowired
    private MemberRepository memberRepository;
    @Value("${spring.jwt.secret-key}")
    private String value;

    @BeforeEach
    void before() {
        tokenProvider = new TokenProvider(value);
        token = tokenProvider.createAuthToken("user", MemberRole.USER, new Date(100000000000L));
    }

    @PostConstruct
    void createWorkout() {
        memberRepository.deleteAll();
        Member member = Member.builder().email("email@naver.com")
                .name("han").build();
        memberRepository.save(member);

    }

    @Test
    @WithUserDetails(value = "email@naver.com")
    @DisplayName("운동 생성 : POST /api/v1/workout")
    void workoutCreate() throws Exception {
        //given
        Member member = Member.builder().email("user").memberRole(MemberRole.USER).build();
        WorkoutRequestDto workoutRequestDto = WorkoutRequestDto.builder()
                .workoutName("pull up")
                .content("광배 운동")
                .filePath("이미지 경로")
                .workoutCategory("BACK").build();

        //when
        mockMvc.perform(post("/api/v1/workout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(workoutRequestDto))
                        .header("Authorization", "Bearer " + token.createToken(member.getEmail(), member.getMemberRole(), new Date(100000000000L))))
                .andExpect(status().isCreated())
                .andDo(document("workout-create",
                        requestFields(
                                fieldWithPath("workoutName").type(STRING).description("운동 이름"),
                                fieldWithPath("content").type(STRING).description("운동 설명"),
                                fieldWithPath("workoutCategory").type(STRING).description("운동 부위"),
                                fieldWithPath("filePath").type(STRING).description("이미지 경로")
                        )
                ));
    }

    @Test
    @WithUserDetails(value = "email@naver.com")
    @DisplayName("운동 조회 : Get /api/v1/workout/{workoutId}")
    void getMyWorkout() throws Exception {
        //given
        Member member = Member.builder().email("user").memberRole(MemberRole.USER).build();
        WorkoutResponseDto workoutResponseDto = WorkoutResponseDto.builder()
                .workoutName("pull up")
                .id(1L)
                .workoutCategory(WorkoutCategory.BACK)
                .content("등 운동입니다.")
                .filePath("imagePath").build();
        given(workoutService.getMyWorkout(any(), any())).willReturn(workoutResponseDto);
        //when
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/workout/{workoutId}", 1L)
                        .header("Authorization", "Bearer " + token.createToken(member.getEmail(), member.getMemberRole(), new Date(100000000000L))))
                .andExpect(status().isOk())
                .andDo(document("workout-getMyWorkout",
                        pathParameters(
                                parameterWithName("workoutId").description("운동 정보 Id")
                        )
                ));
    }
}