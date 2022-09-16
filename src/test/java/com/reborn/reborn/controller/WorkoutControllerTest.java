package com.reborn.reborn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reborn.reborn.dto.FileDto;
import com.reborn.reborn.dto.WorkoutRequestDto;
import com.reborn.reborn.dto.WorkoutResponseDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.MemberRole;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.entity.WorkoutCategory;
import com.reborn.reborn.repository.custom.WorkoutSearchCondition;
import com.reborn.reborn.service.WorkoutService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class WorkoutControllerTest extends ControllerConfig {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private WorkoutService workoutService;

    @Test
    @WithUserDetails(value = "email@naver.com")
    @DisplayName("운동 생성 : POST /api/v1/workout")
    void workoutCreate() throws Exception {
        //given
        Member member = Member.builder().email("user").memberRole(MemberRole.USER).build();
        List<FileDto> files = new ArrayList<>();
        files.add(new FileDto("원본 이름","저장된 파일 이름"));
        WorkoutRequestDto workoutRequestDto = WorkoutRequestDto.builder()
                .workoutName("pull up")
                .content("광배 운동")
                .files(files)
                .workoutCategory("BACK").build();

        given(workoutService.create(any())).willReturn(1L);

        //when
        mockMvc.perform(post("/api/v1/workout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(workoutRequestDto))
                        .header("Authorization", "Bearer " + getToken(member)))
                .andExpect(status().isCreated())
                .andDo(document("workout-create",
                        requestFields(
                                fieldWithPath("workoutName").type(STRING).description("운동 이름"),
                                fieldWithPath("content").type(STRING).description("운동 설명"),
                                fieldWithPath("workoutCategory").type(STRING).description("운동 부위"),
                                fieldWithPath("files").type(ARRAY).description("파일 정보"),
                                fieldWithPath("files[].originFileName").type(STRING).description("원본 파일 이름"),
                                fieldWithPath("files[].uploadFileName").type(STRING).description("저장된 파일 이름")
                        )
                ));
    }


    @Test
    @WithUserDetails(value = "email@naver.com")
    @DisplayName("운동 조회 : Get /api/v1/workout/{workoutId}")
    void getMyWorkout() throws Exception {
        //given
        Member member = Member.builder().email("user").memberRole(MemberRole.USER).build();
        Workout workoutResponseDto = Workout.builder()
                .workoutName("pull up")
                .workoutCategory(WorkoutCategory.BACK)
                .content("등 운동입니다.")
                //TODO 테스트코드 다시작성해야함
//                .filePath("imagePath")
                .build();
        given(workoutService.getWorkout(any())).willReturn(workoutResponseDto);
        //when
        mockMvc.perform(get("/api/v1/workout/{workoutId}", 1L)
                        .header("Authorization", "Bearer " + getToken(member)))
                .andExpect(status().isOk())
                .andDo(document("workout-getMyWorkout",
                        pathParameters(
                                parameterWithName("workoutId").description("운동 정보 Id")
                        )
                ));
    }

    @Test
    @WithUserDetails(value = "email@naver.com")
    @DisplayName("운동 리스트 페이지 조회 : Get /api/v1/workout")
    void getPagingWorkout() throws Exception {
        //given
        Member member = Member.builder().email("user").memberRole(MemberRole.USER).build();
        List<WorkoutResponseDto> list = new ArrayList<>();
        WorkoutResponseDto workoutResponseDto = WorkoutResponseDto.builder().workoutName("pull up").workoutName("pull up")
                .workoutCategory(WorkoutCategory.BACK)
                .content("등 운동입니다.")
                .uploadFileName("uuid.png")
                .originFileName("image.png")
                .build();
        list.add(workoutResponseDto);
        WorkoutSearchCondition cond = new WorkoutSearchCondition(1L, "BACK");

        given(workoutService.pagingWorkout(any())).willReturn(list);
        //when
        mockMvc.perform(get("/api/v1/workout")
                        .queryParam("workoutId", "1").queryParam("workoutCategory","BACK")
                        .header("Authorization", "Bearer " + getToken(member)))
                .andExpect(status().isOk())
                .andDo(document("workout-getPagingList",
                        requestParameters(
                                parameterWithName("workoutId").description("마지막으로 받은 운동 Id"),
                                parameterWithName("workoutCategory").description("운동 카테고리")
                        )
                ));
    }


}