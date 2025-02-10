CREATE DATABASE IF NOT EXISTS aiera;
USE aiera;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for aigc_conversation
-- ----------------------------
DROP TABLE IF EXISTS `aigc_conversation`;
CREATE TABLE `aigc_conversation` (
                                     `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
                                     `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户ID',
                                     `prompt_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '提示词ID',
                                     `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标题',
                                     `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='对话窗口表';


-- ----------------------------
-- Table structure for aigc_docs
-- ----------------------------
DROP TABLE IF EXISTS `aigc_docs`;
CREATE TABLE `aigc_docs` (
                             `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
                             `knowledge_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '知识库ID',
                             `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '名称',
                             `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '类型',
                             `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                             `origin` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '来源',
                             `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '内容或链接',
                             `size` int DEFAULT NULL COMMENT '文件大小',
                             `slice_num` int DEFAULT NULL COMMENT '切片数量',
                             `slice_status` tinyint(1) DEFAULT NULL COMMENT '切片状态',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文档表';

-- ----------------------------
-- Records of aigc_docs
-- ----------------------------
BEGIN;
INSERT INTO `aigc_docs` (`id`, `knowledge_id`, `name`, `type`, `url`, `origin`, `content`, `size`, `slice_num`, `slice_status`, `create_time`) VALUES ('51ae6d7356eec12b30dceb7975846c4e', '393704ac13f67fde5da674ddd0742b03', 'story-about-happy-carrot.pdf', 'UPLOAD', NULL, NULL, NULL, 35359, NULL, 0, '2024-08-08 17:02:41');
INSERT INTO `aigc_docs` (`id`, `knowledge_id`, `name`, `type`, `url`, `origin`, `content`, `size`, `slice_num`, `slice_status`, `create_time`) VALUES ('8933fc0e6b449a153adc1789a4e1781c', '393704ac13f67fde5da674ddd0742b03', 'guide1', 'INPUT', NULL, NULL, 'LangChat 是一个基于Java生态的企业AI知识库和大模型应用解决方案，帮助企业快速搭建AI大模型应用。 同时，LangChat也集成了RBAC权限体系，为企业提供开箱即用的AI大模型产品解决方案。\n\nLangChat 使用Java生态，前后端分离，并采用最新的技术栈开发。后端基于SpringBoot3，前端基于Vue3。 LangChat不仅为企业提供AI领域的产品解决方案，也是一个完整的Java企业级应用案例。这个系统带你全面了解SpringBoot3和Vue3的前后端开发流程、业务模块化，以及AI应用集成方案。 无论是企业开发，还是个人学习，LangChat都为你提供丰富的学习案例', NULL, 1, 1, '2024-08-04 18:18:46');
INSERT INTO `aigc_docs` (`id`, `knowledge_id`, `name`, `type`, `url`, `origin`, `content`, `size`, `slice_num`, `slice_status`, `create_time`) VALUES ('ec0c960461a615bb7c7648d7ee5801b5', '393704ac13f67fde5da674ddd0742b03', 'story-about-happy-carrot.pdf', 'UPLOAD', 'http://127.0.0.1/aiera/2024080866b4b069cdb262aeea8da409.pdf', NULL, NULL, 35359, 37, 1, '2024-08-08 19:47:54');
INSERT INTO `aigc_docs` (`id`, `knowledge_id`, `name`, `type`, `url`, `origin`, `content`, `size`, `slice_num`, `slice_status`, `create_time`) VALUES ('f4a465ea6bfc25c34707f1e132356192', '393704ac13f67fde5da674ddd0742b03', 'story-about-happy-carrot.pdf', 'UPLOAD', NULL, NULL, NULL, 35359, NULL, 0, '2024-08-06 22:57:32');
COMMIT;

-- ----------------------------
-- Table structure for aigc_docs_slice
-- ----------------------------
DROP TABLE IF EXISTS `aigc_docs_slice`;
CREATE TABLE `aigc_docs_slice` (
                                   `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
                                   `vector_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '向量库的ID',
                                   `docs_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文档ID',
                                   `knowledge_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '知识库ID',
                                   `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '文档名称',
                                   `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '切片内容',
                                   `word_num` int DEFAULT NULL COMMENT '字符数',
                                   `status` tinyint(1) DEFAULT NULL COMMENT '状态',
                                   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文档切片表';

-- ----------------------------
-- Table structure for aigc_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `aigc_knowledge`;
CREATE TABLE `aigc_knowledge` (
                                  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
                                  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户ID',
                                  `embed_store_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '向量数据库ID',
                                  `embed_model_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '向量模型ID',
                                  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '知识库名称',
                                  `des` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '描述',
                                  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '封面',
                                  `create_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建时间',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='知识库表';

-- ----------------------------
-- Records of aigc_knowledge
-- ----------------------------
BEGIN;
INSERT INTO `aigc_knowledge` (`id`, `user_id`, `embed_store_id`, `embed_model_id`, `name`, `des`, `cover`, `create_time`) VALUES ('393704ac13f67fde5da674ddd0742b03', NULL, '5d57795705faccdf0ea7095a63c5e463', '1f0525bcf8721689f6a81851e5a0068b', 'LangChat文档', 'LangChat官方文档', NULL, '1722766331165');
COMMIT;

-- ----------------------------
-- Table structure for aigc_message
-- ----------------------------
DROP TABLE IF EXISTS `aigc_message`;
CREATE TABLE `aigc_message` (
                                `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
                                `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户ID',
                                `conversation_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '会话ID',
                                `chat_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '消息的ID',
                                `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名',
                                `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'IP地址',
                                `role` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '角色，user和assistant',
                                `model` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '模型名称',
                                `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '消息内容',
                                `tokens` int DEFAULT NULL,
                                `prompt_tokens` int DEFAULT NULL,
                                `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                KEY `conversation_id` (`conversation_id`) USING BTREE,
                                KEY `role` (`role`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='对话消息表';


-- ----------------------------
-- Table structure for aigc_model
-- ----------------------------
DROP TABLE IF EXISTS `aigc_model`;
CREATE TABLE `aigc_model` (
                              `id` varchar(50) NOT NULL COMMENT '主键',
                              `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '类型: CHAT、Embedding、Image',
                              `model` varchar(100) DEFAULT NULL COMMENT '模型名称',
                              `provider` varchar(100) DEFAULT NULL COMMENT '供应商',
                              `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '别名',
                              `response_limit` int DEFAULT NULL COMMENT '响应长度',
                              `temperature` double DEFAULT NULL COMMENT '温度',
                              `top_p` double DEFAULT NULL,
                              `api_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                              `base_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                              `secret_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                              `endpoint` varchar(100) DEFAULT NULL,
                              `azure_deployment_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'azure模型参数',
                              `gemini_project` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'gemini模型参数',
                              `gemini_location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'gemini模型参数',
                              `image_size` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图片大小',
                              `image_quality` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图片质量',
                              `image_style` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图片风格',
                              `dimension` int DEFAULT NULL COMMENT '向量维数',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='LLM模型配置表';

-- ----------------------------
-- Records of aigc_model
-- ----------------------------
BEGIN;
INSERT INTO `aigc_model`(`id`, `type`, `model`, `provider`, `name`, `response_limit`, `temperature`, `top_p`, `api_key`, `base_url`, `secret_key`, `endpoint`, `azure_deployment_name`, `gemini_project`, `gemini_location`, `image_size`, `image_quality`, `image_style`, `dimension`) VALUES ('f5610a6408d2167fabc8ae8b09241a6e', 'CHAT', 'deepseek-r1:8b', 'OLLAMA', 'deepseek-r1:8b', 2000, 0.2, 0.8, 'ollama', 'http://localhost:11434', null, null, null, null, null, null, null, null, null);
COMMIT;

-- ----------------------------
-- Table structure for aigc_embed_store
-- ----------------------------
DROP TABLE IF EXISTS `aigc_embed_store`;
CREATE TABLE `aigc_embed_store` (
                                    `id` varchar(50) NOT NULL COMMENT '主键',
                                    `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '别名',
                                    `provider` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '供应商',
                                    `host` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '地址',
                                    `port` int DEFAULT NULL COMMENT '端口',
                                    `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名',
                                    `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密码',
                                    `database_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '数据库名称',
                                    `table_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '表名称',
                                    `dimension` int DEFAULT NULL COMMENT '向量维数',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Embedding向量数据库配置表';

SET FOREIGN_KEY_CHECKS = 1;
