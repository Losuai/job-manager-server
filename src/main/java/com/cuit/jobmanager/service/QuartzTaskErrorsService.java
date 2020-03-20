package com.cuit.jobmanager.service;

import com.cuit.jobmanager.model.QuartzTaskErrors;

public interface QuartzTaskErrorsService {
    QuartzTaskErrors addError(QuartzTaskErrors quartzTaskErrors);
    QuartzTaskErrors findErrorByRecordId(long id);
}
