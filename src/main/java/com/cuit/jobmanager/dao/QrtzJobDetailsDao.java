package com.cuit.jobmanager.dao;

import com.cuit.jobmanager.model.QrtzJobDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QrtzJobDetailsDao extends JpaRepository<QrtzJobDetails, String>, QuerydslPredicateExecutor<QrtzJobDetails> {
}
