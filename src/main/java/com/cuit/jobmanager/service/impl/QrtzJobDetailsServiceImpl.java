package com.cuit.jobmanager.service.impl;

import com.cuit.jobmanager.dao.QrtzJobDetailsDao;
import com.cuit.jobmanager.model.QQrtzJobDetails;
import com.cuit.jobmanager.model.QrtzJobDetails;
import com.cuit.jobmanager.service.QrtzJobDetailsService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QrtzJobDetailsServiceImpl implements QrtzJobDetailsService {
    private final QQrtzJobDetails qQrtzJobDetails = QQrtzJobDetails.qrtzJobDetails;

    @Autowired
    private QrtzJobDetailsDao qrtzJobDetailsDao;

    @Override
    public QrtzJobDetails findQrtzJobDetailsByJobName(String jobName) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qQrtzJobDetails.jobName.eq(jobName));
        Optional<QrtzJobDetails> qrtzJobDetailsOpt = qrtzJobDetailsDao.findOne(booleanBuilder);
        return qrtzJobDetailsOpt.orElse(null);
    }
}
