package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.DO.UserDO;

@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
