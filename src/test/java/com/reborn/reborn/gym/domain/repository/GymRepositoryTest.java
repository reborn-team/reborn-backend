package com.reborn.reborn.gym.domain.repository;

import com.reborn.reborn.gym.domain.Gym;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest
class GymRepositoryTest {

    @Autowired
    private GymRepository gymRepository;

    @Commit
    @Test
    public void insertGymDummies(){
        Gym gym = Gym.builder()
                .place("라벨짐")
                .addr("여기는 기구가 뭐가 있을까?")
                .lat(35.1544453)
                .lng(129.0606460)
        .build();

        gymRepository.save(gym);
    }
}