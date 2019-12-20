package com.cuit.jobmanager.service.impl;

import com.cuit.jobmanager.dao.QuartzTaskInformationDao;
import com.cuit.jobmanager.model.QuartzTaskInformation;
import com.cuit.jobmanager.service.DynamicJobService;
import com.cuit.jobmanager.service.QuartzTaskInfoService;
import com.cuit.jobmanager.util.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class QuartzTaskInfoServiceImpl implements QuartzTaskInfoService {
    @Autowired
    private QuartzTaskInformationDao quartzTaskInformationDao;

    @Autowired
    private DynamicJobService dynamicJobService;

    @Override
    public QuartzTaskInformation insert(QuartzTaskInformation quartzTaskInformation) {
        String taskNo = quartzTaskInformation.getTaskNo();
        quartzTaskInformation.setVersion(0);
        quartzTaskInformation.setCreateTime(System.currentTimeMillis());
        quartzTaskInformation.setLastModifyTime(System.currentTimeMillis());
        if (this.selectTaskByNo(taskNo).isPresent()){
            return null;
        }
        return quartzTaskInformationDao.save(quartzTaskInformation);
    }

    @Override
    public Optional<QuartzTaskInformation> selectTaskByNo(String taskNo) {
        Optional<QuartzTaskInformation> quartzTaskInformation = quartzTaskInformationDao.findByTaskNo(taskNo);
        return quartzTaskInformation;
    }

    @Override
    public Optional<List<QuartzTaskInformation>> findAllUnfrozeTasks() {
        Optional<List<QuartzTaskInformation>> quartzTaskInformations = quartzTaskInformationDao.findAllByFrozenStatus(ResultEnum.UNFROZEN.name());
        return quartzTaskInformations;
    }

    @Override
    @Transactional
    public boolean deleteTaskInfoByTaskNo(String taskNo) {
        Optional<QuartzTaskInformation> quartzTaskInformationOptional = quartzTaskInformationDao.findByTaskNo(taskNo);
        if (!quartzTaskInformationOptional.isPresent()){
            return false;
        }
        Integer isDeletedTaskInfo = quartzTaskInformationDao.deleteByTaskNo(taskNo);
        boolean isDeletedJob = dynamicJobService.deleteJob(taskNo);
        return isDeletedJob && (isDeletedTaskInfo == 1);
    }

}
