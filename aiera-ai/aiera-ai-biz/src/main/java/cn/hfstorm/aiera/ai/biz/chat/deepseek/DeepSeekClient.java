package cn.hfstorm.aiera.ai.biz.chat.deepseek;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.hfstorm.aiera.ai.biz.entity.AigcModel;
import cn.hfstorm.aiera.ai.biz.service.AigcModelService;
import cn.hutool.core.collection.CollectionUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import okhttp3.*;

/**
 * DeepSeek client
 *
 * @author HanLucas
 * @Date 2025/2/8 19:08
 */
@Service
@RequiredArgsConstructor
public class DeepSeekClient {

    @Resource
    private AigcModelService aigcModelService;

    private final OkHttpClient client = new OkHttpClient();

    public static final String MODEL_DEEPSEEK_CHAT = "deepseek-chat";

    public static final String MODEL_DEEPSEEK_R1 = "deepseek-reasoner";

    public String getResponse(String prompt) throws IOException {

        List<AigcModel> chatModels = aigcModelService.getChatModels();
        if (CollectionUtil.isEmpty(chatModels)) {
            return "未找到模型：" + MODEL_DEEPSEEK_CHAT;
        }

        AigcModel aigcModel = chatModels.stream().filter(model -> Objects.equals(model.getName(), MODEL_DEEPSEEK_CHAT))
            .findFirst().orElse(null);

        if (aigcModel == null) {
            return "未找到模型：" + MODEL_DEEPSEEK_CHAT;
        }

        // 构建请求体
        DeepSeekRequest.Message message = DeepSeekRequest.Message.builder().role("user").content(prompt).build();
        DeepSeekRequest requestBody =
            DeepSeekRequest.builder().model(MODEL_DEEPSEEK_CHAT).messages(Collections.singletonList(message)).build();

        // 创建HTTP请求
        Request request = new Request.Builder().url(aigcModel.getBaseUrl())
            .post(RequestBody.create(JSON.toJSONString(requestBody), MediaType.get("application/json")))
            .addHeader("Authorization", "Bearer " + aigcModel.getSecretKey()).build();

        // 发送请求并处理响应
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }
}
