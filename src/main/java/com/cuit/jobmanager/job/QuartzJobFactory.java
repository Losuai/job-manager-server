package com.cuit.jobmanager.job;

import com.cuit.jobmanager.model.QuartzTaskRecords;
import com.cuit.jobmanager.service.QuartzTaskService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class QuartzJobFactory extends QuartzJobBean {

    private static Logger logger = LoggerFactory.getLogger(QuartzJobFactory.class);

    @Autowired
    private QuartzTaskService quartzTaskService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String taskNo = jobDataMap.getString("taskNo");
        String taskName = jobDataMap.getString("taskName");
        String schedulerRule = jobDataMap.getString("schedulerRule");
        String frozenStatus = jobDataMap.getString("schedulerRule");
        String executorNo = jobDataMap.getString("executorNo");
        String executeParameter = jobDataMap.getString("executeParameter");
        String sendType = jobDataMap.getString("sendType");
        String url = jobDataMap.getString("url");
        logger.info("定时任务被执行:taskNo={},executorNo={},sendType={},url={},executeParameter={}", taskNo, executorNo, sendType, url, executeParameter);
        QuartzTaskRecords quartzTaskRecords = null;
        quartzTaskRecords = quartzTaskService.addTaskRecord(taskNo);
        if (quartzTaskRecords == null){
            logger.info("taskNo={}保存执行记录失败", taskNo);
            return;
        }

    }
}
