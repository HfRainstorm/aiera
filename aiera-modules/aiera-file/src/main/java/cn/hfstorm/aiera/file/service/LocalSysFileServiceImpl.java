package cn.hfstorm.aiera.file.service;

import cn.hfstorm.aiera.common.core.utils.file.FileUtils;
import cn.hfstorm.aiera.file.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 本地文件存储
 *
 * @author hmy
 */
// @Primary
@Service
public class LocalSysFileServiceImpl implements ISysFileService {
	/**
	 * 资源映射路径 前缀
	 */
	@Value("${local.prefix}")
	public String localFilePrefix;

	/**
	 * 域名或本机访问地址
	 */
	@Value("${local.domain}")
	public String domain;

	/**
	 * 上传文件存储在本地的根路径
	 */
	@Value("${local.path}")
	private String localFilePath;

	/**
	 * 本地文件上传接口
	 *
	 * @param file 上传的文件
	 * @return 访问地址
	 * @throws Exception
	 */
	@Override
	public String uploadFile(MultipartFile file) throws Exception {
		String name = FileUploadUtils.upload(localFilePath, file);
		String url = domain + localFilePrefix + name;
		return url;
	}

	@Override
	public void delete(String path) {
		FileUtils.del(path);
	}

}
