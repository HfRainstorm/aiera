package cn.hfstorm.aiera.common.ai.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author hmy
 * @since 2025/2/15
 */
@Data
@Accessors(chain = true)
public class AigcDocs implements Serializable {
    @Serial
    private static final long serialVersionUID = 7054289955242599217L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 知识库ID
     */
    private String knowledgeId;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 来源
     */
    private String origin;
    private String url;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 切片数量
     */
    private Integer sliceNum;

    /**
     * 切片状态
     */
    private Boolean sliceStatus;

    /**
     * 文档内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

}

