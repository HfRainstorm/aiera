package cn.hfstorm.aiera.common.core.component;

import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author : hmy
 * @date : 2025/2/12 16:00
 */
@AutoConfiguration(after = JdbcTemplateAutoConfiguration.class)
@Component
@ConditionalOnClass({DataSource.class, JdbcTemplate.class})
public class DataSourceConfig {
    /**
     * 创建 springboot 的数据库的数据源 DataSource
     *
     * @return
     */
    @Primary
    @Bean("bizDataSource")
    @ConfigurationProperties("spring.datasource.biz-db")
    public DataSource bizDataSource() {

        return DruidDataSourceBuilder.create().build();
    }




    /**
     * 创建数据库SpringBoot 对应的 JdbcTemplate.
     * 用 @Qualifier 注解 指定使用的是哪一个 Datasource
     *
     * @param dataSourceOne
     * @return
     */
    @Bean("jdbcTemplate")
    JdbcTemplate bizJdbcTemplate(@Qualifier("bizDataSource") DataSource dataSourceOne) {
        return new JdbcTemplate(dataSourceOne);
    }

    /**
     * 创建 springboot2 的数据库的数据源 DataSource
     *
     * @return
     */
    @Bean("vectorDataSource")
    @ConfigurationProperties("spring.datasource.vector-db")
    public DataSource vectorDataSource() {
        return DruidDataSourceBuilder.create().build();
    }
    /**
     * 创建数据库SpringBoot2 对应的 JdbcTemplate.
     * 用 @Qualifier 注解 指定使用的是哪一个 Datasource
     *
     * @param vectorDataSource
     * @return
     */
    @Bean("vectorJdbcTemplate")
    JdbcTemplate vectorJdbcTemplate(@Qualifier("vectorDataSource") DataSource vectorDataSource) {
        return new JdbcTemplate(vectorDataSource);
    }
}