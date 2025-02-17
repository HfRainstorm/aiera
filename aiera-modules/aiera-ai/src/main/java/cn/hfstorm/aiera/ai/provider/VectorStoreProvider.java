//package cn.hfstorm.aiera.ai.provider;
//
//import lombok.AllArgsConstructor;
//import org.springframework.ai.vectorstore.VectorStore;
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * 注册向量数据库
// *
// * @author : hmy
// * @date : 2025/2/14
// */
//@Component
//@AllArgsConstructor
//public class VectorStoreProvider  implements ApplicationContextAware {
//
//    List<VectorStore> vectorStores;
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        for (VectorStore vectorStore : vectorStores) {
//            System.out.println(vectorStore);
//        }
//    }
//
//
//
//    public List<VectorStore> getVectorStoreList() {
//        return vectorStores;
//    }
//
//}
