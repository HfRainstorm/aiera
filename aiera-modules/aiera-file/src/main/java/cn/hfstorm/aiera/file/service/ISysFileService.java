package cn.hfstorm.aiera.file.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传接口
 *
 * @author hmy
 */
public interface ISysFileService {
	/**
	 * 文件上传接口
	 *
	 * @param file 上传的文件
	 * @return 访问地址
	 * @throws Exception
	 */
	String uploadFile(MultipartFile file) throws Exception;

	/**
	 * 文件删除
	 *
	 * @param path 文件路径，包含文件名
	 */
	void delete(String path);

}
