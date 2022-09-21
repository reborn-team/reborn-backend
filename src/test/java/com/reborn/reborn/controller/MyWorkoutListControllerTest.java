package com.reborn.reborn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.MemberRole;
import com.reborn.reborn.service.MyWorkoutListService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MyWorkoutListControllerTest extends ControllerConfig {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MyWorkoutListService myWorkoutListService;

    @Test
    @WithUserDetails(value = "email@naver.com")
    @DisplayName("운동 생성 : POST /api/v1/my-workout/{workoutId}")
    void workoutCreate() throws Exception {
        //given
        Member member = Member.builder().email("user").memberRole(MemberRole.USER).build();
        given(myWorkoutListService.addWorkout(any(), any())).willReturn(1L);

        //when
        mockMvc.perform(post("/api/v1/my-workout/{workoutId}", 1L)
                        .header("Authorization", "Bearer " + getToken(member)))
                .andExpect(status().isCreated())
                .andDo(document("workoutList-addMyWorkoutList",
                        pathParameters(
                                parameterWithName("workoutId").description("운동 정보 Id")
                        )
                ));
    }
}