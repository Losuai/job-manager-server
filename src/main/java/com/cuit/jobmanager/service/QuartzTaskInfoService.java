package com.cuit.jobmanager.service;

import com.cuit.jobmanager.model.QuartzTaskInformation;

import java.util.List;
import java.util.Optional;

public interface QuartzTaskInfoService {
    QuartzTaskInformation insert(QuartzTaskInformation quartzTaskInformation);
    Optional<QuartzTaskInformation> selectTaskByNo(String taskNo);
    Optional<List<QuartzTaskInformation>> findAllUnfrozeTasks();
    boolean deleteTaskInfoByTaskNo(String jobName);
}
