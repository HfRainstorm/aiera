package cn.hfstorm.aiera.ai.provider;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * 注册向量数据库
 *
 * @author : hmy
 * @date : 2025/2/14
 */
@Slf4j
@AllArgsConstructor
@Configuration
public class VectorStoreInitialize implements ApplicationContextAware {

    List<VectorStore> vectorStores;
    private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        init();
    }

    @Async
    public void init() {
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();

        vectorStores.forEach(vectorStore -> {
            String name = vectorStore.getName();
            BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(VectorStore.class,
                    () -> vectorStore).getBeanDefinition();

            registry.registerBeanDefinition(name, beanDefinition);
            log.info("已成功注册向量数据库：{}", name);
        });

    }
}
