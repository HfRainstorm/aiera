package cn.hfstorm.aiera.common.ai.enums;


import cn.hfstorm.aiera.common.ai.consts.BeanNamePrefixConstant;
import lombok.Getter;

/**
 * @author : hmy
 * @date : 2025/2/8 15:27
 */
@Getter
public enum VectorStoreTypeEnum {
    REDIS,
    PGVECTOR,
    MILVUS,
    ELASTICSEARCH,
    ;
//
//    /**
//     * 获取模型bean名称
//     *
//     * @return
//     */
//    public String getModelBeanName() {
//        return BeanNamePrefixConstant.PREFIX_EMBED_STORE_MODEL + name();
//    }

}
