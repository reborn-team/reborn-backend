package com.reborn.reborn.service;

import com.reborn.reborn.dto.RecordRequestList;
import com.reborn.reborn.entity.MyWorkout;
import com.reborn.reborn.entity.Record;
import com.reborn.reborn.repository.MyWorkoutRepository;
import com.reborn.reborn.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final MyWorkoutRepository myWorkoutRepository;
    private final RecordRepository recordRepository;

    @Transactional
    public void create(RecordRequestList list) {
        List<Record> recordList = list.getRecordList().stream()
                .map(recordRequest -> {
                    MyWorkout myWorkout = myWorkoutRepository.findById(recordRequest.getMyWorkoutId()).orElseThrow();
                    return new Record(myWorkout, recordRequest.getTotal());
                })
                .collect(Collectors.toList());

        updateOrSaveRecord(recordList);

    }

    private void updateOrSaveRecord(List<Record> recordList) {
        recordList.forEach(record -> {
            Optional<Record> findToday = recordRepository.findByToday(record.getMyWorkout().getId());
            if (findToday.isPresent()) {
                findToday.get().addWeight(record.getTotal());
                return;
            }
            recordRepository.save(record);
        });
    }
}
