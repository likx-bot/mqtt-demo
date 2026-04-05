package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.DO.DeviceDO;

/**
 * 设备 Mapper 接口
 */
@Mapper
public interface DeviceMapper extends BaseMapper<DeviceDO> {
}
