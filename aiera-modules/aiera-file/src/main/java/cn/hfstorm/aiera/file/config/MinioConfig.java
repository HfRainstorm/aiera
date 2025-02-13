package cn.hfstorm.aiera.file.config;

import io.minio.MinioClient;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Minio 配置信息
 *
 * @author hmy
 */
@Data
@NoArgsConstructor
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

	/**
	 * 内部服务地址
	 */
	private String inUrl;

	/**
	 * 外部服务地址
	 */
	private String outUrl;

	/**
	 * 用户名
	 */
	private String accessKey;

	/**
	 * 密码
	 */
	private String secretKey;

	/**
	 * 存储桶名称
	 */
	private String bucketName;

	@Bean
	public MinioClient getMinioClient() {
		return MinioClient.builder().endpoint(inUrl).credentials(accessKey, secretKey).build();
	}
}
