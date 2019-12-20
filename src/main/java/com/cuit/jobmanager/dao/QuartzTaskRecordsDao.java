package com.cuit.jobmanager.dao;

import com.cuit.jobmanager.model.QuartzTaskRecords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuartzTaskRecordsDao extends JpaRepository<QuartzTaskRecords, Long> {
}
