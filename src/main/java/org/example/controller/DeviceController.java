package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.DO.DeviceDO;
import org.example.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备管理控制器
 */
@Tag(name = "设备管理", description = "设备相关的 API 接口")
@RestController
@Api(tags = "设备管理")
@RequestMapping("/api/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    /**
     * 分页查询设备
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询设备", description = "支持按设备名称、类型、状态等条件分页查询")
    public Page<DeviceDO> getPage(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "设备名称") @RequestParam(required = false) String deviceName,
            @Parameter(description = "设备类型") @RequestParam(required = false) String deviceType,
            @Parameter(description = "设备状态") @RequestParam(required = false) Integer status) {
        
        Page<DeviceDO> page = new Page<>(current, size);
        LambdaQueryWrapper<DeviceDO> wrapper = new LambdaQueryWrapper<>();
        
        if (deviceName != null && !deviceName.isEmpty()) {
            wrapper.like(DeviceDO::getDeviceName, deviceName);
        }
        if (deviceType != null && !deviceType.isEmpty()) {
            wrapper.eq(DeviceDO::getDeviceType, deviceType);
        }
        if (status != null) {
            wrapper.eq(DeviceDO::getStatus, status);
        }
        
        wrapper.orderByDesc(DeviceDO::getCreateTime);
        return deviceService.page(page, wrapper);
    }

    /**
     * 根据ID查询设备
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询设备", description = "通过主键ID获取设备详情")
    public DeviceDO getById(@Parameter(description = "设备ID") @PathVariable Integer id) {
        return deviceService.getById(id);
    }

    /**
     * 根据设备编号查询设备
     */
    @GetMapping("/code/{deviceCode}")
    @Operation(summary = "根据设备编号查询", description = "通过设备编号获取设备信息")
    public DeviceDO getByCode(@Parameter(description = "设备编号") @PathVariable String deviceCode) {
        LambdaQueryWrapper<DeviceDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceDO::getDeviceCode, deviceCode);
        return deviceService.getOne(wrapper);
    }

    /**
     * 新增设备
     */
    @PostMapping("/add")
    @Operation(summary = "新增设备", description = "添加一个新的设备")
    public boolean add(@Parameter(description = "设备对象") @RequestBody DeviceDO device) {
        return deviceService.save(device);
    }

    /**
     * 更新设备
     */
    @PutMapping("/update")
    @Operation(summary = "更新设备", description = "更新指定的设备信息")
    public boolean update(@Parameter(description = "设备对象") @RequestBody DeviceDO device) {
        return deviceService.updateById(device);
    }

    /**
     * 删除设备
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除设备", description = "根据ID删除设备")
    public boolean delete(@Parameter(description = "设备ID") @PathVariable Integer id) {
        return deviceService.removeById(id);
    }

    /**
     * 批量删除设备
     */
    @DeleteMapping("/delete/batch")
    @Operation(summary = "批量删除设备", description = "根据多个ID批量删除设备")
    public boolean deleteBatch(@Parameter(description = "设备ID列表") @RequestBody List<Integer> ids) {
        return deviceService.removeByIds(ids);
    }

    /**
     * 更新设备状态
     */
    @PutMapping("/status/{id}/{status}")
    @Operation(summary = "更新设备状态", description = "修改设备的在线/离线/故障状态")
    public boolean updateStatus(
            @Parameter(description = "设备ID") @PathVariable Integer id,
            @Parameter(description = "状态：0-离线，1-在线，2-故障") @PathVariable Integer status) {
        DeviceDO device = new DeviceDO();
        device.setId(id);
        device.setStatus(status);
        return deviceService.updateById(device);
    }

    /**
     * 查询所有在线设备
     */
    @GetMapping("/online")
    @Operation(summary = "查询在线设备", description = "获取所有在线状态的设备列表")
    public List<DeviceDO> getOnlineDevices() {
        LambdaQueryWrapper<DeviceDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceDO::getStatus, 1);
        return deviceService.list(wrapper);
    }
}
