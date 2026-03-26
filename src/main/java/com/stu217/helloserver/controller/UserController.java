package com.stu217.helloserver.controller;
import com.stu217.helloserver.common.Result;
import com.stu217.helloserver.entity.User;
import com.stu217.helloserver.dto.UserDTO;
import com.stu217.helloserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController // 标记为REST风格控制器
@RequestMapping("/api/users") // 基础路径
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping
    public Result<String> register(@RequestBody UserDTO userDTO) {
        // 调用Service层的注册方法
        return userService.register(userDTO);
    }
    // 2. 用户登录接口：POST /api/users/login
    @PostMapping("/login")
    public Result<String> login(@RequestBody UserDTO userDTO) {
        // 调用Service层的登录方法
        return userService.login(userDTO);
    }
    // 3. 测试查询接口：GET /api/users/{id}（用于后续拦截器测试）
    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable("id") Long id) {
        return Result.success("查询成功，正在返回ID为" + id + "的用户信息");
    }



    // 2. 增：新增用户 - 使用@RequestBody接收JSON请求体
    @PostMapping // 接口路径：基础路径，无额外拼接
    public Result<String> createUser(@RequestBody User user) {
        String data="新增成功,接收到用户:" + user.getName() + ",年龄:" + user.getAge();
        return Result.success(data);
    }

    // 3. 改：全量更新用户信息 - 路径取ID，请求体取更新后的用户数据
    @PutMapping("/{id}") // 接口路径：基础路径+/{id}
    public Result<String> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        String data="更新成功,ID" + id + "的用户已修改为:" + user.getName();
        return Result.success(data);
    }

    // 4. 删：根据ID删除用户 - 路径取参数
    @DeleteMapping("/{id}") // 接口路径：基础路径+/{id}
    public Result<String> deleteUser(@PathVariable("id") Long id) {
        String data="删除成功,已移除ID为" + id + "的用户";
        return Result.success(data);
    }

}
