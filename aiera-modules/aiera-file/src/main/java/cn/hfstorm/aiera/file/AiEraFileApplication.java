package cn.hfstorm.aiera.file;

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
 * 文件服务
 *
 * @author hmy
 */
@Slf4j
@EnableDubbo
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AiEraFileApplication {
	public static void main(String[] args) throws UnknownHostException {
		SpringApplication application = new SpringApplication(AiEraFileApplication.class);
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
		System.out.println("(♥◠‿◠)ﾉﾞ  文件服务模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
	}
}
