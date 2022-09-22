package com.reborn.reborn.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.WorkoutCategory;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@NoArgsConstructor
public class WorkoutResponseDto {

    private Long id;
    private String workoutName;
    private String content;
    private String uploadFileName;
    private String originFileName;
    private WorkoutCategory workoutCategory;

    private Long memberId;

    private String memberNickname;

    @JsonProperty("isAuthor")
    @Accessors(fluent = true)
    private boolean isAuthor;


    @Builder
    @QueryProjection
    public WorkoutResponseDto(Long id, String workoutName, String content, String uploadFileName,
                              String originFileName, WorkoutCategory workoutCategory, Long memberId, String memberNickname) {
        this.id = id;
        this.workoutName = workoutName;
        this.content = content;
        this.uploadFileName = uploadFileName;
        this.originFileName = originFileName;
        this.workoutCategory = workoutCategory;
        this.memberId = memberId;
        this.memberNickname = memberNickname;
    }

    public void isAuthor(Long memberId){
        this.isAuthor = this.memberId == memberId;
    }
}
