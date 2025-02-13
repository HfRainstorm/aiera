package cn.hfstorm.aiera.system.api;

import cn.hfstorm.aiera.system.api.domain.SysFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务
 *
 * @author hmy
 */
public interface RemoteFileService {

    /**
     * 上传文件
     *
     * @param file 文件信息
     * @return 结果
     */
    SysFile upload(MultipartFile file,String fileType);

	/**
	 * 文件删除
	 *
	 * @param path 文件路径，包含文件名
	 */
	void delete(String path, String fileType);

}
