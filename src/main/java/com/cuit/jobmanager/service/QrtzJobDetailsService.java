package com.cuit.jobmanager.service;

import com.cuit.jobmanager.model.QrtzJobDetails;

public interface QrtzJobDetailsService {
    QrtzJobDetails findQrtzJobDetailsByJobName(String JobName);
}
