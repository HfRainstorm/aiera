package cn.hfstorm.aiera.ai.provider;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 注册向量数据库
 *
 * @author : hmy
 * @date : 2025/2/14
 */
@Slf4j
@Component
@AllArgsConstructor
public class EmbeddingProvider implements ApplicationContextAware {

    List<VectorStore> vectorStores;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        vectorStores.forEach(i -> log.info("已成功注册Vector Store：{}", i.getName()));
    }


    public List<VectorStore> getVectorStoreList() {
        return vectorStores;
    }

}
