package com.cuit.jobmanager.service;

import com.cuit.jobmanager.model.QuartzTaskInformation;
import com.cuit.jobmanager.model.QuartzTaskRecords;
import org.quartz.SchedulerException;

public interface QuartzTaskService {
    QuartzTaskInformation addTask(QuartzTaskInformation quartzTaskInformation);
    void initLoadOnlineTasks() throws SchedulerException;
    QuartzTaskRecords addTaskRecord(String taskNo);
    boolean deleteTask(String JobName);
}
