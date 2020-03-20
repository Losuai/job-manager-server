package com.cuit.jobmanager.service;

import com.cuit.jobmanager.model.QuartzTaskInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuartzTaskInfoService {
    QuartzTaskInformation insert(QuartzTaskInformation quartzTaskInformation);

    QuartzTaskInformation selectTaskByNo(String taskNo);

    List<QuartzTaskInformation> findAllUnfrozeTasks();

    boolean deleteTaskInfoByTaskNo(String jobName);

    QuartzTaskInformation updateTaskInfo(QuartzTaskInformation quartzTaskInformation);

    Page<QuartzTaskInformation> findAllByPage(String keyWords, Pageable pageable);

    int findAllTasks();

    int findTasksPaused();

    int findTasksRunning();

    List getNumOfTask();
}
