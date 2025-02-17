package cn.hfstorm.aiera.common.ai.biz.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author : hmy
 * @date : 2025/2/17
 */
@Data
@TableName(autoResultMap = true)
public class AigcVectorStore implements Serializable {
    @Serial
    private static final long serialVersionUID = 2020477032393716663L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String name;

    private String provider;
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String databaseName;
    private String tableName;
    private Integer dimension;
}
