package com.reborn.reborn.record.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reborn.reborn.config.ControllerConfig;
import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.member.domain.MemberRole;
import com.reborn.reborn.record.presentation.dto.RecordRequest;
import com.reborn.reborn.record.presentation.dto.RecordTodayResponse;
import com.reborn.reborn.workout.domain.Workout;
import com.reborn.reborn.record.application.RecordService;
import com.reborn.reborn.workout.domain.WorkoutCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.reborn.reborn.record.presentation.dto.RecordRequest.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RecordControllerTest extends ControllerConfig {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RecordService recordService;


    @Test
    @WithUserDetails(value = "email@naver.com")
    @DisplayName("기록 생성 : POST /api/v1/record")
    void recordCreate() throws Exception {
        //given
        Member member = Member.builder().email("user").memberRole(MemberRole.USER).build();
        Workout workout = Workout.builder().member(member).build();
        List<RecordRequest> list = new ArrayList<>();
        list.add(new RecordRequest(1L, 10, WorkoutCategory.BACK));

        RecordRequestList request = new RecordRequestList(list);
        willDoNothing().given(recordService).create(request);

        //when
        mockMvc.perform(post("/api/v1/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                        .header("Authorization", "Bearer " + getToken(member)))
                .andExpect(status().isCreated())
                .andDo(document("record-create"
                ));
    }

    @Test
    @WithUserDetails(value = "email@naver.com")
    @DisplayName("오늘 기록 조회 : POST /api/v1/record/today")
    void getTodayRecord() throws Exception {
        //given
        Member member = Member.builder().email("user").memberRole(MemberRole.USER).build();
        RecordTodayResponse response = new RecordTodayResponse(10, 20, 30, 40);

        given(recordService.getTodayRecord(any())).willReturn(response);
        //when
        mockMvc.perform(get("/api/v1/record/today")
                        .header("Authorization", "Bearer " + getToken(member))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("record-get-today",
                        responseFields(
                                fieldWithPath("back").type(NUMBER).description("back total"),
                                fieldWithPath("chest").type(NUMBER).description("chest total"),
                                fieldWithPath("lowerBody").type(NUMBER).description("lower body total"),
                                fieldWithPath("core").type(NUMBER).description("core total")
                        )));
    }

}