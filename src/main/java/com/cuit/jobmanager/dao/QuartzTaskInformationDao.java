package com.cuit.jobmanager.dao;

import com.cuit.jobmanager.model.QuartzTaskInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface QuartzTaskInformationDao extends JpaRepository<QuartzTaskInformation, Long> {

    Optional<QuartzTaskInformation> findByTaskNo(String taskNo);

    Optional<List<QuartzTaskInformation>> findAllByFrozenStatus(String frozenStatus);

    Integer  deleteByTaskNo(String taskNo);
}
