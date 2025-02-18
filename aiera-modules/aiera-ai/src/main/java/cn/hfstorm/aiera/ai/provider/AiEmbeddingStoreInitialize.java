//package cn.hfstorm.aiera.ai.provider;
//
//import cn.hfstorm.aiera.ai.biz.service.IAigcVectorStoreService;
//import cn.hfstorm.aiera.ai.holder.SpringContextHolder;
//import cn.hfstorm.aiera.common.ai.domain.AigcVectorStore;
//import cn.hfstorm.aiera.common.ai.enums.VectorStoreTypeEnum;
//import cn.hutool.core.util.StrUtil;
//import dev.langchain4j.store.embedding.elasticsearch.ElasticsearchEmbeddingStore;
//import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore;
//import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
//import dev.langchain4j.store.embedding.redis.RedisEmbeddingStore;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.Header;
//import org.apache.http.HttpHost;
//import org.apache.http.message.BasicHeader;
//import org.elasticsearch.client.RestClient;
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author tycoding
// * @since 2024/10/28
// */
//@Slf4j
//@Component
//@AllArgsConstructor
//public class AiEmbeddingStoreInitialize implements ApplicationContextAware {
//
//    private final IAigcVectorStoreService aigcEmbedStoreService;
//    private final SpringContextHolder contextHolder;
//    private List<AigcVectorStore> modelStore = new ArrayList<>();
//
//    @Override
//    public void setApplicationContext(ApplicationContext context) throws BeansException {
//        init();
//
//        modelStore.forEach(i -> log.info("已成功注册Embedding Store：{}， 配置信息：{}", i.getProvider(), i));
//    }
//
//    public void init() {
//        List<AigcVectorStore> list = aigcEmbedStoreService.list();
//        list.forEach(embed -> {
//            try {
//                if (VectorStoreTypeEnum.REDIS.name().equalsIgnoreCase(embed.getProvider())) {
//                    RedisEmbeddingStore.Builder builder = RedisEmbeddingStore.builder()
//                            .host(embed.getHost())
//                            .port(embed.getPort())
//                            .indexName(embed.getDatabaseName())
//                            .dimension(embed.getDimension());
//                    if (StrUtil.isNotBlank(embed.getUsername()) && StrUtil.isNotBlank(embed.getPassword())) {
//                        builder.user(embed.getUsername()).password(embed.getPassword());
//                    }
//                    RedisEmbeddingStore store = builder.build();
//                    contextHolder.registerBean(embed.getId(), store);
//                }
//                if (VectorStoreTypeEnum.PGVECTOR.name().equalsIgnoreCase(embed.getProvider())) {
//                    PgVectorEmbeddingStore store = PgVectorEmbeddingStore.builder()
//                            .host(embed.getHost())
//                            .port(embed.getPort())
//                            .database(embed.getDatabaseName())
//                            .dimension(embed.getDimension())
//                            .user(embed.getUsername())
//                            .password(embed.getPassword())
//                            .table(embed.getTableName())
//                            .indexListSize(1)
//                            .useIndex(true)
//                            .createTable(true)
//                            .dropTableFirst(false)
//                            .build();
//                    contextHolder.registerBean(embed.getId(), store);
//                }
//                if (VectorStoreTypeEnum.MILVUS.name().equalsIgnoreCase(embed.getProvider())) {
//                    MilvusEmbeddingStore store = MilvusEmbeddingStore.builder()
//                            .host(embed.getHost())
//                            .port(embed.getPort())
//                            .databaseName(embed.getDatabaseName())
//                            .dimension(embed.getDimension())
//                            .username(embed.getUsername())
//                            .password(embed.getPassword())
//                            .collectionName(embed.getTableName())
//                            .build();
//                    contextHolder.registerBean(embed.getId(), store);
//                }
//                if (VectorStoreTypeEnum.ELASTICSEARCH.name().equalsIgnoreCase(embed.getProvider())) {
//                    RestClient restClient = RestClient.builder(HttpHost.create(embed.getHost()))
//                            .setDefaultHeaders(new Header[]{
//                                    new BasicHeader("Authorization", "ApiKey " + embed.getPassword())
//                            }).build();
//                    ElasticsearchEmbeddingStore store = ElasticsearchEmbeddingStore.builder()
//                            .restClient(restClient)
//                            .indexName(embed.getDatabaseName())
//                            .build();
//                    contextHolder.registerBean(embed.getId(), store);
//                }
//                modelStore.add(embed);
//            } catch (Exception e) {
//                e.printStackTrace();
//                log.error("向量数据库初始化失败：[{}] --- [{}]，数据库配置信息：[{}]", embed.getName(), embed.getProvider(), embed);
//            }
//        });
//    }
//}
