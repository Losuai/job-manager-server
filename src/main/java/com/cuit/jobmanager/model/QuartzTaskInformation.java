package com.cuit.jobmanager.model;

import javax.persistence.*;

@Entity
@Table(name = "quartz_task_information")
public class QuartzTaskInformation {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;

  private long version;
  private String taskNo;
  private String taskName;
  private String schedulerRule;
  private String frozenStatus;
  private String executorNo;
  private long frozenTime;
  private long unfrozenTime;
  private long createTime;
  private long lastModifyTime;
  private String sendType;
  private String url;
  private String executeParamter;
  private String timeKey;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getVersion() {
    return version;
  }

  public void setVersion(long version) {
    this.version = version;
  }


  public String getTaskNo() {
    return taskNo;
  }

  public void setTaskNo(String taskNo) {
    this.taskNo = taskNo;
  }


  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }


  public String getSchedulerRule() {
    return schedulerRule;
  }

  public void setSchedulerRule(String schedulerRule) {
    this.schedulerRule = schedulerRule;
  }


  public String getFrozenStatus() {
    return frozenStatus;
  }

  public void setFrozenStatus(String frozenStatus) {
    this.frozenStatus = frozenStatus;
  }


  public String getExecutorNo() {
    return executorNo;
  }

  public void setExecutorNo(String executorNo) {
    this.executorNo = executorNo;
  }


  public long getFrozenTime() {
    return frozenTime;
  }

  public void setFrozenTime(long frozenTime) {
    this.frozenTime = frozenTime;
  }


  public long getUnfrozenTime() {
    return unfrozenTime;
  }

  public void setUnfrozenTime(long unfrozenTime) {
    this.unfrozenTime = unfrozenTime;
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


  public String getSendType() {
    return sendType;
  }

  public void setSendType(String sendType) {
    this.sendType = sendType;
  }


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }


  public String getExecuteParamter() {
    return executeParamter;
  }

  public void setExecuteParamter(String executeParamter) {
    this.executeParamter = executeParamter;
  }


  public String getTimeKey() {
    return timeKey;
  }

  public void setTimeKey(String timeKey) {
    this.timeKey = timeKey;
  }

}
