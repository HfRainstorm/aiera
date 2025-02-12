package cn.hfstorm.aiera.ai.core.component;

import cn.hfstorm.aiera.common.core.component.DataSourceConfig;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreProperties;
import org.springframework.ai.embedding.BatchingStrategy;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.observation.VectorStoreObservationConvention;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author : hmy
 * @date : 2025/2/12 16:09
 */
@Component
@AutoConfiguration(after = JdbcTemplateAutoConfiguration.class)
@ConditionalOnClass({DataSourceConfig.class, PgVectorStore.class, DataSource.class, JdbcTemplate.class})
@EnableConfigurationProperties(PgVectorStoreProperties.class)
public class VectorStoreComponent {


    @Bean
    @ConditionalOnMissingBean
    public PgVectorStore vectorStore(@Qualifier("vectorJdbcTemplate")
                                     JdbcTemplate jdbcTemplate, EmbeddingModel embeddingModel,
                                     PgVectorStoreProperties properties,
                                     ObjectProvider<ObservationRegistry> observationRegistry,
                                     ObjectProvider<VectorStoreObservationConvention> customObservationConvention,
                                     BatchingStrategy batchingStrategy) {

        var initializeSchema = properties.isInitializeSchema();

        return PgVectorStore.builder(jdbcTemplate, embeddingModel).schemaName(properties.getSchemaName()).vectorTableName(properties.getTableName()).vectorTableValidationsEnabled(properties.isSchemaValidation()).dimensions(properties.getDimensions()).distanceType(properties.getDistanceType()).removeExistingVectorStoreTable(properties.isRemoveExistingVectorStoreTable()).indexType(properties.getIndexType()).initializeSchema(initializeSchema).observationRegistry(observationRegistry.getIfUnique(() -> ObservationRegistry.NOOP)).customObservationConvention(customObservationConvention.getIfAvailable(() -> null)).batchingStrategy(batchingStrategy).maxDocumentBatchSize(properties.getMaxDocumentBatchSize()).build();
    }
}
