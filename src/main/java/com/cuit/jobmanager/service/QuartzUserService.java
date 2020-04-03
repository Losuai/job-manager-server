package com.cuit.jobmanager.service;

import com.cuit.jobmanager.model.QuartzUser;
import com.cuit.jobmanager.util.Result;

public interface QuartzUserService {
    Result addUser(QuartzUser quartzUser);
    Result updateUser(QuartzUser quartzUser);
    Result login(String userName, String password);
    Result findUser(String userName);
    Result updateAvatar(byte[] avatar, Long id);
    byte[] getAvatar(String userName);
}
