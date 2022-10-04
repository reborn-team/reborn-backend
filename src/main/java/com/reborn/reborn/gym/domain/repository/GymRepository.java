package com.reborn.reborn.gym.domain.repository;

import com.reborn.reborn.gym.domain.Gym;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GymRepository extends JpaRepository<Gym, Long> {
}
