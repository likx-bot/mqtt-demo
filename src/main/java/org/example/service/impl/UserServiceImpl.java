package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.DO.UserDO;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Override
    public void insert() {
        UserDO userDO = new UserDO();
        userDO.setUsername("test123");
        userDO.setPassword("test123");
        userDO.setEmail("test123");
        userDO.setPhone("test123");
        userDO.setAvatar("test");
        save(userDO);

    }
}
