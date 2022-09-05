package com.reborn.reborn.service;

import com.reborn.reborn.dto.WorkoutRequestDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.repository.WorkoutRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkoutServiceTest {

    @InjectMocks
    private WorkoutServiceImpl workoutService;
    @Mock
    WorkoutRepository workoutRepository;

    @Test
    void create() {
        WorkoutRequestDto requestDto = WorkoutRequestDto.builder()
                .workoutCategory("BACK").build();
        Member member = Member.builder().build();

//       when(workoutRepository.findAllById(any())).thenReturn(Collections.singletonList(workout));

        workoutService.create(member, requestDto);

        verify(workoutRepository).save(any());


    }

}