package cn.hfstorm.aiera.ai.biz.service;

import cn.hfstorm.aiera.ai.biz.entity.AigcFileStorage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author tycoding
 * @since 2024/1/4
 */
public interface AigcFileStorageService extends IService<AigcFileStorage> {

    AigcFileStorage upload(MultipartFile file, String userId);
}

