package com.cuit.jobmanager.service.impl;

import com.cuit.jobmanager.model.QrtzJobDetails;
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
import java.util.Optional;
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
        return quartzTaskInfoService.insert(quartzTaskInformation);
    }

    @Override
    public void initLoadOnlineTasks() throws SchedulerException {
        Optional<List<QuartzTaskInformation>> quartzTaskInformations = quartzTaskInfoService.findAllUnfrozeTasks();
        if (!quartzTaskInformations.isPresent())
            return;
        for (QuartzTaskInformation quartzTaskInformation : quartzTaskInformations.get()){
            if (qrtzJobDetailsService.findQrtzJobDetailsByJobName(quartzTaskInformation.getTaskNo()) != null)
                continue;
            this.schedule(quartzTaskInformation);
        }
    }

    @Override
    public QuartzTaskRecords addTaskRecord(String taskNo){
        Optional<QuartzTaskInformation> quartzTaskInformationOptional = quartzTaskInfoService.selectTaskByNo(taskNo);
        if (quartzTaskInformationOptional == null) {
            logger.info("taskNo={} not exist or status is frozen!");
            return null;
        }
        QuartzTaskRecords quartzTaskRecords = new QuartzTaskRecords();
        QuartzTaskInformation quartzTaskInformation = quartzTaskInformationOptional.get();
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
    public void afterPropertiesSet() throws Exception {
        this.initLoadOnlineTasks();
    }

    private void schedule(QuartzTaskInformation quartzTaskInformation){
        JobDataMap jobDataMap = dynamicJobService.getJobDataMap(quartzTaskInformation);
        JobDetail jobDetail = dynamicJobService.getJobDetail(quartzTaskInformation, jobDataMap);
        CronTrigger cronTrigger = dynamicJobService.getTrigger(quartzTaskInformation);
        dynamicJobService.schedule(cronTrigger, jobDetail);
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
}
