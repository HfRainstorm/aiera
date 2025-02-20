package cn.hfstorm.aiera.ai.chat.service;

import cn.hfstorm.aiera.common.core.domain.R;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author : hmy
 * @date : 2025/2/14
 */
public interface IEmbeddingService {

    /**
     * embedding
     *
     * @param file
     * @param knowledgeId
     */
    R embeddingFile(MultipartFile file, String knowledgeId) throws IOException;

//    void chat(ChatReq req);
}
