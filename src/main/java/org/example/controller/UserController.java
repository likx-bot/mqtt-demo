package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制器
 */
@Tag(name = "用户管理", description = "用户相关的 API 接口")
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户管理")
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * 获取所有用户
     * @return 用户列表
     */
    @GetMapping("/list")
    @Operation(summary = "获取所有用户列表", description = "返回系统中所有用户的详细信息")
    public String getAllUsers() {
        userService.insert();
        return "yes";
    }


}
