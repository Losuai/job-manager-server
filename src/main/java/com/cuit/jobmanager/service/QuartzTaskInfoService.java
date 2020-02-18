package com.cuit.jobmanager.service;

import com.cuit.jobmanager.model.QuartzTaskInformation;

import java.util.List;

public interface QuartzTaskInfoService {
    QuartzTaskInformation insert(QuartzTaskInformation quartzTaskInformation);

    QuartzTaskInformation selectTaskByNo(String taskNo);

    List<QuartzTaskInformation> findAllUnfrozeTasks();

    boolean deleteTaskInfoByTaskNo(String jobName);

    QuartzTaskInformation updateTaskInfo(QuartzTaskInformation quartzTaskInformation);
}
