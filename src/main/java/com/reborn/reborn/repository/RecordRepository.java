package com.reborn.reborn.repository;

import com.reborn.reborn.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> , RecordQuerydslRepository{

}
