package cn.hfstorm.aiera.ai.provider;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientProviderTest {


    @Autowired
    private RedisVectorStore vectorStore;


    @Test
    public void testEmbedding() {

        var ollamaApi = new OllamaApi();

        var embeddingModel = new OllamaEmbeddingModel(ollamaApi,
                OllamaOptions.builder()
                        .model(OllamaModel.MISTRAL.id())
                        .build(), null, null);

        EmbeddingResponse embeddingResponse =embeddingModel.call(
                new EmbeddingRequest(List.of("Hello World", "World is big and salvation is near"),
                        OllamaOptions.builder()
                                .model("qwen2.5:latest")
                        .truncate(false)
                        .build()));


        System.out.println(embeddingResponse);
    }

    @Test
    public void t1() {


        List<Document> documents = List.of(
                new Document("1. 使用SpringAIAlibaba创建一个Spring Boot项目，并添加spring-ai-alibaba-starter依赖。"),
                new Document("2. 在SpringAIAlibaba项目的pom.xml中添加Spring Milestone和Snapshot存储库。"),
                new Document("3. 通过SpringAIAlibaba申请阿里云通义API Key，在application.yml中进行配置。"),
                new Document("4. 使用SpringAIAlibaba的ChatClient和Prompt功能实现对话模型。"),
                new Document("5. 通过SpringAIAlibaba的Spring Boot与Spring Cloud Alibaba AI对接，实现基本聊天功能。"),
                new Document("6. SpringAIAlibaba支持文本生成、翻译、摘要等生成式AI功能。"),
                new Document("7. SpringAIAlibaba支持文本数据的语义搜索和AI绘画功能。"),
                new Document("8. 使用SpringAIAlibaba的TongYiChatModel和TongYiImagesModel实现聊天和图片服务。"),
                new Document("9. 在SpringAIAlibaba的REST控制器中提供对外的API接口。"),
                new Document("10. 通过SpringAIAlibaba的简单API调用实现AI模型的集成。"),
                new Document("11. 使用SpringAIAlibaba的Prompt模板管理控制AI模型的输出。"),
                new Document("12. 结合SpringAIAlibaba的检索和生成技术（RAG）提高生成内容的质量。"),
                new Document("13. 使用SpringAIAlibaba实现文本生成图像和图像识别功能。"),
                new Document("14. 准备SpringAIAlibaba需要的Java 17及以上的开发环境。"),
                new Document("15. 使用IDEA进行SpringAIAlibaba的Java开发和HBuilder X进行前端开发。"),
                new Document("16. 在SpringAIAlibaba的Spring Boot项目中集成多种AI模型和向量数据库。"),
                new Document("17. SpringAIAlibaba支持自然语言处理、计算机视觉、语音处理和数据分析与预测功能。"),
                new Document("18. 通过SpringAIAlibaba的配置中心和注册中心实现动态扩展。")
        );

         vectorStore.add(documents);



// Retrieve documents similar to a query
        List<Document> results = this.vectorStore.similaritySearch(SearchRequest.builder().query("Spring").topK(5).build());

        System.out.println(results);
    }
}