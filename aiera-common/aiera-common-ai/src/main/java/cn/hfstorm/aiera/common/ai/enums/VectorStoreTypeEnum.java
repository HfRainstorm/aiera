package cn.hfstorm.aiera.common.ai.enums;


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


}
