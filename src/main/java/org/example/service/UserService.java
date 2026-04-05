package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.DO.UserDO;

public interface UserService extends IService<UserDO> {
    void insert();
}
