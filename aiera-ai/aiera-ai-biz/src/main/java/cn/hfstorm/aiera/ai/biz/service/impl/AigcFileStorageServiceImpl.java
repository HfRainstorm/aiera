package cn.hfstorm.aiera.ai.biz.service.impl;

import cn.hfstorm.aiera.ai.biz.entity.AigcFileStorage;
import cn.hfstorm.aiera.ai.biz.mapper.AigcFileStorageMapper;
import cn.hfstorm.aiera.ai.biz.service.AigcFileStorageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author tycoding
 * @since 2024/1/4
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AigcFileStorageServiceImpl extends ServiceImpl<AigcFileStorageMapper, AigcFileStorage> implements AigcFileStorageService {

    @Autowired
    MultipartConfigElement multipartConfigElement;
    @Autowired
    ServletContext context;

    @Override
    public AigcFileStorage upload(MultipartFile file, String userId) {
        log.info(">>>>>>>>>>>>>> OSS文件上传开始： {}", file.getOriginalFilename());
        //获取原始文件名 - 1.jpg  123.0.0.jpg
        String originalFilename = file.getOriginalFilename();

        //构造唯一的文件名 (不能重复) - uuid(通用唯一识别码) de49685b-61c0-4b11-80fa-c71e95924018
        int index = originalFilename.lastIndexOf(".");
        String extname = originalFilename.substring(index);
        String newFileName = UUID.randomUUID().toString() + extname;
        log.info("新的文件名: {}", newFileName);

        //将文件存储在服务器的磁盘目录中 E:\images
        String locationStr = multipartConfigElement.getLocation();
        if (locationStr == null || locationStr.isEmpty()) {
            locationStr = context.getContextPath();
        }
        try {
            file.transferTo(new File(locationStr + File.separator + newFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}

