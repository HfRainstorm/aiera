/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package cn.hfstorm.aiera.ai.biz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * AI Generated Content Model
 *
 * @author : hmy
 * @date : 2025/2/8 15:20
 */
@Data
@Accessors(chain = true)
public class AigcModel implements Serializable {

    private static final long serialVersionUID = -1636328699345841524L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

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
