package cn.hfstorm.aiera.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author : hmy
 * @date : 2025/2/7 16:54
 */
@EnableAsync
@SpringBootApplication
@ComponentScan(basePackages = "cn.hfstorm.aiera")
@MapperScan("cn.hfstorm.aiera.*.*.mapper")
public class AiEraAppStarter {


    public static void main(String[] args) {
        SpringApplication.run(AiEraAppStarter.class, args);
    }
}
