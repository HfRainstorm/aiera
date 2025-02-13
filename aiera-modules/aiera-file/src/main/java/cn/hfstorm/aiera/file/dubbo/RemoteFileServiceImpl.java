package cn.hfstorm.aiera.file.dubbo;

import cn.hfstorm.aiera.file.enums.FileEnumd;
import cn.hfstorm.aiera.common.core.utils.SpringUtils;
import cn.hfstorm.aiera.common.core.utils.file.FileUtils;
import cn.hfstorm.aiera.file.service.ISysFileService;
import cn.hfstorm.aiera.system.api.RemoteFileService;
import cn.hfstorm.aiera.system.api.domain.SysFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务
 *
 * @author hmy
 */
@Slf4j
@RequiredArgsConstructor
@Service
@DubboService
public class RemoteFileServiceImpl implements RemoteFileService {


	@Override
	public SysFile upload(MultipartFile file, String fileType) {
		SysFile sysFile = null;
		try {
			ISysFileService sysFileService = (ISysFileService) SpringUtils.getBean(FileEnumd.getServiceClass(fileType));
			// 上传并返回访问地址
			String url = sysFileService.uploadFile(file);
			sysFile = new SysFile();
			sysFile.setName(FileUtils.getName(url));
			sysFile.setUrl(url);
		} catch (Exception e) {
			log.error("上传文件失败", e);
		}
		return sysFile;
	}

	@Override
	public void delete(String path, String fileType) {
		ISysFileService sysFileService = (ISysFileService) SpringUtils.getBean(FileEnumd.getServiceClass(fileType));
		sysFileService.delete(path);
	}
}
