package com.stu217.helloserver.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.stu217.helloserver.common.Result;
import com.stu217.helloserver.common.ResultCode;
import com.stu217.helloserver.dto.UserDTO;
import com.stu217.helloserver.entity.User;
import com.stu217.helloserver.mapper.UserMapper;
import com.stu217.helloserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;





import java.util.concurrent.TimeUnit;
import org.springframework.core.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.stu217.helloserver.entity.UserInfo;
import cn.hutool.json.JSONUtil;
import cn.hutool.core.util.StrUtil;
import com.stu217.helloserver.mapper.UserInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stu217.helloserver.vo.UserDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.stu217.helloserver.common.ResultCode.*;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    // 缓存前缀（建议放到常量类）
    public static final String CACHE_KEY_PREFIX = "user:detail:";

    private final UserInfoMapper userInfoMapper;
    private final StringRedisTemplate redisTemplate;
    @Override
    public Result<UserDetailVO> getUserDetail(Long userId) {
        String key = CACHE_KEY_PREFIX + userId;

        // 1. 查 Redis
        String json = redisTemplate.opsForValue().get(key);
        if (json != null && !json.isBlank()) {
            try {
                UserDetailVO vo = JSONUtil.toBean(json, UserDetailVO.class);
                return Result.success(vo);
            } catch (Exception e) {
                redisTemplate.delete(key); // 脏缓存删掉
            }
        }

        // 2. 查数据库
        UserDetailVO detail = userInfoMapper.getUserDetail(userId);
        if (detail == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }

        // 3. 写入缓存 10 分钟
        redisTemplate.opsForValue().set(
                key,
                JSONUtil.toJsonStr(detail),
                10,
                TimeUnit.MINUTES
        );

        return Result.success(detail);
    }
    @Override
    @Transactional
    public Result<String> updateUserInfo(UserInfo userInfo) {
        if (userInfo == null || userInfo.getUserId() == null) {
            return Result.error(err1);
        }

        // 1. 更新数据库
        LambdaUpdateWrapper<UserInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UserInfo::getUserId, userInfo.getUserId());
        int rows = userInfoMapper.update(userInfo, wrapper);

        if (rows <= 0) {
            return Result.error(err2);
        }

        // 2. 删除 Redis 缓存
        String key = CACHE_KEY_PREFIX + userInfo.getUserId();
        redisTemplate.delete(key);

        return Result.success("更新成功");
    }
    @Override
    @Transactional
    public Result<String> deleteUser(Long userId) {
        int rows = userInfoMapper.deleteById(userId);
        if (rows <= 0) return Result.error(ERROR);

        String key = CACHE_KEY_PREFIX + userId;
        redisTemplate.delete(key);

        return Result.success("删除成功");
    }













//    @Autowired
//    private UserMapper userMapper;
//
//    // 注册
//    @Override
//    public Result<String> register(UserDTO userDTO) {
//        // 1. 判断用户名是否存在
//        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(User::getUsername, userDTO.getUsername());
//        User dbUser = userMapper.selectOne(queryWrapper);
//
//        if (dbUser != null) {
//            return Result.error(ResultCode.USER_HAS_EXISTED);
//        }
//
//        // 2. 插入数据库
//        User user = new User();
//        user.setUsername(userDTO.getUsername());
//        user.setPassword(userDTO.getPassword());
//        userMapper.insert(user);
//
//        return Result.success("注册成功！");
//    }
//
//    // 登录
//    @Override
//    public Result<String> login(UserDTO userDTO) {
//        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(User::getUsername, userDTO.getUsername());
//        User dbUser = userMapper.selectOne(queryWrapper);
//
//        // 用户不存在
//        if (dbUser == null) {
//            return Result.error(USER_NOT_EXIST);
//        }
//
//        // 2. 校验密码是否正确
//        if (!dbUser.getPassword().equals(userDTO.getPassword())) {
//            return Result.error(ResultCode.PASSWORD_ERROR);
//        }
//        // 3. 登录成功，后续可在此生成Token（本步骤暂略，测试阶段可返回"登录成功"）
//        String token = UUID.randomUUID().toString();
//        return Result.success(token);
//
//    }
//
//    // 根据ID查询
//    @Override
//    public Result<String> getUserById(Long id) {
//        User user = userMapper.selectById(id);
//        if (user == null) {
//            return Result.error(USER_NOT_EXIST);
//        }
//        return Result.success("查询成功：" +user.toString());
//    }
//    @Override
//    public Result<Object> getUserPage(Integer pageNum, Integer pageSize) {
//        // 1. 创建分页对象：当前页、每页条数
//        Page<User> page = new Page<>(pageNum, pageSize);
//
//        // 2. 执行分页查询（null 表示无条件，查全部）
//        Page<User> resultPage = userMapper.selectPage(page, null);
//
//        // 3. 返回成功
//        return Result.success(resultPage);
//    }

}



