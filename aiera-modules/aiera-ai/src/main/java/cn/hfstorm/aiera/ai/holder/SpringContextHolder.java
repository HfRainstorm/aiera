package cn.hfstorm.aiera.ai.holder;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * spring context holder
 *
 * @author hmy
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    public static void publishEvent(ApplicationEvent event) {
        if (applicationContext == null) {
            return;
        }
        applicationContext.publishEvent(event);
    }

    public <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public void registerBean(String beanName, Object beanInstance) {
        BeanDefinitionRegistry beanDefinitionRegistry =
                (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();

        BeanDefinitionBuilder beanDefinitionBuilder =
                BeanDefinitionBuilder.genericBeanDefinition((Class<Object>) beanInstance.getClass(),
                        () -> beanInstance);

        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();

        beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
        // 通知其他集群节点
        // 1. Spring Cloud Bus 可以通过消息总线（如 RabbitMQ 或 Kafka）将事件广播到集群中的所有节点。
        // 2.  使用分布式配置中心（如 Nacos、Consul） 将Bean的注册信息存储到分布式配置中心，其他节点监听配置变化并动态注册Bean。（推荐）
    }

    public void unregisterBean(String beanName) {
        BeanDefinitionRegistry beanDefinitionRegistry =
                (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();

        if (beanDefinitionRegistry.containsBeanDefinition(beanName)) {
            beanDefinitionRegistry.removeBeanDefinition(beanName);
        }

        // 通知其他集群节点
    }
}
