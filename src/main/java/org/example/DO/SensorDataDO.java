package org.example.DO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 传感器数据实体
 */
@Data
@TableName("sensor_data")
@Schema(description = "传感器数据实体")
public class SensorDataDO {

    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "设备ID")
    private String deviceId;

    @Schema(description = "传感器类型：temperature-温度, humidity-湿度, pressure-气压")
    private String sensorType;

    @Schema(description = "传感器数值")
    private Double value;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "采集时间")
    private LocalDateTime collectTime;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
