package com.cuit.jobmanager.dao;

import com.cuit.jobmanager.model.QuartzTaskInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface QuartzTaskInformationDao extends JpaRepository<QuartzTaskInformation, Long>, QuerydslPredicateExecutor<QuartzTaskInformation> {

    Optional<QuartzTaskInformation> findByTaskNo(String taskNo);

    Optional<List<QuartzTaskInformation>> findAllByFrozenStatus(String frozenStatus);

    Integer  deleteByTaskNo(String taskNo);

    @Query(value = "select date_format(from_unixtime(quartz_task_information.create_time/1000), '%Y/%m/%d') as d , count(*) as s from quartz_task_information group by d", nativeQuery = true)
    List getNumTask();
}
