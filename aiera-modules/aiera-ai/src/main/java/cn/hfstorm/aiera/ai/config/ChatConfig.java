//package cn.hfstorm.aiera.ai.config;
//
//
//import org.springframework.ai.embedding.EmbeddingModel;
//import org.springframework.ai.embedding.TokenCountBatchingStrategy;
//import org.springframework.ai.vectorstore.VectorStore;
//import org.springframework.ai.vectorstore.redis.RedisVectorStore;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import redis.clients.jedis.JedisPooled;
//
//@Configuration
//@EnableConfigurationProperties(RedisProperties.class)
//public class ChatConfig {
//    @Bean
//    public JedisPooled jedisPooled(RedisProperties redisProperties) {
//        return new JedisPooled(redisProperties.getHost(), redisProperties.getPort());
//    }
//
//    @Bean
//    public VectorStore vectorStore(JedisPooled jedisPooled, EmbeddingModel embeddingModel) {
//        return RedisVectorStore.builder(jedisPooled, embeddingModel).indexName("custom-index")                //
//                // Optional: defaults to "spring-ai-index"
//                .prefix("custom-prefix")                  // Optional: defaults to "embedding:"
//                .metadataFields(                         // Optional: define metadata fields for filtering
//                        RedisVectorStore.MetadataField.tag("country"),
//                        RedisVectorStore.MetadataField.numeric("year")).initializeSchema(true)                   //
//                // Optional: defaults to false
//                .batchingStrategy(new TokenCountBatchingStrategy()) // Optional: defaults to TokenCountBatchingStrategy
//                .build();
//    }
//
////    // This can be any EmbeddingModel implementation
////    @Bean
////    public EmbeddingModel embeddingModel() {
////        return new OpenAiEmbeddingModel(new OpenAiApi(System.getenv("OPENAI_API_KEY")));
////    }
//}
