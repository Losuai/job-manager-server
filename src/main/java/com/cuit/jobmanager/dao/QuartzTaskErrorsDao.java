package com.cuit.jobmanager.dao;

import com.cuit.jobmanager.model.QuartzTaskErrors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QuartzTaskErrorsDao extends JpaRepository<QuartzTaskErrors, Long>, QuerydslPredicateExecutor<QuartzTaskErrors> {
}
