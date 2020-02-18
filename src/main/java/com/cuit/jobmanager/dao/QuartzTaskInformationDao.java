package com.cuit.jobmanager.dao;

import com.cuit.jobmanager.model.QuartzTaskInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface QuartzTaskInformationDao extends JpaRepository<QuartzTaskInformation, Long>, QuerydslPredicateExecutor<QuartzTaskInformation> {

    Optional<QuartzTaskInformation> findByTaskNo(String taskNo);

    Optional<List<QuartzTaskInformation>> findAllByFrozenStatus(String frozenStatus);

    Integer  deleteByTaskNo(String taskNo);
}
