package cn.hfstorm.aiera.ai.chat.endpoint;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hfstorm.aiera.ai.chat.service.IEmbeddingService;
import cn.hfstorm.aiera.common.ai.domain.AigcDocs;
import cn.hfstorm.aiera.common.core.domain.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * embedding 接口
 *
 * @author : hmy
 * @date : 2025/2/14 9:50
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/embedding")
public class AiEmbeddingEndpoint {
    IEmbeddingService embeddingService;

    @PostMapping("/docs/{knowledgeId}")
//    @SaCheckPermission("aigc:embedding:docs")
    public R docs(@RequestParam MultipartFile file, @PathVariable String knowledgeId) throws IOException {
        return embeddingService.embeddingFile(file, knowledgeId);
    }

    @GetMapping("/re-embed/{docsId}")
    public R reEmbed(@PathVariable String docsId) {

        return R.ok();
    }

    @PostMapping("/search")
    public R search(@RequestBody AigcDocs data) {
//        return R.ok(embeddingService.search(data));
        return R.ok();
    }
}
