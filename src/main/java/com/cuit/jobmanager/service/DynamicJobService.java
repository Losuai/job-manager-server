package com.cuit.jobmanager.service;

import com.cuit.jobmanager.job.QuartzJobFactory;
import com.cuit.jobmanager.model.QuartzTaskInformation;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class DynamicJobService  {

    private final SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    public DynamicJobService(SchedulerFactoryBean schedulerFactoryBean) {
        this.schedulerFactoryBean = schedulerFactoryBean;
    }

    public JobDataMap getJobDataMap(QuartzTaskInformation quartzTaskInfo){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("id", quartzTaskInfo.getId());
        jobDataMap.put("taskNo", quartzTaskInfo.getTaskNo());
        jobDataMap.put("executorNo", quartzTaskInfo.getExecutorNo());
        jobDataMap.put("sendType", quartzTaskInfo.getSendType());
        jobDataMap.put("url", quartzTaskInfo.getRemark());
        jobDataMap.put("executeParameter", quartzTaskInfo.getExecuteParamter());
        return jobDataMap;
    }

    public JobDetail getJobDetail(QuartzTaskInformation quartzTaskInfo, JobDataMap jobDataMap){
        return JobBuilder.newJob(QuartzJobFactory.class).withIdentity(quartzTaskInfo.getTaskNo())
                .withDescription(quartzTaskInfo.getTaskName()).setJobData(jobDataMap).build();
    }

    public CronTrigger getTrigger(QuartzTaskInformation quartzTaskInformation){
        TriggerKey triggerKey = TriggerKey.triggerKey(quartzTaskInformation.getTaskNo(), Scheduler.DEFAULT_GROUP);
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartzTaskInformation.getSchedulerRule());
        return TriggerBuilder.newTrigger().withIdentity(triggerKey.getName(), triggerKey.getGroup())
                .withSchedule(cronScheduleBuilder).withDescription(quartzTaskInformation.getTaskName()).build();
    }

    public void schedule(CronTrigger trigger, JobDetail jobDetail){
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteJob(String jobName){
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobName, Scheduler.DEFAULT_GROUP);
        boolean isDeleted = false;
        try {
            isDeleted =  scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    public void schedule(QuartzTaskInformation quartzTaskInformation){
        JobDataMap jobDataMap = this.getJobDataMap(quartzTaskInformation);
        JobDetail jobDetail = this.getJobDetail(quartzTaskInformation, jobDataMap);
        CronTrigger cronTrigger = this.getTrigger(quartzTaskInformation);
        this.schedule(cronTrigger, jobDetail);
    }

    public void updateJob(String jobName, QuartzTaskInformation quartzTaskInformation){
        this.deleteJob(jobName);
        this.schedule(quartzTaskInformation);
    }

    public void pauseJob(String jobName){
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.pauseJob(JobKey.jobKey(jobName, Scheduler.DEFAULT_GROUP));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void resumeJob(String jobName){
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.resumeJob(JobKey.jobKey(jobName, Scheduler.DEFAULT_GROUP));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void runJobNow(String jobName){
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.triggerJob(JobKey.jobKey(jobName, Scheduler.DEFAULT_GROUP));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
