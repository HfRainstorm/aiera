package cn.hfstorm.aiera.file.service;

import cn.hfstorm.aiera.file.utils.FileUploadUtils;
import cn.hfstorm.aiera.system.api.domain.CommonsMultipleFile;
import com.alibaba.nacos.common.utils.IoUtils;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * FastDFS 文件存储
 *
 * @author hmy
 */
@Service
public class FastDfsSysFileServiceImpl implements ISysFileService {
    /**
     * 域名或本机访问地址
     */
    @Value("${fdfs.domain}")
    public String domain;

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * FastDfs文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     */
    @Override
    public String uploadFile(CommonsMultipleFile file) {
        InputStream inputStream = file.getInputStream();
        StorePath storePath = storageClient.uploadFile(inputStream, file.getSize(),
                FileUploadUtils.getExtension(file), null);
        IoUtils.closeQuietly(inputStream);
        return domain + "/" + storePath.getFullPath();
    }

    @Override
    public void delete(String path) {
        path = path.replace(domain + "/", "");
        storageClient.deleteFile(path);
    }

}
