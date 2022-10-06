package com.reborn.reborn.record.application;

import com.reborn.reborn.myworkout.domain.MyWorkout;
import com.reborn.reborn.record.domain.Record;
import com.reborn.reborn.myworkout.exception.MyWorkoutNotFoundException;
import com.reborn.reborn.myworkout.domain.repository.MyWorkoutRepository;
import com.reborn.reborn.record.domain.repository.RecordRepository;
import com.reborn.reborn.record.presentation.dto.RecordTodayResponse;
import com.reborn.reborn.record.presentation.dto.RecordWeekResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.reborn.reborn.record.presentation.dto.RecordRequest.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecordService {

    private final MyWorkoutRepository myWorkoutRepository;
    private final RecordRepository recordRepository;

    @Transactional
    public void create(RecordRequestList list) {
        List<Record> recordList = list.getRecordList().stream()
                .map(recordRequest -> {
                    MyWorkout myWorkout = myWorkoutRepository.findById(recordRequest.getMyWorkoutId())
                            .orElseThrow(() -> new MyWorkoutNotFoundException("찾으시는 내 운동이 없습니다 :" + recordRequest.getMyWorkoutId()));
                    return new Record(myWorkout, recordRequest.getTotal(), recordRequest.getWorkoutCategory());
                })
                .collect(Collectors.toList());

        updateOrSaveRecord(recordList);

    }

    public RecordTodayResponse getTodayRecord(Long memberId) {
        List<Record> records = recordRepository.findTodayRecordByMemberId(memberId);
        RecordTodayResponse response = new RecordTodayResponse();
        records.forEach(record -> response.addTotal(record.getWorkoutCategory(), record.getTotal()));
        return response;
    }

    public RecordWeekResponse getWeekRecord(Long memberId, LocalDate localDate) {
        return recordRepository.findWeekMyRecord(memberId, localDate);
    }

    private void updateOrSaveRecord(List<Record> recordList) {
        recordList.forEach(record -> {
            Optional<Record> findToday = recordRepository.findTodayRecordByMyWorkoutId(record.getMyWorkout().getId());
            if (findToday.isPresent()) {
                findToday.get().addWeight(record.getTotal());
                return;
            }
            recordRepository.save(record);
        });
    }

}
