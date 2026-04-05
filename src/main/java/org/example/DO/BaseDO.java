package org.example.DO;

import io.swagger.v3.oas.annotations.media.Schema;

public class BaseDO {
    @Schema(description = "ID")
    private String id; // ID
    @Schema(description = "创建时间")
    private String createTime; // 创
    @Schema(description = "更新时间")
    private String updateTime; // 更新时间
    @Schema(description = "是否删除")
    private Boolean deleted;
}
