package com.cuit.jobmanager.dao;

import com.cuit.jobmanager.model.QuartzUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QuartzUserDao extends JpaRepository<QuartzUser, Long>, QuerydslPredicateExecutor<QuartzUser> {
    QuartzUser findByUsername(String userName);
}
