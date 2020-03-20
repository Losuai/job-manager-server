package com.cuit.jobmanager.service.impl;

import com.cuit.jobmanager.dao.QuartzTaskRecordsDao;
import com.cuit.jobmanager.model.QQuartzTaskRecords;
import com.cuit.jobmanager.model.QuartzTaskRecords;
import com.cuit.jobmanager.service.QuartzTaskRecordsService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuartzTaskRecordsServiceImpl implements QuartzTaskRecordsService {
    private final QQuartzTaskRecords qQuartzTaskRecords = QQuartzTaskRecords.quartzTaskRecords;
    @Autowired
    private QuartzTaskRecordsDao quartzTaskRecordsDao;

    @Override
    public QuartzTaskRecords addTaskRecord(QuartzTaskRecords quartzTaskRecords) {
        quartzTaskRecords.setCreateTime(System.currentTimeMillis());
        quartzTaskRecords.setLastModifyTime(System.currentTimeMillis());
        return quartzTaskRecordsDao.save(quartzTaskRecords);
    }

    @Override
    public Page<QuartzTaskRecords> searchRecordsByPage(String keyWords, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (keyWords == null)
            return quartzTaskRecordsDao.findAll(pageable);
        booleanBuilder.or(qQuartzTaskRecords.taskNo.containsIgnoreCase(keyWords))
                .or(qQuartzTaskRecords.taskName.containsIgnoreCase(keyWords));
        return quartzTaskRecordsDao.findAll(booleanBuilder, pageable);
    }
}
