package com.cuit.jobmanager.model;

import javax.persistence.*;

@Entity
@Table(name = "quartz_task_records")
public class QuartzTaskRecords {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;
  private String taskNo;
  private long executeTime;
  private String schedulerRule;
  private long isFailure;
  private String taskName;
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

  public long getExecuteTime() {
    return executeTime;
  }

  public void setExecuteTime(long executeTime) {
    this.executeTime = executeTime;
  }


  public String getSchedulerRule() {
    return schedulerRule;
  }

  public void setSchedulerRule(String schedulerRule) {
    this.schedulerRule = schedulerRule;
  }


  public long getIsFailure() {
    return isFailure;
  }

  public void setIsFailure(long isFailure) {
    this.isFailure = isFailure;
  }


  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
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
