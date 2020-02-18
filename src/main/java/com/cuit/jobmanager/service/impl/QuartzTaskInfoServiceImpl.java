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
    @Transactional
    public QuartzTaskInformation insert(QuartzTaskInformation quartzTaskInformation) {
        String taskNo = quartzTaskInformation.getTaskNo();
        quartzTaskInformation.setVersion(1);
        quartzTaskInformation.setCreateTime(System.currentTimeMillis());
        quartzTaskInformation.setLastModifyTime(System.currentTimeMillis());
        if (this.selectTaskByNo(taskNo) != null){
            return null;
        }
        return quartzTaskInformationDao.save(quartzTaskInformation);
    }

    @Override
    public QuartzTaskInformation selectTaskByNo(String taskNo) {
        Optional<QuartzTaskInformation> quartzTaskInformationOpt = quartzTaskInformationDao.findByTaskNo(taskNo);
        return quartzTaskInformationOpt.orElse(null);
    }

    @Override
    public List<QuartzTaskInformation> findAllUnfrozeTasks() {
        Optional<List<QuartzTaskInformation>> quartzTaskInformationsOpt = quartzTaskInformationDao.findAllByFrozenStatus(ResultEnum.UNFROZEN.name());
        return quartzTaskInformationsOpt.orElse(null);
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

    @Override
    @Transactional
    public QuartzTaskInformation updateTaskInfo(QuartzTaskInformation quartzTaskInformation) {
        Optional<QuartzTaskInformation> quartzTaskInformationOptional = quartzTaskInformationDao.findByTaskNo(quartzTaskInformation.getTaskNo());
        if (!quartzTaskInformationOptional.isPresent())
            return null;
        if (quartzTaskInformation.getVersion() != quartzTaskInformationOptional.get().getVersion()){
            return null;
        }
        if (ResultEnum.UNFROZEN.name().equals(quartzTaskInformation.getFrozenStatus())){
            quartzTaskInformation.setUnfrozenTime(System.currentTimeMillis());
        }else if (ResultEnum.FROZEN.name().equals(quartzTaskInformation.getFrozenStatus())){
            quartzTaskInformation.setFrozenTime(System.currentTimeMillis());
        }
        quartzTaskInformation.setLastModifyTime(System.currentTimeMillis());
        quartzTaskInformation.setVersion(quartzTaskInformation.getVersion()+1);
        return quartzTaskInformationDao.saveAndFlush(quartzTaskInformation);
    }


}
