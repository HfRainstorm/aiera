package cn.hfstorm.aiera.ai.biz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author hmy
 * @since 2025/2/12
 */
@Data
@Accessors(chain = true)
public class AigcFileStorage implements Serializable {
    private static final long serialVersionUID = -2974325417226349138L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户ID
     */
    private String userId;
}
