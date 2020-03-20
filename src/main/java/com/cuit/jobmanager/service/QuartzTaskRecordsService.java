package com.cuit.jobmanager.service;

import com.cuit.jobmanager.model.QuartzTaskRecords;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuartzTaskRecordsService {
    QuartzTaskRecords addTaskRecord(QuartzTaskRecords quartzTaskRecords);

    Page<QuartzTaskRecords> searchRecordsByPage(String keyWords, Pageable pageable);
}
