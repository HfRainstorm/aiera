package cn.hfstorm.aiera.auth;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.core.env.Environment;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 认证授权中心
 *
 * @author hmy
 */
@Slf4j
@EnableDubbo
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AiEraAuthApplication {
	public static void main(String[] args) throws UnknownHostException {
		SpringApplication application = new SpringApplication(AiEraAuthApplication.class);
		application.setApplicationStartup(new BufferingApplicationStartup(2048));
		application.addInitializers(context -> {
			// 获取Environment对象
			Environment env = context.getEnvironment();
			String filePath = System.getProperty("user.home") + File.separator + ".dubbo" + File.separator + env.getProperty("server.port");
			// 修改dubbo的本地缓存路径，避免缓存冲突
			System.setProperty("dubbo.meta.cache.filePath", filePath);
			System.setProperty("dubbo.mapping.cache.filePath",filePath);
		});
		Environment env = application.run(args).getEnvironment();
		log.info("\n----------------------------------------------------------\n\t" +
						"Application '{}' is running! Access URLs:\n\t" +
						"Local: \t\tlocalhost:{}\n\t" +
						"External: \t{}:{}\n\t" +
						"Profile(s): \t{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"),
				env.getProperty("server.port"),
				InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"),
				env.getActiveProfiles());
		System.out.println("AI ERA 授权中心启动成功！");
	}
}
