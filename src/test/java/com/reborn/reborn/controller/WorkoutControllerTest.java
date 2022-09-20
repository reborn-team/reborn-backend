package com.reborn.reborn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reborn.reborn.dto.FileDto;
import com.reborn.reborn.dto.WorkoutListDto;
import com.reborn.reborn.dto.WorkoutRequestDto;
import com.reborn.reborn.dto.WorkoutResponseDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.MemberRole;
import com.reborn.reborn.entity.WorkoutCategory;
import com.reborn.reborn.service.WorkoutImageService;
import com.reborn.reborn.service.WorkoutService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
    @MockBean
    private WorkoutImageService workoutImageService;


    @Test
    @WithUserDetails(value = "email@naver.com")
    @DisplayName("운동 생성 : POST /api/v1/workout")
    void workoutCreate() throws Exception {
        //given
        Member member = Member.builder().email("user").memberRole(MemberRole.USER).build();
        List<FileDto> files = new ArrayList<>();
        files.add(new FileDto("원본 이름", "저장된 파일 이름"));
        WorkoutRequestDto workoutRequestDto = WorkoutRequestDto.builder()
                .workoutName("pull up")
                .content("광배 운동")
                .files(files)
                .workoutCategory("BACK").build();

        given(workoutImageService.create(any(), any())).willReturn(1L);
        given(workoutService.create(any(),any())).willReturn(1L);

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
        WorkoutResponseDto workoutResponseDto = WorkoutResponseDto.builder()
                .workoutName("pull up")
                .workoutCategory(WorkoutCategory.BACK)
                .content("등 운동입니다.")
                .originFileName("원본.png")
                .uploadFileName("uuid.png")
                .build();
        given(workoutService.getWorkoutDto(any())).willReturn(workoutResponseDto);
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
    @DisplayName("운동 리스트 페이지 조회 : Get /api/v1/workout")
    void getPagingWorkout() throws Exception {
        //given
        List<WorkoutListDto> list = new ArrayList<>();
        WorkoutListDto workoutResponseDto = WorkoutListDto.builder().workoutId(1L).workoutName("pull up")
                .uploadFileName("uuid.png")
                .build();
        list.add(workoutResponseDto);
        given(workoutService.pagingWorkout(any())).willReturn(list);

        //when
        mockMvc.perform(get("/api/v1/workout")
                        .queryParam("id", "1").queryParam("category", "BACK"))
                .andExpect(status().isOk())
                .andDo(document("workout-getPagingList",
                        requestParameters(
                                parameterWithName("id").description("마지막으로 받은 운동 Id"),
                                parameterWithName("category").description("운동 카테고리")
                        ),
                        responseFields(
                                //TODO 해야함
                                fieldWithPath("page").type(ARRAY).description("페이지에 출력할 List"),
                                fieldWithPath("hasNext").type(BOOLEAN).description("출력할 내용이 더 있는지")
                        ).andWithPrefix("page[].",
                                fieldWithPath("workoutName").type(STRING).description("운동 이름"),
                                fieldWithPath("workoutId").type(NUMBER).description("운동 ID"),
                                fieldWithPath("uploadFileName").type(STRING).description("업로드 파일 이름")
                        )));
    }


}