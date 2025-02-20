package cn.hfstorm.aiera.ai.chat.service.impl;

import cn.hfstorm.aiera.ai.chat.service.IEmbeddingService;
import cn.hfstorm.aiera.common.core.domain.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author : hmy
 * @date : 2025/2/14
 */
@Slf4j
@Service
@AllArgsConstructor
public class EmbeddingServiceImpl implements IEmbeddingService {

    @Override
    public R embeddingFile(MultipartFile file, String knowledgeId) throws IOException {
        // 判断用户权限

        // 上传到oss

        // oss结果信息保存到数据库


        // 进行分片存储到向量数据库

        // 从IO流中读取文件
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(new InputStreamResource(file.getInputStream()));
        // 将文本内容划分成更小的块
        List<Document> splitDocuments = new TokenTextSplitter()
                .apply(tikaDocumentReader.read());

        // 根据知识库id，获取向量库的配置

        // 存入向量数据库，这个过程会自动调用embeddingModel,将文本变成向量再存入。
//        vectorStore.add(splitDocuments);

        return null;
    }
}
