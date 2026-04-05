package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.DO.SensorDataDO;

import java.util.List;
import java.util.Map;

/**
 * 传感器数据 Service 接口
 */
public interface SensorDataService extends IService<SensorDataDO> {

    /** 写入单条数据 */
    void writeData(SensorDataDO data);

    void writeData123(String message);

    /** 写入单条数据 */
    void writeListData();

    /** 写入真实数据 */
    void writeRealData();

    /** 读取单条数据 */
    Map<String, Object> readData(String deviceId);

    /** 读取多条数据 */
    List<SensorDataDO> readListData(String deviceId);

    /** 批量写入数据 */
    void writeBatch(List<SensorDataDO> dataList);

    /** 查询最近N分钟的数据 */
    List<SensorDataDO> queryRecentData(String deviceId, int minutes);

    /** 查询设备最新一条数据 */
    SensorDataDO queryLatestData(String deviceId);
}
