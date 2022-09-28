package com.reborn.reborn.service;

import com.reborn.reborn.dto.RecordRequest;
import com.reborn.reborn.entity.MyWorkout;
import com.reborn.reborn.entity.Record;
import com.reborn.reborn.repository.MyWorkoutRepository;
import com.reborn.reborn.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final MyWorkoutRepository myWorkoutRepository;
    private final RecordRepository recordRepository;

    @Transactional
    public void create(List<RecordRequest> list) {
        List<Record> recordList = list.stream().map(
                recordRequest -> {
                    MyWorkout myWorkout = myWorkoutRepository.findById(recordRequest.getMyWorkoutId()).orElseThrow();
                    return new Record(myWorkout, recordRequest.getWeight());
                }
        ).collect(Collectors.toList());

        recordList.forEach(record -> {
            Optional<Record> findToday = recordRepository.findByToday(record.getMyWorkout().getId());
            if (findToday.isPresent()) {
                findToday.get().addWeight(record.getWeight());
            } else {
                recordRepository.save(record);
            }
        });

    }
}
