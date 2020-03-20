package com.cuit.jobmanager.dao;

import com.cuit.jobmanager.model.QuartzTaskRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QuartzTaskRecordsDao extends JpaRepository<QuartzTaskRecords, Long>, QuerydslPredicateExecutor<QuartzTaskRecords> {
}
