package com.reborn.reborn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reborn.reborn.dto.WorkoutListDto;
import com.reborn.reborn.dto.WorkoutSliceDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.MemberRole;
import com.reborn.reborn.service.MyWorkoutService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MyWorkoutControllerTest extends ControllerConfig {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MyWorkoutService myWorkoutService;

    @Test
    @WithUserDetails(value = "email@naver.com")
    @DisplayName("내 운동 리스트 페이지 조회 : Get /api/v1/my-workout")
    void getPagingWorkout() throws Exception {
        //given
        Member member = Member.builder().email("user").memberRole(MemberRole.USER).build();
        List<WorkoutListDto> list = new ArrayList<>();
        WorkoutListDto workoutResponseDto = WorkoutListDto.builder().workoutId(1L).workoutName("pull up")
                .uploadFileName("uuid.png")
                .build();
        list.add(workoutResponseDto);
        given(myWorkoutService.getMyWorkoutList(any(), any())).willReturn(new WorkoutSliceDto(list));

        //when
        mockMvc.perform(get("/api/v1/my-workout")
                        .header("Authorization", "Bearer " + getToken(member))
                .queryParam("id", "1").queryParam("category", "BACK"))
                .andExpect(status().isOk())
                .andDo(document("my-workout-getPagingList",
                        requestParameters(
                                parameterWithName("id").description("마지막으로 받은 운동 Id"),
                                parameterWithName("category").description("운동 카테고리")
                        ),
                        responseFields(
                                fieldWithPath("page").type(ARRAY).description("페이지에 출력할 List"),
                                fieldWithPath("hasNext").type(BOOLEAN).description("출력할 내용이 더 있는지")
                        ).andWithPrefix("page[].",
                                fieldWithPath("workoutName").type(STRING).description("운동 이름"),
                                fieldWithPath("workoutId").type(NUMBER).description("운동 ID"),
                                fieldWithPath("uploadFileName").type(STRING).description("업로드 파일 이름")
                        )));
    }
    @Test
    @WithUserDetails(value = "email@naver.com")
    @DisplayName("내 운동 추가 : POST /api/v1/my-workout/{workoutId}")
    void workoutCreate() throws Exception {
        //given
        Member member = Member.builder().email("user").memberRole(MemberRole.USER).build();
        given(myWorkoutService.addMyWorkout(any(), any())).willReturn(1L);

        //when
        mockMvc.perform(post("/api/v1/my-workout/{workoutId}", 1L)
                        .header("Authorization", "Bearer " + getToken(member)))
                .andExpect(status().isCreated())
                .andDo(document("my-workout-addMyWorkout",
                        pathParameters(
                                parameterWithName("workoutId").description("운동 정보 Id")
                        )
                ));
    }

    @Test
    @WithUserDetails(value = "email@naver.com")
    @DisplayName("내 운동 삭제 : Delete /api/v1/my-workout/{workoutId}")
    void deleteWorkout() throws Exception {
        //given
        Member member = Member.builder().email("user").nickname("nickname").memberRole(MemberRole.USER).build();

        doNothing().when(myWorkoutService).deleteMyWorkout(any(), any());
        //when
        mockMvc.perform(delete("/api/v1/my-workout/{workoutId}", 1L)
                        .header("Authorization", "Bearer " + getToken(member)))
                .andExpect(status().isNoContent())
                .andDo(document("my-workout-delete",
                        pathParameters(
                                parameterWithName("workoutId").description("운동 정보 Id")
                        )
                ));
    }

}