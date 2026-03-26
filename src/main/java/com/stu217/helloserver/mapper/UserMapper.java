package com.stu217.helloserver.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stu217.helloserver.entity.User;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
