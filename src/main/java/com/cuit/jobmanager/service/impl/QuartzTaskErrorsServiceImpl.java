package com.cuit.jobmanager.service.impl;

import com.cuit.jobmanager.dao.QuartzTaskErrorsDao;
import com.cuit.jobmanager.model.QQuartzTaskErrors;
import com.cuit.jobmanager.model.QuartzTaskErrors;
import com.cuit.jobmanager.service.QuartzTaskErrorsService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuartzTaskErrorsServiceImpl implements QuartzTaskErrorsService {
    private final QQuartzTaskErrors qQuartzTaskErrors = QQuartzTaskErrors.quartzTaskErrors;
    @Autowired
    private QuartzTaskErrorsDao quartzTaskErrorsDao;
    @Override
    public QuartzTaskErrors addError(QuartzTaskErrors quartzTaskErrors) {
        return quartzTaskErrorsDao.save(quartzTaskErrors);
    }

    @Override
    public QuartzTaskErrors findErrorByRecordId(long id) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qQuartzTaskErrors.taskExecuteRecordId.eq(id));
        Optional<QuartzTaskErrors> quartzTaskErrors =quartzTaskErrorsDao.findOne(booleanBuilder);
        return quartzTaskErrors.orElse(null);
    }
}
