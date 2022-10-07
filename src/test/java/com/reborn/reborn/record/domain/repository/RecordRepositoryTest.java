package com.reborn.reborn.record.domain.repository;

import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.member.domain.repository.MemberRepository;
import com.reborn.reborn.myworkout.domain.MyWorkout;
import com.reborn.reborn.myworkout.domain.repository.MyWorkoutRepository;
import com.reborn.reborn.record.domain.Record;
import com.reborn.reborn.record.presentation.dto.RecordWeekResponse;
import com.reborn.reborn.workout.domain.Workout;
import com.reborn.reborn.workout.domain.WorkoutCategory;
import com.reborn.reborn.workout.domain.repository.WorkoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.reborn.reborn.config.ControllerConfig.getMember;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class RecordRepositoryTest {

    @Autowired
    RecordRepository recordRepository;
    @Autowired
    MyWorkoutRepository myWorkoutRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    WorkoutRepository workoutRepository;
    @Autowired
    EntityManager em;

    @BeforeEach
    void before() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("saveAll 을 통해 전체 저장한다.")
    void saveAll() {
        Member saveMember = memberRepository.save(getMember());
        Workout saveWorkout = workoutRepository.save(getWorkout(saveMember));
        MyWorkout saveMyWorkout = myWorkoutRepository.save(new MyWorkout(saveWorkout, saveMember));
        em.flush();
        em.clear();

        List<Record> records = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            records.add(new Record(saveMyWorkout, Long.valueOf(i), WorkoutCategory.BACK));
        }

        List<Record> saveRecord = recordRepository.saveAll(records);
        assertThat(records).isEqualTo(saveRecord);

    }

    @Test
    @DisplayName("내 운동 ID로 오늘 등록한 Record를 출력한다.")
    void findByToday() {
        Member saveMember = memberRepository.save(getMember());
        Workout saveWorkout = workoutRepository.save(getWorkout(saveMember));
        MyWorkout saveMyWorkout = myWorkoutRepository.save(new MyWorkout(saveWorkout, saveMember));
        recordRepository.save(new Record(saveMyWorkout, 10L, WorkoutCategory.BACK));
        em.flush();
        em.clear();

        Record record = recordRepository.findTodayRecordByMyWorkoutId(saveMyWorkout.getId()).orElseThrow();
        assertThat(record.getMyWorkout().getId()).isEqualTo(saveMyWorkout.getId());
    }
    @Test
    @DisplayName("member ID로 오늘 등록한 Record 리스트를 출력한다.")
    void findByTodayList() {
        Member saveMember = memberRepository.save(getMember());
        Workout saveWorkout = workoutRepository.save(getWorkout(saveMember));
        MyWorkout saveMyWorkout = myWorkoutRepository.save(new MyWorkout(saveWorkout, saveMember));
        Record record = new Record(saveMyWorkout, 10L, WorkoutCategory.BACK);
        Record record2 = new Record(saveMyWorkout, 20L, WorkoutCategory.CHEST);
        Record record3 = new Record(saveMyWorkout, 40L, WorkoutCategory.CORE);
        Record record4 = new Record(saveMyWorkout, 20L, WorkoutCategory.BACK);
        recordRepository.save(record);
        recordRepository.save(record2);
        recordRepository.save(record3);
        recordRepository.save(record4);
        em.flush();
        em.clear();


        List<Record> records = recordRepository.findTodayRecordByMemberId(saveMember.getId());
        assertThat(records.size()).isEqualTo(4);
    }

    public static Workout getWorkout(Member saveMember) {
        return Workout.builder().workoutName("name").workoutCategory(WorkoutCategory.BACK).content("content").member(saveMember).build();
    }
    @Test
    @DisplayName("오늘 날짜 기준으로 일주일간 Record 리스트를 출력한다.")
    void findRecordByDayOfWeek() {
        Member saveMember = memberRepository.save(getMember());
        Workout saveWorkout = workoutRepository.save(getWorkout(saveMember));
        MyWorkout saveMyWorkout = myWorkoutRepository.save(new MyWorkout(saveWorkout, saveMember));
        Record record = new Record(saveMyWorkout, 10L, WorkoutCategory.BACK);
        Record record2 = new Record(saveMyWorkout, 20L, WorkoutCategory.CHEST);
        Record record3 = new Record(saveMyWorkout, 40L, WorkoutCategory.CORE);
        Record record4 = new Record(saveMyWorkout, 20L, WorkoutCategory.BACK);
        recordRepository.save(record);
        recordRepository.save(record2);
        recordRepository.save(record3);
        recordRepository.save(record4);

        Optional<RecordWeekResponse> weekMyRecord = recordRepository.findWeekMyRecord(saveMember.getId(), LocalDate.now());
        assertThat(weekMyRecord.isPresent()).isTrue();
    }

    @Test
    @DisplayName("특정 날짜 기준으로 일주일간 Record 리스트를 출력한다.")
    void findRecordBySpecificDayOfWeek() {
        Member saveMember = memberRepository.save(getMember());
        Workout saveWorkout = workoutRepository.save(getWorkout(saveMember));
        MyWorkout saveMyWorkout = myWorkoutRepository.save(new MyWorkout(saveWorkout, saveMember));
        Record record = new Record(saveMyWorkout, 10L, WorkoutCategory.BACK);
        Record record2 = new Record(saveMyWorkout, 20L, WorkoutCategory.CHEST);
        Record record3 = new Record(saveMyWorkout, 40L, WorkoutCategory.CORE);
        Record record4 = new Record(saveMyWorkout, 20L, WorkoutCategory.BACK);
        recordRepository.save(record);
        recordRepository.save(record2);
        recordRepository.save(record3);
        recordRepository.save(record4);

        Optional<RecordWeekResponse> weekMyRecord = recordRepository.findWeekMyRecord(saveMember.getId(), LocalDate.of(2021, 10, 1));
        assertThat(weekMyRecord.isPresent()).isFalse();
    }
}