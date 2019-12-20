package com.cuit.jobmanager.model;

import javax.persistence.*;

@Entity
@Table(name = "quartz_task_records")
public class QuartzTaskRecords {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;
  private String taskNo;
  private String timeKeyValue;
  private long executeTime;
  private String taskStatus;
  private long failcount;
  private String failReason;
  private long createTime;
  private long lastModifyTime;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getTaskNo() {
    return taskNo;
  }

  public void setTaskNo(String taskNo) {
    this.taskNo = taskNo;
  }


  public String getTimeKeyValue() {
    return timeKeyValue;
  }

  public void setTimeKeyValue(String timeKeyValue) {
    this.timeKeyValue = timeKeyValue;
  }


  public long getExecuteTime() {
    return executeTime;
  }

  public void setExecuteTime(long executeTime) {
    this.executeTime = executeTime;
  }


  public String getTaskStatus() {
    return taskStatus;
  }

  public void setTaskStatus(String taskStatus) {
    this.taskStatus = taskStatus;
  }


  public long getFailcount() {
    return failcount;
  }

  public void setFailcount(long failcount) {
    this.failcount = failcount;
  }


  public String getFailReason() {
    return failReason;
  }

  public void setFailReason(String failReason) {
    this.failReason = failReason;
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
