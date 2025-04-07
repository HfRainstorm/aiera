package cn.hfstorm.aiera.file.service;

import cn.hfstorm.aiera.system.api.domain.CommonsMultipleFile;
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
    default String uploadFile(MultipartFile file) throws Exception {
        return uploadFile(new CommonsMultipleFile(file));
    }

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    String uploadFile(CommonsMultipleFile file) throws Exception;

    /**
     * 文件删除
     *
     * @param path 文件路径，包含文件名
     */
    void delete(String path);

}
