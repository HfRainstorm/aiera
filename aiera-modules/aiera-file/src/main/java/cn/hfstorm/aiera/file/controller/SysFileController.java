package cn.hfstorm.aiera.file.controller;

import cn.hfstorm.aiera.file.enums.FileEnumd;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import cn.hfstorm.aiera.common.core.domain.R;
import cn.hfstorm.aiera.common.core.utils.SpringUtils;
import cn.hfstorm.aiera.common.core.utils.file.FileUtils;
import cn.hfstorm.aiera.file.service.ISysFileService;
import cn.hfstorm.aiera.system.api.domain.SysFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件请求处理
 *
 * @author hmy
 */
@Slf4j
@RestController
public class SysFileController {


	/**
	 * 文件上传请求
	 */
//	@PostMapping("upload")
	@PostMapping(path = "/upload", produces= MediaType.MULTIPART_FORM_DATA_VALUE)
	public R<SysFile> upload(@RequestParam(name = "file") MultipartFile file,
							 @RequestParam(name="fileType") String fileType) {
		try {
			ISysFileService sysFileService = (ISysFileService) SpringUtils.getBean(FileEnumd.getServiceClass(fileType));
			// 上传并返回访问地址
			String url = sysFileService.uploadFile(file);
			SysFile sysFile = new SysFile();
			sysFile.setName(FileUtils.getName(url));
			sysFile.setUrl(url);
			return R.ok(sysFile);
		} catch (Exception e) {
			log.error("上传文件失败", e);
			return R.fail(e.getMessage());
		}
	}

	@PostMapping("/download")
	public R<byte[]> download(String url) {
		byte[] fileByte;
		try {
			fileByte = HttpUtil.downloadBytes(url);
		} catch (HttpException e) {
			log.error("下载文件失败", e);
			return R.fail(e.getMessage());
		}
		return R.ok(fileByte);
	}

	@PostMapping("/delete")
	public R<?> delete(String url,String fileType) {
		ISysFileService sysFileService = (ISysFileService) SpringUtils.getBean(FileEnumd.getServiceClass(fileType));
		sysFileService.delete(url);
		return R.ok();
	}

}
