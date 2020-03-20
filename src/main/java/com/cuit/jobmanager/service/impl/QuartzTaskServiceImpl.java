package com.cuit.jobmanager.service.impl;

import com.cuit.jobmanager.model.QuartzTaskErrors;
import com.cuit.jobmanager.model.QuartzTaskInformation;
import com.cuit.jobmanager.model.QuartzTaskRecords;
import com.cuit.jobmanager.service.*;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuartzTaskServiceImpl implements QuartzTaskService, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(QuartzTaskServiceImpl.class);

    @Autowired
    private QuartzTaskInfoService quartzTaskInfoService;

    @Autowired
    private QuartzTaskRecordsService quartzTaskRecordsService;

    @Autowired
    private DynamicJobService dynamicJobService;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private QrtzJobDetailsService qrtzJobDetailsService;

    @Autowired
    private QuartzTaskErrorsService quartzTaskErrorsService;
    @Override
    public QuartzTaskInformation addTask(QuartzTaskInformation quartzTaskInformation) {
        QuartzTaskInformation newQuartzTaskInformation =  quartzTaskInfoService.insert(quartzTaskInformation);
        if (newQuartzTaskInformation == null) return null;
//        dynamicJobService.schedule(newQuartzTaskInformation);
        return newQuartzTaskInformation;
    }

    @Override
    public void initLoadOnlineTasks() {
        List<QuartzTaskInformation> quartzTaskInformations = quartzTaskInfoService.findAllUnfrozeTasks();
        if (quartzTaskInformations == null)
            return;
        for (QuartzTaskInformation quartzTaskInformation : quartzTaskInformations){
            if (qrtzJobDetailsService.findQrtzJobDetailsByJobName(quartzTaskInformation.getTaskNo()) != null)
                continue;
            dynamicJobService.schedule(quartzTaskInformation);
        }
    }

    @Override
    public QuartzTaskRecords addTaskRecord(String taskNo, long isFailure){
        QuartzTaskInformation quartzTaskInformation = quartzTaskInfoService.selectTaskByNo(taskNo);
        if (quartzTaskInformation == null) {
            logger.info("taskNo={} not exist or status is frozen!");
            return null;
        }
        QuartzTaskRecords quartzTaskRecords = new QuartzTaskRecords();
        quartzTaskRecords.setExecuteTime(quartzTaskInformation.getLastModifyTime());
        quartzTaskRecords.setTaskNo(quartzTaskInformation.getTaskNo());
        quartzTaskRecords.setSchedulerRule(quartzTaskInformation.getSchedulerRule());
        quartzTaskRecords.setIsFailure(isFailure);
        quartzTaskRecords.setTaskName(quartzTaskInformation.getTaskName());
        return quartzTaskRecordsService.addTaskRecord(quartzTaskRecords);
    }

    @Override
    public boolean deleteTask(String jobName) {
        return quartzTaskInfoService.deleteTaskInfoByTaskNo(jobName);
    }

    @Override
    public QuartzTaskInformation updateTaskAndJob(QuartzTaskInformation quartzTaskInformation) {
        QuartzTaskInformation newQuartzTaskInformation = quartzTaskInfoService.updateTaskInfo(quartzTaskInformation);
        if (newQuartzTaskInformation == null){
            return null;
        }
        dynamicJobService.updateJob(quartzTaskInformation.getTaskNo(), newQuartzTaskInformation);
        return newQuartzTaskInformation;
    }

    @Override
    public void pauseOrResumeJob(String jobName, Integer onOrOff) {
        QuartzTaskInformation quartzTaskInformation = quartzTaskInfoService.selectTaskByNo(jobName);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        if (quartzTaskInformation == null){
            return;
        }
        if (onOrOff == 0){
            try {
                Boolean run = scheduler.checkExists(JobKey.jobKey(jobName));
                if (run){
                    dynamicJobService.pauseJob(jobName);
                    quartzTaskInformation.setPauseOrResume(0);
                    this.updateTask(quartzTaskInformation);
                }
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }else if (onOrOff == 1){
            try {
                Boolean run = scheduler.checkExists(JobKey.jobKey(jobName));
                if (run){
                    dynamicJobService.resumeJob(jobName);
                    quartzTaskInformation.setPauseOrResume(1);
                    this.updateTask(quartzTaskInformation);
                }else {
                    quartzTaskInformation.setPauseOrResume(1);
                    this.updateTask(quartzTaskInformation);
                    dynamicJobService.schedule(quartzTaskInformation);
                }
            } catch (SchedulerException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void runJobNow(String jobName) {
        QuartzTaskInformation quartzTaskInformation = quartzTaskInfoService.selectTaskByNo(jobName);
        if (quartzTaskInformation == null) {
            logger.info("taskNo={} not exist or status is frozen!");
            return;
        }
            dynamicJobService.runJobNow(jobName);
    }

    @Override
    public Page<QuartzTaskInformation> findAllByPage(String keywords, int page, int size) {
        Pageable pageable = PageRequest.of(page-1, 7);
        return quartzTaskInfoService.findAllByPage(keywords, pageable);
    }

    @Override
    public QuartzTaskInformation updateTask(QuartzTaskInformation quartzTaskInformation) {
        QuartzTaskInformation newQuartzTaskInformation = quartzTaskInfoService.updateTaskInfo(quartzTaskInformation);
        if (newQuartzTaskInformation == null){
            return null;
        }
        return newQuartzTaskInformation;
    }

    @Override
    public Map<String, Integer> getTaskStatisticsV1() {
        Map<String, Integer> map = new HashMap<>();
        int num = quartzTaskInfoService.findAllTasks();
        int pausedTasks = quartzTaskInfoService.findTasksPaused();
        int runningTasks = quartzTaskInfoService.findTasksRunning();
        map.put("pausedTasks", pausedTasks);
        map.put("runningTasks", runningTasks);
        map.put("unRunningTasks", num-pausedTasks-runningTasks);
        map.put("allTasks", num);
        return map;
    }

    @Override
    public QuartzTaskErrors addError(QuartzTaskErrors quartzTaskErrors) {
        return quartzTaskErrorsService.addError(quartzTaskErrors);
    }

    @Override
    public Page<QuartzTaskRecords> searchRecordsByPage(String keyWords, int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return quartzTaskRecordsService.searchRecordsByPage(keyWords, pageable);
    }

    @Override
    public QuartzTaskErrors findQuartzTaskError(long id) {
        return quartzTaskErrorsService.findErrorByRecordId(id);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        this.initLoadOnlineTasks();
    }

    private boolean isRunningJob(String jobName) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        List<JobExecutionContext> currentJobs = scheduler.getCurrentlyExecutingJobs();
        for (JobExecutionContext jobExecutionContext: currentJobs){
            String curJobName = jobExecutionContext.getJobDetail().getKey().getName();
            if (curJobName.equalsIgnoreCase(jobName)) return true;
        }
        return false;
    }

    private boolean isJobExist(String jobName){
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        List<JobExecutionContext> curJobs = null;
        try {
            curJobs = scheduler.getCurrentlyExecutingJobs();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        if (curJobs != null) {
            for (JobExecutionContext context: curJobs){
                if (jobName.equals(context.getTrigger().getKey().getName())){
                    return true;
                }
            }
        }
        return false;
    }
}
