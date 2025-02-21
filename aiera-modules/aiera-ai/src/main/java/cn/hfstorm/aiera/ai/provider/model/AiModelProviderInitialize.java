//package cn.hfstorm.aiera.ai.provider.model;
//
//
//import cn.hfstorm.aiera.ai.biz.service.impl.AigcModelService;
//import cn.hfstorm.aiera.ai.holder.SpringContextHolder;
//import cn.hfstorm.aiera.ai.provider.embedmodel.build.EmbedModelBuildHandler;
//import cn.hfstorm.aiera.ai.provider.model.build.ModelBuildHandler;
//import cn.hfstorm.aiera.common.ai.domain.AigcModel;
//import cn.hfstorm.aiera.common.ai.enums.ModelTypeEnum;
//import cn.hutool.core.util.ObjectUtil;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.ai.chat.model.ChatModel;
//import org.springframework.ai.embedding.EmbeddingModel;
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.Async;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//
///**
// * model provider initialize
// *
// * @author : hmy
// * @date : 2025/2/8 10:33
// */
//@Slf4j
//@AllArgsConstructor
//@Configuration
//public class AiModelProviderInitialize implements ApplicationContextAware {
//
//    private ModelProviderFactory modelProviderFactory;
//    @Override
//    public void setApplicationContext(ApplicationContext context) throws BeansException {
//        modelProviderFactory.init();
//    }
//
//
//
//}
//
