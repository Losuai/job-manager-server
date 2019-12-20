package com.cuit.jobmanager.service.impl;

import com.cuit.jobmanager.dao.QuartzTaskRecordsDao;
import com.cuit.jobmanager.model.QuartzTaskRecords;
import com.cuit.jobmanager.service.QuartzTaskRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuartzTaskRecordsServiceImpl implements QuartzTaskRecordsService {

    @Autowired
    private QuartzTaskRecordsDao quartzTaskRecordsDao;

    @Override
    public QuartzTaskRecords addTaskRecord(QuartzTaskRecords quartzTaskRecords) {
        quartzTaskRecords.setCreateTime(System.currentTimeMillis());
        quartzTaskRecords.setLastModifyTime(System.currentTimeMillis());
        return quartzTaskRecordsDao.save(quartzTaskRecords);
    }
}
