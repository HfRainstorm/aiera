//package cn.hfstorm.aiera.ai.config;
//
//
//import cn.hfstorm.aiera.ai.config.properties.ChatConfigProperties;
//import cn.hfstorm.aiera.common.redis.config.properties.RedissonProperties;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.ai.embedding.EmbeddingModel;
//import org.springframework.ai.embedding.TokenCountBatchingStrategy;
//import org.springframework.ai.ollama.OllamaEmbeddingModel;
//import org.springframework.ai.ollama.api.OllamaApi;
//import org.springframework.ai.vectorstore.VectorStore;
//import org.springframework.ai.vectorstore.redis.RedisVectorStore;
//import org.springframework.boot.autoconfigure.AutoConfiguration;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import redis.clients.jedis.JedisPooled;
//
//@Slf4j
//@AutoConfiguration
//@EnableCaching
//@EnableConfigurationProperties({RedissonProperties.class, ChatConfigProperties.class})
//public class ChatConfig {
//
////
////    @Bean
////    public JedisPooled jedisPooled() {
////        return new JedisPooled("<host>", 6379);
////    }
//
////
////    @Bean
////    public VectorStore vectorStore(JedisPooled jedisPooled, EmbeddingModel embeddingModel) {
////        return RedisVectorStore.builder(jedisPooled, embeddingModel)
////                .indexName("custom-index")                // Optional: defaults to "spring-ai-index"
////                .prefix("custom-prefix")                  // Optional: defaults to "embedding:"
////                .metadataFields(                         // Optional: define metadata fields for filtering
////                        RedisVectorStore.MetadataField.tag("country"),
////                        RedisVectorStore.MetadataField.numeric("year"))
////                .initializeSchema(true)                   // Optional: defaults to false
////                .batchingStrategy(new TokenCountBatchingStrategy()) // Optional: defaults to TokenCountBatchingStrategy
////                .build();
////    }
////
////    // This can be any EmbeddingModel implementation
////    @Bean
////    public EmbeddingModel embeddingModel() {
////        return new OllamaEmbeddingModel(new OllamaApi());
////    }
//}
