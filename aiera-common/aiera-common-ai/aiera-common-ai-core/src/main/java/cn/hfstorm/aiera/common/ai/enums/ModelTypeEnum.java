package cn.hfstorm.aiera.common.ai.enums;


import lombok.Getter;

/**
 * @author : hmy
 * @date : 2025/2/8 15:27
 */
@Getter
public enum ModelTypeEnum {

    /**
     * 聊天模型
     */
    CHAT,

    /**
     * 嵌入模型
     */
    EMBEDDING,

    /**
     * 图像模型
     */
    TEXT_IMAGE,

    /**
     * 搜索模型
     */
    WEB_SEARCH,

    /**
     * 音频模型
     */
    AUDIO,

    /**
     * 审核模型
     */
    MODERATION;
}
