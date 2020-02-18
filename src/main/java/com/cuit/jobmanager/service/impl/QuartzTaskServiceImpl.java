package com.cuit.jobmanager.service.impl;

import com.cuit.jobmanager.model.QuartzTaskInformation;
import com.cuit.jobmanager.model.QuartzTaskRecords;
import com.cuit.jobmanager.service.*;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public QuartzTaskInformation addTask(QuartzTaskInformation quartzTaskInformation) {
        QuartzTaskInformation newQuartzTaskInformation =  quartzTaskInfoService.insert(quartzTaskInformation);
        if (newQuartzTaskInformation == null) return null;
        dynamicJobService.schedule(newQuartzTaskInformation);
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
    public QuartzTaskRecords addTaskRecord(String taskNo){
        QuartzTaskInformation quartzTaskInformation = quartzTaskInfoService.selectTaskByNo(taskNo);
        if (quartzTaskInformation == null) {
            logger.info("taskNo={} not exist or status is frozen!");
            return null;
        }
        QuartzTaskRecords quartzTaskRecords = new QuartzTaskRecords();
        quartzTaskRecords.setExecuteTime(quartzTaskInformation.getLastModifyTime());
        quartzTaskRecords.setTaskNo(quartzTaskInformation.getTaskNo());
        quartzTaskRecords.setTaskStatus(quartzTaskInformation.getFrozenStatus());
        return quartzTaskRecordsService.addTaskRecord(quartzTaskRecords);
    }

    @Override
    public boolean deleteTask(String jobName) {
        return quartzTaskInfoService.deleteTaskInfoByTaskNo(jobName);
    }

    @Override
    public QuartzTaskInformation updateTask(QuartzTaskInformation quartzTaskInformation) {
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
        boolean flag = false;
        if (quartzTaskInformation == null){
            return;
        }
        if (onOrOff == 0 && quartzTaskInformation.getPauseOrResume().equals(onOrOff)){
            dynamicJobService.pauseJob(jobName);
            quartzTaskInformation.setPauseOrResume(0);
            this.updateTask(quartzTaskInformation);
        }else if (onOrOff == 1 && quartzTaskInformation.getPauseOrResume().equals(onOrOff)){
            try {
                List<JobExecutionContext> curJobs = scheduler.getCurrentlyExecutingJobs();
                for (JobExecutionContext context: curJobs){
                    if (jobName.equals(context.getJobDetail().getKey().getName())){
                        dynamicJobService.resumeJob(jobName);
                        quartzTaskInformation.setPauseOrResume(1);
                        this.updateTask(quartzTaskInformation);
                        flag = true;
                    }
                }
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }else throw new RuntimeException();
        if (!flag){
            quartzTaskInformation.setPauseOrResume(1);
            this.updateTask(quartzTaskInformation);
            dynamicJobService.schedule(quartzTaskInformation);
        }
    }

    @Override
    public void runJobNow(String jobName) {
        QuartzTaskInformation quartzTaskInformation = quartzTaskInfoService.selectTaskByNo(jobName);
        if (quartzTaskInformation == null) {
            logger.info("taskNo={} not exist or status is frozen!");
            return;
        }
//        if (isJobExist(jobName)){
            dynamicJobService.runJobNow(jobName);
//        }
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
