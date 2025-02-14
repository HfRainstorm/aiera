package cn.hfstorm.aiera.common.ai.biz.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * aigc_model
 *
 * @author : hmy
 * @date : 2025/2/8 15:20
 */
@Data
@Accessors(chain = true)
public class AigcModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 注册模型id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 模型类型
     */
    private String type;
    private String model;
    private String provider;
    private String name;
    private Integer responseLimit;
    private Double temperature = 0.2;
    private Double topP = 0.0;
    private String apiKey;
    private String secretKey;
    private String baseUrl;
    private String endpoint;
    private String geminiLocation;
    private String geminiProject;
    private String azureDeploymentName;
    private String imageSize;
    private String imageQuality;
    private String imageStyle;
    private Integer dimension;
}