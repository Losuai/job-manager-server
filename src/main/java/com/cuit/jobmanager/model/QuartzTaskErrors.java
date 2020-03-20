package com.cuit.jobmanager.model;

import javax.persistence.*;

@Entity
@Table(name = "quartz_task_errors")
public class QuartzTaskErrors {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;
  private long taskExecuteRecordId;
  private String errorKey;
  private String errorValue;
  private long createTime;
  private long lastModifyTime;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getTaskExecuteRecordId() {
    return taskExecuteRecordId;
  }

  public void setTaskExecuteRecordId(long taskExecuteRecordId) {
    this.taskExecuteRecordId = taskExecuteRecordId;
  }


  public String getErrorKey() {
    return errorKey;
  }

  public void setErrorKey(String errorKey) {
    this.errorKey = errorKey;
  }


  public String getErrorValue() {
    return errorValue;
  }

  public void setErrorValue(String errorValue) {
    this.errorValue = errorValue;
  }


  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }


  public long getLastModifyTime() {
    return lastModifyTime;
  }

  public void setLastModifyTime(long lastModifyTime) {
    this.lastModifyTime = lastModifyTime;
  }

}
