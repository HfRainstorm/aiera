package cn.hfstorm.aiera.common.ai.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 向量库信息
 *
 * @author : hmy
 * @date : 2025/2/17
 */
@Data
@TableName(autoResultMap = true)
public class AigcEmbedStore implements Serializable {
    @Serial
    private static final long serialVersionUID = 2020477032393716663L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @Schema(description = "数据库别名")
    private String name;

    @Schema(description = "数据库类型")
    private String provider;

    private String host;
    private Integer port;
    private String username;
    private String password;

    @Schema(description = "数据库名称")
    private String databaseName;

    @Schema(description = "向量表名称")
    private String tableName;

    @Schema(description = "向量维度")
    private Integer dimension;
}
