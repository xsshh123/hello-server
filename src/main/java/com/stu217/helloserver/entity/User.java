package com.stu217.helloserver.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
@TableName("sys_user")
public class User {
    @TableId(type = IdType.AUTO) // 主键自增，与数据库表对应
    private String name;
    private Long id;
    private Integer age;

    public User(String name, Long id, Integer age) {
        this.name = name;
        this.id = id;
        this.age = age;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
