package cn.hfstorm.aiera.ai.chat.service.impl;

import cn.hfstorm.aiera.ai.admin.service.IAigcKnowledgeService;
import cn.hfstorm.aiera.ai.chat.service.IEmbeddingService;
import cn.hfstorm.aiera.ai.provider.knowledge.AiKnowledgeFactory;
import cn.hfstorm.aiera.common.ai.consts.EmbedTypeConstant;
import cn.hfstorm.aiera.common.ai.domain.AigcDocs;
import cn.hfstorm.aiera.common.ai.task.TaskManager;
import cn.hfstorm.aiera.common.core.domain.R;
import cn.hfstorm.aiera.common.satoken.utils.LoginHelper;
import cn.hfstorm.aiera.system.api.RemoteFileService;
import cn.hfstorm.aiera.system.api.domain.SysFile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author : hmy
 * @date : 2025/2/14
 */
@Slf4j
@Service
@AllArgsConstructor
public class EmbeddingServiceImpl implements IEmbeddingService {

    @DubboReference
    private RemoteFileService remoteFileService;

    private AiKnowledgeFactory aiKnowledgeFactory;
    private final IAigcKnowledgeService aigcKnowledgeService;


    @Override
    public R embeddingFile(MultipartFile file, String saveType, String knowledgeId) throws IOException {
        // 判断用户权限
        String userId = String.valueOf(LoginHelper.getUserId());

        // 上传到oss
        SysFile oss = remoteFileService.upload(file, saveType);
        // oss结果信息保存到数据库
        AigcDocs data = new AigcDocs()
                .setName(oss.getName())
                .setSliceStatus(false)
                .setUrl(oss.getUrl())
                .setSize(file.getSize())
                .setType(EmbedTypeConstant.ORIGIN_TYPE_UPLOAD)
                .setKnowledgeId(knowledgeId);
        aigcKnowledgeService.addDocs(data);
        // 进行分片存储到向量数据库
//        TaskManager.submitTask(userId, Executors.callable(() -> {
//            embeddingService.embedDocsSlice(data, oss.getUrl());
//        }));
        // 从IO流中读取文件
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(new InputStreamResource(file.getInputStream()));
        // 将文本内容划分成更小的块
        List<Document> splitDocuments = new TokenTextSplitter()
                .apply(tikaDocumentReader.read());

        // 根据知识库id，获取向量库的配置
        EmbeddingModel embeddingModel = aiKnowledgeFactory.getEmbeddingModel(knowledgeId);
        // 存入向量数据库，这个过程会自动调用embeddingModel,将文本变成向量再存入。
        VectorStore vectorStore = aiKnowledgeFactory.getVectorStore(knowledgeId, embeddingModel);
        vectorStore.add(splitDocuments);

        return R.ok();
    }
}
