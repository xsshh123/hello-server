package com.stu217.helloserver.service.impl;
import com.stu217.helloserver.common.Result;
import com.stu217.helloserver.common.ResultCode;
import com.stu217.helloserver.dto.UserDTO;
import com.stu217.helloserver.service.UserService;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Service
public class UserServiceImpl implements UserService {
    // 静态Map模拟数据库，key=用户名，value=密码
    private static final Map<String, String> userDb = new HashMap<>();

    @Override
    public Result<String> register(UserDTO userDTO) {
        // 1. 校验用户名是否已存在
        if (userDb.containsKey(userDTO.getUsername())) {
            return Result.error(ResultCode.USER_HAS_EXISTED); // 返回已注册异常
        }
        // 2. 不存在则存入模拟数据库
        userDb.put(userDTO.getUsername(), userDTO.getPassword());
        return Result.success("注册成功"); // 返回成功结果
    }
    @Override
    public Result<String> login(UserDTO userDTO) {
        // 1. 校验用户名是否存在
        if (!userDb.containsKey(userDTO.getUsername())) {
            return Result.error(ResultCode.USER_NOT_EXIST); // 返回用户不存在异常
        }
        // 2. 校验密码是否正确
        String dbPassword = userDb.get(userDTO.getUsername());
        if (!dbPassword.equals(userDTO.getPassword())) {
            return Result.error(ResultCode.PASSWORD_ERROR); // 返回密码错误异常
        }
        // 3. 登录成功，后续可在此生成Token（本步骤暂略，测试阶段可返回"登录成功"）
        String token = UUID.randomUUID().toString();
        return Result.success(token);
    }
}
