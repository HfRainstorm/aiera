package cn.hfstorm.aiera.file.enums;

import cn.hfstorm.aiera.common.core.utils.StringUtils;
import cn.hfstorm.aiera.file.service.FastDfsSysFileServiceImpl;
import cn.hfstorm.aiera.file.service.LocalSysFileServiceImpl;
import cn.hfstorm.aiera.file.service.MinioSysFileServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 对象存储服务商枚举
 *
 * @author hmy
 */
@Getter
@AllArgsConstructor
public enum FileEnumd {

	/**
	 * local
	 */
	LOCAL("local", LocalSysFileServiceImpl.class),

	/**
	 * FastDFS
	 */
	FASTDFS("fdfs", FastDfsSysFileServiceImpl.class),

	/**
	 * minio
	 */
	MINIO("minio", MinioSysFileServiceImpl.class);

	private final String value;

	private final Class<?> serviceClass;

	public static Class<?> getServiceClass(String value) {
		for (FileEnumd clazz : values()) {
			if (clazz.getValue().equals(value)) {
				return clazz.getServiceClass();
			}
		}
		return null;
	}

	public static String getServiceName(String value) {
		for (FileEnumd clazz : values()) {
			if (clazz.getValue().equals(value)) {
				return StringUtils.uncapitalize(clazz.getServiceClass().getSimpleName());
			}
		}
		return null;
	}


}
