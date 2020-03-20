package com.cuit.jobmanager.service.impl;

import com.cuit.jobmanager.dao.QuartzTaskInformationDao;
import com.cuit.jobmanager.model.QQuartzTaskInformation;
import com.cuit.jobmanager.model.QuartzTaskInformation;
import com.cuit.jobmanager.service.DynamicJobService;
import com.cuit.jobmanager.service.QuartzTaskInfoService;
import com.cuit.jobmanager.util.ResultEnum;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class QuartzTaskInfoServiceImpl implements QuartzTaskInfoService {
    private final QQuartzTaskInformation qQuartzTaskInformation = QQuartzTaskInformation.quartzTaskInformation;
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
        quartzTaskInformation.setPauseOrResume(2);
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
        quartzTaskInformation.setCreateTime(quartzTaskInformationOptional.get().getCreateTime());
        return quartzTaskInformationDao.save(quartzTaskInformation);
    }

    @Override
    public Page<QuartzTaskInformation> findAllByPage(String keyWords , Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (keyWords == null)
            return quartzTaskInformationDao.findAll(pageable);
        booleanBuilder.or(qQuartzTaskInformation.taskName.containsIgnoreCase(keyWords)).
                or(qQuartzTaskInformation.taskNo.containsIgnoreCase(keyWords)).
                or(qQuartzTaskInformation.executorNo.containsIgnoreCase(keyWords)).
                or(qQuartzTaskInformation.schedulerRule.containsIgnoreCase(keyWords));
        return quartzTaskInformationDao.findAll(booleanBuilder, pageable);
    }

    @Override
    public int findAllTasks() {
        return quartzTaskInformationDao.findAll().size();
    }

    @Override
    public int findTasksPaused() {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qQuartzTaskInformation.pauseOrResume.eq(0));
        return (int) quartzTaskInformationDao.count(booleanBuilder);
    }

    @Override
    public int findTasksRunning() {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qQuartzTaskInformation.pauseOrResume.eq(1));
        return (int) quartzTaskInformationDao.count(booleanBuilder);
    }

    @Override
    public List getNumOfTask(){
        List list = quartzTaskInformationDao.getNumTask();
        return list;
    }
}
