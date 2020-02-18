package com.cuit.jobmanager.controller;

import com.cuit.jobmanager.model.QuartzTaskInformation;
import com.cuit.jobmanager.service.QuartzTaskService;
import com.cuit.jobmanager.util.ResultEnum;
import com.cuit.jobmanager.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/quartz")
public class QuartzController {

    @Autowired
    private QuartzTaskService quartzTaskService;

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
        QuartzTaskInformation taskInfo = quartzTaskService.updateTask(quartzTaskInformation);
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
}
