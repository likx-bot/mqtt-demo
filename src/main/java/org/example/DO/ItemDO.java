package org.example.DO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 物品实体
 */
@Data
@TableName("item")
@Schema(description = "物品实体")
public class ItemDO {

    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "物品编号")
    private String itemCode;

    @Schema(description = "物品名称")
    private String itemName;

    @Schema(description = "物品类型")
    private String itemType;

    @Schema(description = "物品数量")
    private Integer quantity;

    @Schema(description = "物品单位")
    private String unit;

    @Schema(description = "物品价格")
    private Double price;

    @Schema(description = "物品状态：0-禁用，1-启用")
    private Integer status;

    @Schema(description = "存放位置")
    private String location;

    @Schema(description = "备注")
    private String remark;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
