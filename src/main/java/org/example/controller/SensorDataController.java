package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.DO.SensorDataDO;
import org.example.service.SensorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 传感器数据控制器
 */
@Tag(name = "传感器数据管理", description = "传感器数据相关的 API 接口")
@RestController
@Api(tags = "传感器数据管理")
@RequestMapping("/api/sensor")
public class SensorDataController {

    @Autowired
    private SensorDataService sensorDataService;

    /**
     * 写入一条数据
     */
    @GetMapping("/write")
    @Operation(summary = "写入一条数据", description = "写入一条新的传感器数据记录")
    public String write() {
        sensorDataService.writeData(new SensorDataDO());
        return "Data written successfully";
    }

    /**
     * 写入多条数据
     */
    @GetMapping("/writeListData")
    @Operation(summary = "写入多条数据", description = "写入多条新的传感器数据记录")
    public String writeListData() {
        sensorDataService.writeListData();
        return "List data written successfully";
    }



    /**
     * 读取一条数据
     */
    @GetMapping("/read")
    @Operation(summary = "读取一条数据", description = "读取一条传感器数据记录")
    public Map<String, Object> readData(@Parameter(description = "设备ID")
                                     @RequestParam(required = false) String deviceId) {
        return sensorDataService.readData(deviceId);
    }

    /**
     * 分页查询传感器数据
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询传感器数据", description = "支持按设备ID、传感器类型等条件分页查询")
    public Page<SensorDataDO> getPage(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "设备ID") @RequestParam(required = false) String deviceId,
            @Parameter(description = "传感器类型") @RequestParam(required = false) String sensorType) {
        
        Page<SensorDataDO> page = new Page<>(current, size);
        LambdaQueryWrapper<SensorDataDO> wrapper = new LambdaQueryWrapper<>();
        
        if (deviceId != null && !deviceId.isEmpty()) {
            wrapper.eq(SensorDataDO::getDeviceId, deviceId);
        }
        if (sensorType != null && !sensorType.isEmpty()) {
            wrapper.eq(SensorDataDO::getSensorType, sensorType);
        }
        
        wrapper.orderByDesc(SensorDataDO::getCollectTime);
        return sensorDataService.page(page, wrapper);
    }

    /**
     * 根据ID查询传感器数据
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询传感器数据", description = "通过主键ID获取传感器数据详情")
    public SensorDataDO getById(@Parameter(description = "数据ID") @PathVariable Integer id) {
        return sensorDataService.getById(id);
    }

    /**
     * 新增传感器数据
     */
    @PostMapping("/add")
    @Operation(summary = "新增传感器数据", description = "添加一条新的传感器数据记录")
    public boolean add(@Parameter(description = "传感器数据对象") @RequestBody SensorDataDO sensorData) {
        return sensorDataService.save(sensorData);
    }

    /**
     * 更新传感器数据
     */
    @PutMapping("/update")
    @Operation(summary = "更新传感器数据", description = "更新指定的传感器数据记录")
    public boolean update(@Parameter(description = "传感器数据对象") @RequestBody SensorDataDO sensorData) {
        return sensorDataService.updateById(sensorData);
    }

    /**
     * 删除传感器数据
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除传感器数据", description = "根据ID删除传感器数据记录")
    public boolean delete(@Parameter(description = "数据ID") @PathVariable Integer id) {
        return sensorDataService.removeById(id);
    }

    /**
     * 批量删除传感器数据
     */
    @DeleteMapping("/delete/batch")
    @Operation(summary = "批量删除传感器数据", description = "根据多个ID批量删除传感器数据")
    public boolean deleteBatch(@Parameter(description = "数据ID列表") @RequestBody List<Integer> ids) {
        return sensorDataService.removeByIds(ids);
    }

    /**
     * 查询设备的最新传感器数据
     */
    @GetMapping("/latest/{deviceId}")
    @Operation(summary = "查询设备最新数据", description = "获取指定设备的最新一条传感器数据")
    public SensorDataDO getLatestData(@Parameter(description = "设备ID") @PathVariable String deviceId) {
        LambdaQueryWrapper<SensorDataDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SensorDataDO::getDeviceId, deviceId)
               .orderByDesc(SensorDataDO::getCollectTime)
               .last("LIMIT 1");
        return sensorDataService.getOne(wrapper);
    }
}
