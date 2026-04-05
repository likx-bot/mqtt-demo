package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.DO.SensorDataDO;

/**
 * 传感器数据 Mapper 接口
 */
@Mapper
public interface SensorDataMapper extends BaseMapper<SensorDataDO> {
}
