package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.DO.DeviceDO;
import org.example.mapper.DeviceMapper;
import org.example.service.DeviceService;
import org.springframework.stereotype.Service;

/**
 * 设备 Service 实现类
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, DeviceDO> implements DeviceService {
}
