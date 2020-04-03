package com.cuit.jobmanager.service.impl;

import com.cuit.jobmanager.dao.QuartzUserDao;
import com.cuit.jobmanager.model.QuartzUser;
import com.cuit.jobmanager.service.QuartzUserService;
import com.cuit.jobmanager.util.Result;
import com.cuit.jobmanager.util.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class QuartzUserServiceImpl implements QuartzUserService {

    @Autowired
    private QuartzUserDao quartzUserDao;

    @Override
    public Result addUser(QuartzUser quartzUser) {
        QuartzUser existUser = quartzUserDao.findByUsername(quartzUser.getUsername());
        if (existUser == null){
            QuartzUser user = quartzUserDao.save(quartzUser);
            user.setPassword(null);
            return new Result(ResultEnum.SUCCESS, user);
        }else {
            return  new Result(222, "账户名已存在", null);
        }
    }

    @Override
    public Result updateUser(QuartzUser quartzUser) {
        Optional<QuartzUser> existUser = quartzUserDao.findById(quartzUser.getId());
        if (existUser.isPresent()){
            quartzUser.setPassword(existUser.get().getPassword());
            QuartzUser user = quartzUserDao.save(quartzUser);
            user.setPassword(null);
            return new Result(ResultEnum.SUCCESS, user);
        }else {
            return  new Result(222, "不存在该用户", null);
        }
    }

    @Override
    public Result login(String userName, String password) {
        QuartzUser existUser = quartzUserDao.findByUsername(userName);
        if (existUser == null){
            return  new Result(222, "用户不存在", null);
        }else if (existUser.getPassword().equals(password)){
            return new Result(ResultEnum.SUCCESS, existUser);
        }else {
            return  new Result(222, "用户名或密码错误", null);
        }
    }

    @Override
    public Result findUser(String userName) {
        QuartzUser existUser = quartzUserDao.findByUsername(userName);
        if (existUser != null){
            existUser.setPassword(null);
            return new Result(ResultEnum.SUCCESS, existUser);
        }else {
            return  new Result(222, "账户名已存在", null);
        }
    }

    @Override
    public Result updateAvatar(byte[] avatar, Long id) {
        Optional<QuartzUser> existUserOptional = quartzUserDao.findById(id);
        if (existUserOptional.isPresent()){
            QuartzUser existUser = existUserOptional.get();
            existUser.setAvatar(avatar);
            QuartzUser user = quartzUserDao.save(existUser);
            user.setPassword(null);
            return new Result(ResultEnum.SUCCESS, user);
        }else {
            return  new Result(222, "不存在该用户", null);
        }
    }

    @Override
    public byte[] getAvatar(String userName) {
        byte[] avatar;
        QuartzUser existUser = quartzUserDao.findByUsername(userName);
        if (existUser != null){
            avatar = existUser.getAvatar();
        }else {
            return  null;
        }
        return avatar;
    }
}
