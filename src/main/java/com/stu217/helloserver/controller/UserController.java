package com.stu217.helloserver.controller;
import com.stu217.helloserver.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController // 标记为REST风格控制器
@RequestMapping("/api/users") // 基础路径
public class UserController {
    // 1. 查：根据ID获取用户信息 - 使用@PathVariable从路径取参数
    @GetMapping("/{id}") // 接口路径：基础路径+/{id}，如/api/users/1001
    public String getUser(@PathVariable("id") Long id) {
        return "查询成功,正在返回ID为" + id + "的用户信息";
    }

    // 2. 增：新增用户 - 使用@RequestBody接收JSON请求体
    @PostMapping // 接口路径：基础路径，无额外拼接
    public String createUser(@RequestBody User user) {
        return "新增成功,接收到用户:" + user.getName() + ",年龄:" + user.getAge();
    }

    // 3. 改：全量更新用户信息 - 路径取ID，请求体取更新后的用户数据
    @PutMapping("/{id}") // 接口路径：基础路径+/{id}
    public String updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        return "更新成功,ID" + id + "的用户已修改为:" + user.getName();
    }

    // 4. 删：根据ID删除用户 - 路径取参数
    @DeleteMapping("/{id}") // 接口路径：基础路径+/{id}
    public String deleteUser(@PathVariable("id") Long id) {
        return "删除成功,已移除ID为" + id + "的用户";
    }
}
