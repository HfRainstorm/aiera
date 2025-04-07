package cn.hfstorm.aiera.system.api;

import cn.hfstorm.aiera.system.api.domain.CommonsMultipleFile;
import cn.hfstorm.aiera.system.api.domain.SysFile;

/**
 * 文件服务
 *
 * @author hmy
 */
public interface RemoteFileService {

    /**
     * 上传文件
     *
     * @param multipleFile
     * @return
     */
    SysFile upload(CommonsMultipleFile multipleFile);

    /**
     * 文件删除
     *
     * @param path 文件路径，包含文件名
     */
    void delete(String path, String fileType);

}
