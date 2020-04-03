package com.cuit.jobmanager.controller;

import com.cuit.jobmanager.model.QuartzUser;
import com.cuit.jobmanager.service.QuartzUserService;
import com.cuit.jobmanager.util.Result;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/quartz")
public class UserController {
    @Autowired
    private QuartzUserService quartzUserService;

    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestBody QuartzUser quartzUser){
        Result result = quartzUserService.addUser(quartzUser);
        return ResponseEntity.ok(result);
    }
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public ResponseEntity updateUser(@RequestBody QuartzUser quartzUser){
        Result result = quartzUserService.updateUser(quartzUser);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody JsonNode jsonNode){
        String userName = jsonNode.path("userName").textValue();
        String password = jsonNode.path("password").textValue();
        Result result = quartzUserService.login(userName, password);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/user/find", method = RequestMethod.GET)
    public ResponseEntity find(@RequestParam String username){
        if (username.equals("")){
            return null;
        }
        Result result = quartzUserService.findUser(username);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/user/update/avatar",method = RequestMethod.POST)
    public ResponseEntity updateAvatar(@RequestParam(name = "userID")Long id, @RequestParam(name = "avatar")MultipartFile multipartFile){
        byte[]   avatar = new byte[0];
        Result result = null;
        if (!multipartFile.isEmpty() && id !=null){
            try {
                avatar = multipartFile.getBytes();
                result = quartzUserService.updateAvatar(avatar, id);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            return  ResponseEntity.ok(new Result(222, "请求参数错误", null)); 
        }
        
        return  ResponseEntity.ok(avatar);
    }

    @RequestMapping(value = "/user/find/avatar",method = RequestMethod.GET)
    public ResponseEntity findUserAvatar(@RequestParam String username){
        byte[] avatar = quartzUserService.getAvatar(username);
        return ResponseEntity.ok(avatar);
    }
}
