package com.cuit.jobmanager.controller;

import com.cuit.jobmanager.model.QuartzTaskErrors;
import com.cuit.jobmanager.model.QuartzTaskInformation;
import com.cuit.jobmanager.model.QuartzTaskRecords;
import com.cuit.jobmanager.service.QuartzTaskInfoService;
import com.cuit.jobmanager.service.QuartzTaskService;
import com.cuit.jobmanager.util.ResultEnum;
import com.cuit.jobmanager.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/quartz")
public class QuartzController {

    @Autowired
    private QuartzTaskService quartzTaskService;

    @Autowired
    private QuartzTaskInfoService quartzTaskInfoService;

    @RequestMapping(value = "/task/add", method = RequestMethod.POST)
    public ResponseEntity addTask(@RequestBody QuartzTaskInformation quartzTaskInformation){
        QuartzTaskInformation quartzTaskInformationAdded = quartzTaskService.addTask(quartzTaskInformation);
        if (quartzTaskInformationAdded == null)
            return ResponseEntity.ok(ResultUtil.fail(ResultEnum.SAVE_FAIL));
        return ResponseEntity.ok(ResultUtil.success(quartzTaskInformationAdded));
    }

    @RequestMapping(value = "/task/delete", method = RequestMethod.DELETE)
    public ResponseEntity deleteTask(@RequestParam String jobName){
        Boolean isDeletedTask = quartzTaskService.deleteTask(jobName);
        if (!isDeletedTask){
            return ResponseEntity.ok(ResultUtil.fail(ResultEnum.DELETE_JOB_FAIL));
        }
        return ResponseEntity.ok(ResultUtil.fail(ResultEnum.DELETE_JOB_SUCCESS));
    }

    @RequestMapping(value = "/task/update", method = RequestMethod.POST)
    public ResponseEntity updateTask(@RequestBody QuartzTaskInformation quartzTaskInformation){
        QuartzTaskInformation taskInfo = quartzTaskService.updateTaskAndJob(quartzTaskInformation);
        if (taskInfo == null){
            return ResponseEntity.ok(ResultUtil.fail(ResultEnum.UPDATE_FAIL));
        }
        return ResponseEntity.ok(ResultUtil.success(taskInfo));
    }

    @RequestMapping(value = "/task/option" , method = RequestMethod.GET)
    public ResponseEntity pauseOrResumeJob(@RequestParam String jobName, Integer onOrOff){
        quartzTaskService.pauseOrResumeJob(jobName, onOrOff);
        return ResponseEntity.ok(ResultUtil.success());
    }

    @RequestMapping(value = "/task/runNow", method = RequestMethod.GET)
    public ResponseEntity runJobNow(@RequestParam String jobName){
        quartzTaskService.runJobNow(jobName);
        return ResponseEntity.ok(ResultUtil.success());
    }

    @RequestMapping(value = "/task/find", method = RequestMethod.GET)
    public ResponseEntity findAllTaskByPage(@RequestParam(required = false) String keyWords , int page){
        Page<QuartzTaskInformation> quartzTaskInformations = quartzTaskService.findAllByPage(keyWords, page, 10);
        return ResponseEntity.ok(quartzTaskInformations);
    }

    @RequestMapping(value = "/task/statistics/v1", method = RequestMethod.GET)
    public ResponseEntity getTaskStatisticsV1(){
        return ResponseEntity.ok(quartzTaskService.getTaskStatisticsV1());
    }

    @RequestMapping(value = "/task/num", method = RequestMethod.GET)
    public  ResponseEntity getNumOfTask(){
        List list = quartzTaskInfoService.getNumOfTask();
        return ResponseEntity.ok(ResultUtil.success(list));
    }

    @RequestMapping(value = "record/search", method = RequestMethod.GET)
    public ResponseEntity searchAllRecordsByPage(@RequestParam (required = false)String keyWords, int page){
        Page<QuartzTaskRecords> quartzTaskRecords = quartzTaskService.searchRecordsByPage(keyWords, page, 5);
        return ResponseEntity.ok(ResultUtil.success(quartzTaskRecords));
    }

    @RequestMapping(value = "/task/error", method = RequestMethod.GET)
    public ResponseEntity findQuartzTaskError(@RequestParam long id){
        QuartzTaskErrors quartzTaskErrors = quartzTaskService.findQuartzTaskError(id);
        return ResponseEntity.ok(ResultUtil.success(quartzTaskErrors));
    }
}
