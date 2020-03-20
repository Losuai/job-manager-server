package com.cuit.jobmanager.service;

import com.cuit.jobmanager.model.QuartzTaskErrors;
import com.cuit.jobmanager.model.QuartzTaskInformation;
import com.cuit.jobmanager.model.QuartzTaskRecords;
import org.quartz.SchedulerException;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface QuartzTaskService {
    QuartzTaskInformation addTask(QuartzTaskInformation quartzTaskInformation);

    void initLoadOnlineTasks() throws SchedulerException;

    QuartzTaskRecords addTaskRecord(String taskNo, long isFailure);

    boolean deleteTask(String JobName);

    QuartzTaskInformation updateTaskAndJob(QuartzTaskInformation quartzTaskInformation);

    void pauseOrResumeJob(String jobName, Integer onOrOff);

    void runJobNow(String jobName);

    Page<QuartzTaskInformation> findAllByPage(String keywords, int page, int size);

    QuartzTaskInformation updateTask(QuartzTaskInformation quartzTaskInformation);

    Map<String, Integer> getTaskStatisticsV1();

    QuartzTaskErrors addError(QuartzTaskErrors quartzTaskErrors);

    Page<QuartzTaskRecords> searchRecordsByPage(String keyWords, int page, int size);

    QuartzTaskErrors findQuartzTaskError(long id);
}
