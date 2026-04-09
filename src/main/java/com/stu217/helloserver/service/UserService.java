package com.stu217.helloserver.service;


import com.stu217.helloserver.common.Result;
import com.stu217.helloserver.dto.UserDTO;

public interface UserService {
    // 用户注册方法
    Result<String> register(UserDTO userDTO);
    // 用户登录方法
    Result<String> login(UserDTO userDTO);
    Result<String> getUserById(Long id);
    Result<Object> getUserPage(Integer pageNum, Integer pageSize);
}