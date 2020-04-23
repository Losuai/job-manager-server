package com.cuit.jobmanager.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "quartz_user")
public class QuartzUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @NotNull
  private String username;
  @NotNull
  private String password;
  private String emailAddress;
  private java.sql.Date birthday;
  private Integer gender;
  private String address;
  private String aboutMe;
  @Column(name = "avatar",columnDefinition="mediumblob")
  private byte[] avatar;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }


  public java.sql.Date getBirthday() {
    return birthday;
  }

  public void setBirthday(java.sql.Date birthday) {
    this.birthday = birthday;
  }


  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }


  public String getAboutMe() {
    return aboutMe;
  }

  public void setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
  }

  public byte[] getAvatar() {
    return avatar;
  }

  public void setAvatar(byte[] avatar) {
    this.avatar = avatar;
  }
}
