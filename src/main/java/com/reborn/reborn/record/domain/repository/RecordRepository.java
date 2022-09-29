package com.reborn.reborn.record.domain.repository;

import com.reborn.reborn.record.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> , RecordQuerydslRepository{

}
