CREATE DATABASE IF NOT EXISTS nacos;
use nacos;
/******************************************/
/*   表名称 = config_info                  */
/******************************************/
CREATE TABLE `config_info`
(
    `id`                 bigint(20)    NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`            varchar(255)  NOT NULL COMMENT 'data_id',
    `group_id`           varchar(128)           DEFAULT NULL COMMENT 'group_id',
    `content`            longtext      NOT NULL COMMENT 'content',
    `md5`                varchar(32)            DEFAULT NULL COMMENT 'md5',
    `gmt_create`         datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`       datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`           text COMMENT 'source user',
    `src_ip`             varchar(50)            DEFAULT NULL COMMENT 'source ip',
    `app_name`           varchar(128)           DEFAULT NULL COMMENT 'app_name',
    `tenant_id`          varchar(128)           DEFAULT '' COMMENT '租户字段',
    `c_desc`             varchar(256)           DEFAULT NULL COMMENT 'configuration description',
    `c_use`              varchar(64)            DEFAULT NULL COMMENT 'configuration usage',
    `effect`             varchar(64)            DEFAULT NULL COMMENT '配置生效的描述',
    `type`               varchar(64)            DEFAULT NULL COMMENT '配置的类型',
    `c_schema`           text COMMENT '配置的模式',
    `encrypted_data_key` varchar(1024) NOT NULL DEFAULT '' COMMENT '密钥',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`, `group_id`, `tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='config_info';


-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (1, 'application-common.yml', 'DEFAULT_GROUP', 'dubbo:\n  application:\n    # 关闭qos端口避免单机多生产者端口冲突 如需使用自行开启\n    qos-enable: false\n  protocol:\n    # 如需使用 Triple 3.0 新协议 可查看官方文档\n    # 使用 dubbo 协议通信\n    name: dubbo\n    # dubbo 协议端口(-1表示自增端口,从20880开始)\n    port: -1\n    # 指定dubbo协议注册ip\n    # host: 192.168.0.100\n  # 消费者相关配置\n  consumer:\n    # 超时时间\n    timeout: 3000\n  scan:\n    # 接口实现类扫描\n    base-packages: cn.hfstorm.aiera.**.dubbo\n  # 自定义配置\n  custom:\n    # 全局请求log\n    request-log: true\n    # info 基础信息 param 参数信息 full 全部\n    log-level: info\n\nspring:\n  # 资源信息\n  messages:\n    # 国际化资源文件路径\n    basename: i18n/messages\n  servlet:\n    multipart:\n      # 整个请求大小限制\n      max-request-size: 20MB\n      # 上传单个文件大小限制\n      max-file-size: 10MB\n  mvc:\n    format:\n      date-time: yyyy-MM-dd HH:mm:ss\n  #jackson配置\n  jackson:\n    # 日期格式化\n    date-format: yyyy-MM-dd HH:mm:ss\n    serialization:\n      # 格式化输出\n      INDENT_OUTPUT: false\n      # 忽略无法转换的对象\n      fail_on_empty_beans: false\n    deserialization:\n      # 允许对象忽略json中不存在的属性\n      fail_on_unknown_properties: false\n  cloud:\n    # sentinel 配置\n    sentinel:\n      # sentinel 开关\n      enabled: true\n      # 取消控制台懒加载\n      eager: true\n      transport:\n        # dashboard控制台服务名 用于服务发现\n        # 如无此配置将默认使用下方 dashboard 配置直接注册\n        server-name: aiera-sentinel-dashboard\n        # 客户端指定注册的ip 用于多网卡ip不稳点使用\n        # client-ip:\n        # 控制台地址 从1.3.0开始使用 server-name 注册\n        # dashboard: localhost:8718\n\n  # redis通用配置 子服务可以自行配置进行覆盖\n  data:\n    redis:\n      host: localhost\n      port: 6379\n      # 密码(如没有密码请注释掉)\n      password: 123456\n      database: 0\n      timeout: 10s\n      ssl.enabled: false\n\n# redisson 配置\nredisson:\n  # redis key前缀\n  keyPrefix:\n  # 线程池数量\n  threads: 4\n  # Netty线程池数量\n  nettyThreads: 8\n  # 单节点配置\n  singleServerConfig:\n    # 客户端名称\n    clientName: ${spring.application.name}\n    # 最小空闲连接数\n    connectionMinimumIdleSize: 8\n    # 连接池大小\n    connectionPoolSize: 32\n    # 连接空闲超时，单位：毫秒\n    idleConnectionTimeout: 10000\n    # 命令等待超时，单位：毫秒\n    timeout: 3000\n    # 发布和订阅连接池大小\n    subscriptionConnectionPoolSize: 50\n\n# 分布式锁 lock4j 全局配置\nlock4j:\n  # 获取分布式锁超时时间，默认为 3000 毫秒\n  acquire-timeout: 3000\n  # 分布式锁的超时时间，默认为 30 秒\n  expire: 30000\n\n# 暴露监控端点\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include:  \'*\'\n  endpoint:\n    health:\n      show-details: ALWAYS\n    logfile:\n      external-file: ./logs/${spring.application.name}/console.log\n\n# 日志配置\nlogging:\n  level:\n    org.springframework: warn\n    org.apache.dubbo: warn\n    com.alibaba.nacos: warn\n  config: classpath:logback-plus.xml\n\n# Sa-Token配置\nsa-token:\n  # token名称 (同时也是cookie名称)\n  token-name: Authorization\n  # token 有效期（单位：秒） 默认30天2592000秒，-1 代表永久有效\n  timeout: 86400\n  # token 最低活跃频率（单位：秒），如果 token 超过此时间没有访问系统就会被冻结，默认-1 代表不限制，永不冻结\n  active-timeout: -1\n  # 开启内网服务调用鉴权(不允许越过gateway访问内网服务 保障服务安全)\n  check-same-token: true\n  # 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)\n  is-concurrent: true\n  # 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)\n  is-share: false\n  # jwt秘钥\n  jwt-secret-key: abcdefghijklmnopqrstuvwxyz\n\n# api接口加密\napi-decrypt:\n  # 是否开启全局接口加密\n  enabled: false\n  # AES 加密头标识\n  headerFlag: encrypt-key\n  # 响应加密公钥 非对称算法的公私钥 如：SM2，RSA 使用者请自行更换\n  # 对应前端解密私钥 MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAmc3CuPiGL/LcIIm7zryCEIbl1SPzBkr75E2VMtxegyZ1lYRD+7TZGAPkvIsBcaMs6Nsy0L78n2qh+lIZMpLH8wIDAQABAkEAk82Mhz0tlv6IVCyIcw/s3f0E+WLmtPFyR9/WtV3Y5aaejUkU60JpX4m5xNR2VaqOLTZAYjW8Wy0aXr3zYIhhQQIhAMfqR9oFdYw1J9SsNc+CrhugAvKTi0+BF6VoL6psWhvbAiEAxPPNTmrkmrXwdm/pQQu3UOQmc2vCZ5tiKpW10CgJi8kCIFGkL6utxw93Ncj4exE/gPLvKcT+1Emnoox+O9kRXss5AiAMtYLJDaLEzPrAWcZeeSgSIzbL+ecokmFKSDDcRske6QIgSMkHedwND1olF8vlKsJUGK3BcdtM8w4Xq7BpSBwsloE=\n  publicKey: MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJnNwrj4hi/y3CCJu868ghCG5dUj8wZK++RNlTLcXoMmdZWEQ/u02RgD5LyLAXGjLOjbMtC+/J9qofpSGTKSx/MCAwEAAQ==\n  # 请求解密私钥 非对称算法的公私钥 如：SM2，RSA 使用者请自行更换\n  # 对应前端加密公钥 MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKoR8mX0rGKLqzcWmOzbfj64K8ZIgOdHnzkXSOVOZbFu/TJhZ7rFAN+eaGkl3C4buccQd/EjEsj9ir7ijT7h96MCAwEAAQ==\n  privateKey: MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAqhHyZfSsYourNxaY7Nt+PrgrxkiA50efORdI5U5lsW79MmFnusUA355oaSXcLhu5xxB38SMSyP2KvuKNPuH3owIDAQABAkAfoiLyL+Z4lf4Myxk6xUDgLaWGximj20CUf+5BKKnlrK+Ed8gAkM0HqoTt2UZwA5E2MzS4EI2gjfQhz5X28uqxAiEA3wNFxfrCZlSZHb0gn2zDpWowcSxQAgiCstxGUoOqlW8CIQDDOerGKH5OmCJ4Z21v+F25WaHYPxCFMvwxpcw99EcvDQIgIdhDTIqD2jfYjPTY8Jj3EDGPbH2HHuffvflECt3Ek60CIQCFRlCkHpi7hthhYhovyloRYsM+IS9h/0BzlEAuO0ktMQIgSPT3aFAgJYwKpqRYKlLDVcflZFCKY7u3UP8iWi1Qw0Y=\n\n\n# 接口文档配置\nspringdoc:\n  api-docs:\n    # 是否开启接口文档\n    enabled: true\n  swagger-ui:\n    version: 5.2.0\n#    # 持久化认证数据\n#    persistAuthorization: true\n  info:\n    # 标题\n    title: \'标题：AI ERA 微服务权限管理系统_接口文档\'\n    # 描述\n    description: \'\'\n    # 版本\n    version: \'版本号：系统版本...\'\n    # 作者信息\n    contact:\n      name: hmy\n      email: hmy_hammer@163.com\n      url: https://github.com/HfRainstorm/aiera\n  components:\n    # 鉴权方式配置\n    security-schemes:\n      apiKey:\n        type: APIKEY\n        in: HEADER\n        name: ${sa-token.token-name}\n\n# seata配置\nseata:\n  # 是否启用\n  enabled: false\n  # Seata 应用编号，默认为应用名\n  application-id: ${spring.application.name}\n  # Seata 事务组编号，用于 TC 集群名\n  tx-service-group: ${spring.application.name}-group\n', '516fd63439dd6b3540d68683aa18bfbb', '2022-01-09 15:18:55', '2024-05-10 06:17:53', 'nacos', '0:0:0:0:0:0:0:1', '', 'dev', '通用配置基础配置', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (1, 'application-common.yml', 'DEFAULT_GROUP', 'dubbo:
  application:
    # 关闭qos端口避免单机多生产者端口冲突 如需使用自行开启
    qos-enable: false
  protocol:
    # 如需使用 Triple 3.0 新协议 可查看官方文档
    # 使用 dubbo 协议通信
    name: dubbo
    # dubbo 协议端口(-1表示自增端口,从20880开始)
    port: -1
    # 指定dubbo协议注册ip
    # host: 10.129.30.139
  # 消费者相关配置
  consumer:
    # 超时时间
    timeout: 3000
  scan:
    # 接口实现类扫描
    base-packages: cn.hfstorm.aiera.**.dubbo
  # 自定义配置
  custom:
    # 全局请求log
    request-log: true
    # info 基础信息 param 参数信息 full 全部
    log-level: info
  registry:
    address: nacos://localhost:8848?username=nacos&password=nacos2025

spring:
  # 资源信息
  messages:
    # 国际化资源文件路径
    basename: i18n/messages
  servlet:
    multipart:
      # 整个请求大小限制
      max-request-size: 20MB
      # 上传单个文件大小限制
      max-file-size: 10MB
  mvc:
    format:
      date-time: yyyy-MM-dd HH:mm:ss
  #jackson配置
  jackson:
    # 日期格式化
    date-format: yyyy-MM-dd HH:mm:ss
    serialization:
      # 格式化输出
      INDENT_OUTPUT: false
      # 忽略无法转换的对象
      fail_on_empty_beans: false
    deserialization:
      # 允许对象忽略json中不存在的属性
      fail_on_unknown_properties: false
  cloud:
    # sentinel 配置
    sentinel:
      # sentinel 开关
      enabled: true
      # 取消控制台懒加载
      eager: true
      transport:
        # dashboard控制台服务名 用于服务发现
        # 如无此配置将默认使用下方 dashboard 配置直接注册
        server-name: aiera-sentinel-dashboard
        # 客户端指定注册的ip 用于多网卡ip不稳点使用
        # client-ip:
        # 控制台地址 从1.3.0开始使用 server-name 注册
        # dashboard: localhost:8718

  # redis通用配置 子服务可以自行配置进行覆盖
  data:
    redis:
      host: localhost
      port: 6379
      # 密码(如没有密码请注释掉)
      password: 123456
      database: 0
      timeout: 10s
      ssl.enabled: false

# redisson 配置
redisson:
  # redis key前缀
  keyPrefix:
  # 线程池数量
  threads: 4
  # Netty线程池数量
  nettyThreads: 8
  # 单节点配置
  singleServerConfig:
    # 客户端名称
    clientName: ${spring.application.name}
    # 最小空闲连接数
    connectionMinimumIdleSize: 8
    # 连接池大小
    connectionPoolSize: 32
    # 连接空闲超时，单位：毫秒
    idleConnectionTimeout: 10000
    # 命令等待超时，单位：毫秒
    timeout: 3000
    # 发布和订阅连接池大小
    subscriptionConnectionPoolSize: 50

# 分布式锁 lock4j 全局配置
lock4j:
  # 获取分布式锁超时时间，默认为 3000 毫秒
  acquire-timeout: 3000
  # 分布式锁的超时时间，默认为 30 秒
  expire: 30000

# 暴露监控端点
management:
  endpoints:
    web:
      exposure:
        include:  \'*\'
  endpoint:
    health:
      show-details: ALWAYS
    logfile:
      external-file: ./logs/${spring.application.name}/console.log

# 日志配置
logging:
  level:
    org.springframework: warn
    org.apache.dubbo: warn
    com.alibaba.nacos: warn
  config: classpath:logback-plus.xml

# Sa-Token配置
sa-token:
  # token名称 (同时也是cookie名称)
  token-name: Authorization
  # token 有效期（单位：秒） 默认30天2592000秒，-1 代表永久有效
  timeout: 86400
  # token 最低活跃频率（单位：秒），如果 token 超过此时间没有访问系统就会被冻结，默认-1 代表不限制，永不冻结
  active-timeout: -1
  # 开启内网服务调用鉴权(不允许越过gateway访问内网服务 保障服务安全)
  check-same-token: true
  # 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)
  is-concurrent: true
  # 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)
  is-share: false
  # jwt秘钥
  jwt-secret-key: abcdefghijklmnopqrstuvwxyz

# api接口加密
api-decrypt:
  # 是否开启全局接口加密
  enabled: false
  # AES 加密头标识
  headerFlag: encrypt-key
  # 响应加密公钥 非对称算法的公私钥 如：SM2，RSA 使用者请自行更换
  # 对应前端解密私钥 MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAmc3CuPiGL/LcIIm7zryCEIbl1SPzBkr75E2VMtxegyZ1lYRD+7TZGAPkvIsBcaMs6Nsy0L78n2qh+lIZMpLH8wIDAQABAkEAk82Mhz0tlv6IVCyIcw/s3f0E+WLmtPFyR9/WtV3Y5aaejUkU60JpX4m5xNR2VaqOLTZAYjW8Wy0aXr3zYIhhQQIhAMfqR9oFdYw1J9SsNc+CrhugAvKTi0+BF6VoL6psWhvbAiEAxPPNTmrkmrXwdm/pQQu3UOQmc2vCZ5tiKpW10CgJi8kCIFGkL6utxw93Ncj4exE/gPLvKcT+1Emnoox+O9kRXss5AiAMtYLJDaLEzPrAWcZeeSgSIzbL+ecokmFKSDDcRske6QIgSMkHedwND1olF8vlKsJUGK3BcdtM8w4Xq7BpSBwsloE=
  publicKey: MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJnNwrj4hi/y3CCJu868ghCG5dUj8wZK++RNlTLcXoMmdZWEQ/u02RgD5LyLAXGjLOjbMtC+/J9qofpSGTKSx/MCAwEAAQ==
  # 请求解密私钥 非对称算法的公私钥 如：SM2，RSA 使用者请自行更换
  # 对应前端加密公钥 MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKoR8mX0rGKLqzcWmOzbfj64K8ZIgOdHnzkXSOVOZbFu/TJhZ7rFAN+eaGkl3C4buccQd/EjEsj9ir7ijT7h96MCAwEAAQ==
  privateKey: MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAqhHyZfSsYourNxaY7Nt+PrgrxkiA50efORdI5U5lsW79MmFnusUA355oaSXcLhu5xxB38SMSyP2KvuKNPuH3owIDAQABAkAfoiLyL+Z4lf4Myxk6xUDgLaWGximj20CUf+5BKKnlrK+Ed8gAkM0HqoTt2UZwA5E2MzS4EI2gjfQhz5X28uqxAiEA3wNFxfrCZlSZHb0gn2zDpWowcSxQAgiCstxGUoOqlW8CIQDDOerGKH5OmCJ4Z21v+F25WaHYPxCFMvwxpcw99EcvDQIgIdhDTIqD2jfYjPTY8Jj3EDGPbH2HHuffvflECt3Ek60CIQCFRlCkHpi7hthhYhovyloRYsM+IS9h/0BzlEAuO0ktMQIgSPT3aFAgJYwKpqRYKlLDVcflZFCKY7u3UP8iWi1Qw0Y=


# 接口文档配置
springdoc:
  api-docs:
    # 是否开启接口文档
    enabled: true
  swagger-ui:
    version: 5.2.0
#    # 持久化认证数据
#    persistAuthorization: true
  info:
    # 标题
    title: \'标题：AI ERA 微服务权限管理系统_接口文档\'
    # 描述
    description: \'\'
    # 版本
    version: \'版本号：系统版本...\'
    # 作者信息
    contact:
      name: hmy
      email: hmy_hammer@163.com
      url: https://github.com/HfRainstorm/aiera
  components:
    # 鉴权方式配置
    security-schemes:
      apiKey:
        type: APIKEY
        in: HEADER
        name: ${sa-token.token-name}

# seata配置
seata:
  # 是否启用
  enabled: false
  # Seata 应用编号，默认为应用名
  application-id: ${spring.application.name}
  # Seata 事务组编号，用于 TC 集群名
  tx-service-group: ${spring.application.name}-group
', '785f6d204dedb825848bdd95fceb2fae', '2022-01-09 15:18:55', '2025-04-03 16:15:04', 'nacos', '172.19.0.1', '', 'dev', '通用配置基础配置', '', '', 'yaml', '', '');

INSERT INTO `config_info` VALUES (2, 'datasource.yml', 'DEFAULT_GROUP', 'datasource:\n  system-master:\n    # jdbc 所有参数配置参考 https://lionli.blog.csdn.net/article/details/122018562\n    # rewriteBatchedStatements=true 批处理优化 大幅提升批量插入更新删除性能\n    url: jdbc:mysql://localhost:3306/aiera?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true\n    username: root\n    password: root\n  job:\n    url: jdbc:mysql://localhost:3306/xxl-job?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true\n    username: root\n    password: root\n#  system-oracle:\n#    url: jdbc:oracle:thin:@//localhost:1521/XE\n#    username: ROOT\n#    password: password\n#  system-postgres:\n#    url: jdbc:postgresql://localhost:5432/postgres?useUnicode=true&characterEncoding=utf8&useSSL=true&autoReconnect=true&reWriteBatchedInserts=true\n#    username: root\n#    password: password\n\nspring:\n  datasource:\n    type: com.zaxxer.hikari.HikariDataSource\n    # 动态数据源文档 https://www.kancloud.cn/tracy5546/dynamic-datasource/content\n    dynamic:\n      # 性能分析插件(有性能损耗 不建议生产环境使用)\n      p6spy: true\n      # 开启seata代理，开启后默认每个数据源都代理，如果某个不需要代理可单独关闭\n      seata: ${seata.enabled}\n      # 严格模式 匹配不到数据源则报错\n      strict: true\n      hikari:\n        # 最大连接池数量\n        maxPoolSize: 20\n        # 最小空闲线程数量\n        minIdle: 10\n        # 配置获取连接等待超时的时间\n        connectionTimeout: 30000\n        # 校验超时时间\n        validationTimeout: 5000\n        # 空闲连接存活最大时间，默认10分钟\n        idleTimeout: 600000\n        # 此属性控制池中连接的最长生命周期，值0表示无限生命周期，默认30分钟\n        maxLifetime: 1800000\n        # 多久检查一次连接的活性\n        keepaliveTime: 30000\n\n\n# MyBatisPlus配置\n# https://baomidou.com/config/\nmybatis-plus:\n  # 不支持多包, 如有需要可在注解配置 或 提升扫包等级\n  # 例如 com.**.**.mapper\n  mapperPackage: cn.hfstorm.aiera.**.mapper\n  # 对应的 XML 文件位置\n  mapperLocations: classpath*:mapper/**/*Mapper.xml\n  # 实体扫描，多个package用逗号或者分号分隔\n  typeAliasesPackage: cn.hfstorm.aiera.**.domain\n  global-config:\n    dbConfig:\n      # 主键类型\n      # AUTO 自增 NONE 空 INPUT 用户输入 ASSIGN_ID 雪花 ASSIGN_UUID 唯一 UUID\n      # 如需改为自增 需要将数据库表全部设置为自增\n      idType: ASSIGN_ID', '295ea10158d07af511281c0866aaa776', '2022-01-09 15:19:07', '2024-05-08 06:49:20', 'nacos', '0:0:0:0:0:0:0:1', '', 'dev', '数据源配置', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (3, 'aiera-gateway.yml', 'DEFAULT_GROUP', '# 安全配置\nsecurity:\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/code\n      - /auth/logout\n      - /auth/login\n      - /auth/binding/*\n      - /auth/register\n      - /resource/sms/code\n      - /*/v3/api-docs\n      - /*/error\n      - /csrf\n\nspring:\n  cloud:\n    # 网关配置\n    gateway:\n      # 打印请求日志(自定义)\n      requestLog: true\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:\n        # 认证中心\n        - id: aiera-auth\n          uri: lb://aiera-auth\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        # 系统模块\n        - id: aiera-system\n          uri: lb://aiera-system\n          predicates:\n            - Path=/system/**,/monitor/**\n          filters:\n            - StripPrefix=1\n        # 资源服务\n        - id: aiera-file\n          uri: lb://aiera-file\n          predicates:\n            - Path=/file/**\n          filters:\n            - StripPrefix=1\n\n\n    # sentinel 配置\n    sentinel:\n      filter:\n        enabled: false\n      # nacos配置持久化\n      datasource:\n        ds1:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            dataId: sentinel-${spring.application.name}.json\n            groupId: ${spring.cloud.nacos.config.group}\n            namespace: ${spring.profiles.active}\n            data-type: json\n            rule-type: gw-flow\n', 'b43b96d002a9f5d12cb745a7699ad764', '2022-01-09 15:19:43', '2024-05-08 08:04:51', 'nacos', '0:0:0:0:0:0:0:1', '', 'dev', '网关模块', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (4, 'aiera-auth.yml', 'DEFAULT_GROUP', '# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    # 是否开启验证码\n    enabled: false\n    # 验证码类型 MATH 数组计算 CHAR 字符验证\n    type: MATH\n    # line 线段干扰 circle 圆圈干扰 shear 扭曲干扰\n    category: CIRCLE\n    # 数字验证码位数\n    numberLength: 1\n    # 字符验证码长度\n    charLength: 4\n\n# 用户配置\nuser:\n  password:\n    # 密码最大错误次数\n    maxRetryCount: 500\n    # 密码锁定时间（默认10分钟）\n    lockTime: 10\n', '493cac677549e7afacd8da0cf03e81c0', '2022-01-09 15:19:43', '2024-05-10 03:07:03', 'nacos', '0:0:0:0:0:0:0:1', '', 'dev', '认证中心', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (5, 'aiera-monitor.yml', 'DEFAULT_GROUP', '# 监控中心配置\nspring:\n  security:\n    user:\n      name: aiera\n      password: 123456\n  boot:\n    admin:\n      ui:\n        title: aiera-Cloud服务监控中心\n      discovery:\n        # seata 不具有健康检查的能力 防止报错排除掉\n        ignored-services: \n', '6840d6585e13553b8c60ffe3471c31f0', '2022-01-09 15:20:18', '2024-05-08 07:56:53', 'nacos', '0:0:0:0:0:0:0:1', '', 'dev', '监控中心', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (6, 'aiera-system.yml', 'DEFAULT_GROUP', 'spring:\n  datasource:\n    dynamic:\n      # 设置默认的数据源或者数据源组,默认值即为 master\n      primary: master\n      datasource:\n        # 主库数据源\n        master:\n          type: ${spring.datasource.type}\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: ${datasource.system-master.url}\n          username: ${datasource.system-master.username}\n          password: ${datasource.system-master.password}\n#        oracle:\n#          type: ${spring.datasource.type}\n#          driverClassName: oracle.jdbc.OracleDriver\n#          url: ${datasource.system-oracle.url}\n#          username: ${datasource.system-oracle.username}\n#          password: ${datasource.system-oracle.password}\n#        postgres:\n#          type: ${spring.datasource.type}\n#          driverClassName: org.postgresql.Driver\n#          url: ${datasource.system-postgres.url}\n#          username: ${datasource.system-postgres.username}\n#          password: ${datasource.system-postgres.password}\n\n\n# elasticsearch 功能配置\n# 文档地址: https://www.easy-es.cn/\n# 更改包名需要去 EasyEsConfiguration 修改包扫描(后续版本支持配置文件读取)\neasy-es:\n  # 是否开启EE自动配置\n  enable: false\n  # es连接地址+端口 格式必须为ip:port,如果是集群则可用逗号隔开\n  address : localhost:9200\n  # 默认为http\n  schema: http\n  # 注意ES建议使用账号认证 不使用会报警告日志\n  #如果无账号密码则可不配置此行\n  #username:\n  #如果无账号密码则可不配置此行\n  #password:\n  # 心跳策略时间 单位:ms\n  keep-alive-millis: 18000\n  # 连接超时时间 单位:ms\n  connectTimeout: 5000\n  # 通信超时时间 单位:ms\n  socketTimeout: 5000\n  # 连接请求超时时间 单位:ms\n  connectionRequestTimeout: 5000\n  # 最大连接数 单位:个\n  maxConnTotal: 100\n  # 最大连接路由数 单位:个\n  maxConnPerRoute: 100\n  global-config:\n    # 开启控制台打印通过本框架生成的DSL语句,默认为开启,测试稳定后的生产环境建议关闭,以提升少量性能\n    print-dsl: true\n    # 异步处理索引是否阻塞主线程 默认阻塞 数据量过大时调整为非阻塞异步进行 项目启动更快\n    asyncProcessIndexBlocking: true\n    db-config:\n      # 是否开启下划线转驼峰 默认为false\n      map-underscore-to-camel-case: true\n      # id生成策略 customize为自定义,id值由用户生成,比如取MySQL中的数据id,如缺省此项配置,则id默认策略为es自动生成\n      id-type: customize\n      # 字段更新策略 默认为not_null\n      field-strategy: not_null\n      # 默认开启,查询若指定了size超过1w条时也会自动开启,开启后查询所有匹配数据,若不开启,会导致无法获取数据总条数,其它功能不受影响.\n      enable-track-total-hits: true\n      # 数据刷新策略,默认为不刷新\n      refresh-policy: immediate\n\n\nxxl:\n  job:\n    # 执行器开关\n    enabled: false\n    # 调度中心地址：如调度中心集群部署存在多个地址则用逗号分隔。\n    # admin-addresses: http://localhost:9900\n    # 调度中心应用名 通过服务名连接调度中心(启用admin-appname会导致admin-addresses不生效)\n    admin-appname: xxl-job-admin\n    # 执行器通讯TOKEN：非空时启用\n    access-token: xxl-job\n    # 执行器配置\n    executor:\n      # 执行器AppName：执行器心跳注册分组依据；为空则关闭自动注册\n      appname: ${spring.application.name}-executor\n      # 29203 端口 随着主应用端口飘逸 避免集群冲突\n      port: 9901\n      # 执行器注册：默认IP:PORT\n      address:\n      # 执行器IP：默认自动获取IP\n      ip:\n      # 执行器运行日志文件存储磁盘路径\n      logpath: ./logs/${spring.application.name}/xxl-job\n      # 执行器日志文件保存天数：大于3生效\n      logretentiondays: 30\n', '51a28997df0ce414b84d6d9fbd7ec3de', '2022-01-09 15:20:18', '2024-05-08 07:53:00', 'nacos', '0:0:0:0:0:0:0:1', '', 'dev', '系统模块', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (157, 'xxl-job-admin.yml', 'DEFAULT_GROUP', '# server 配置\r\nspring:\r\n  datasource:\r\n    type: com.zaxxer.hikari.HikariDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: ${datasource.job.url}\r\n    username: ${datasource.job.username}\r\n    password: ${datasource.job.password}\r\n    hikari:\r\n      auto-commit: true\r\n      connection-test-query: SELECT 1\r\n      connection-timeout: 10000\r\n      idle-timeout: 30000\r\n      max-lifetime: 900000\r\n      maximum-pool-size: 30\r\n      minimum-idle: 10\r\n      pool-name: HikariCP\r\n      validation-timeout: 1000\r\n  mail:\r\n    from: xxx@qq.com\r\n    host: smtp.qq.com\r\n    username: xxx@qq.com\r\n    password: xxx\r\n    port: 25\r\n    properties:\r\n      mail:\r\n        smtp:\r\n          auth: true\r\n          socketFactory:\r\n            class: javax.net.ssl.SSLSocketFactory\r\n          starttls:\r\n            enable: true\r\n            required: true\r\n\r\n# mybatis 配置\r\nmybatis:\r\n  mapper-locations: classpath:/mybatis-mapper/*Mapper.xml\r\n\r\n# Actuator 监控端点的配置项\r\nmanagement:\r\n  health:\r\n    mail:\r\n      enabled: false\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'\r\n  endpoint:\r\n    health:\r\n      show-details: ALWAYS\r\n    logfile:\r\n      external-file: ./logs/${spring.application.name}/console.log\r\n\r\n# xxljob系统配置\r\nxxl:\r\n  job:\r\n    # 鉴权token\r\n    accessToken: xxl-job\r\n    # 国际化\r\n    i18n: zh_CN\r\n    # 日志清理\r\n    logretentiondays: 30\r\n    triggerpool:\r\n      fast:\r\n        max: 200\r\n      slow:\r\n        max: 100\r\n', '553e2a8c91fa07f9015c823761fe34ea', '2024-05-08 06:48:49', '2024-05-08 06:48:49', 'nacos', '0:0:0:0:0:0:0:1', '', 'dev', NULL, NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (158, 'aiera-ai.yml', 'DEFAULT_GROUP', 'spring:\n  ai:\n    vectorstore:\n      milvus:\n        client:\n          host: \"localhost\"\n          port: 19530\n          username: \"root\"\n          password: \"milvus\"\n        databaseName: \"aiera\"\n        collectionName: \"vector_store\"\n        embeddingDimension: 1536', '279213ce335b3533313a07ea71228921', '2025-02-16 17:41:33', '2025-02-16 17:43:29', NULL, '0:0:0:0:0:0:0:1', '', 'dev', 'Spring AI配置', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (159, 'aiera-file.yml', 'DEFAULT_GROUP', '# 本地文件上传    \nlocal:\n    domain: http://127.0.0.1:19330\n    path: D:/aiera/uploadPath\n    prefix: /statics\n\n# FastDFS配置\nfdfs:\n  domain: http://8.129.231.12\n  soTimeout: 3000\n  connectTimeout: 2000\n  trackerList: 8.129.231.12:22122\n\n# Minio配置\nminio:\n  inUrl: http://10.2.12.201:9000\n  outUrl: http://61.160.52.204:49000\n  accessKey: minioadmin\n  secretKey: minioadmin\n  bucketName: aiera', '84251959d2b1cf150f5b514ac7acaa70', '2024-05-09 07:31:59', '2024-05-09 07:33:53', 'nacos', '0:0:0:0:0:0:0:1', '', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (163, 'application-common.yml', 'DEFAULT_GROUP' , 'dubbo:\n  application:\n    # 关闭qos端口避免单机多生产者端口冲突 如需使用自行开启\n    qos-enable: false\n  protocol:\n    # 如需使用 Triple 3.0 新协议 可查看官方文档\n    # 使用 dubbo 协议通信\n    name: dubbo\n    # dubbo 协议端口(-1表示自增端口,从20880开始)\n    port: -1\n    # 指定dubbo协议注册ip\n    # host: 192.168.0.100\n  # 消费者相关配置\n  consumer:\n    # 超时时间\n    timeout: 3000\n  scan:\n    # 接口实现类扫描\n    base-packages: cn.hfstorm.aiera.**.dubbo\n  # 自定义配置\n  custom:\n    # 全局请求log\n    request-log: true\n    # info 基础信息 param 参数信息 full 全部\n    log-level: info\n\nspring:\n  # 资源信息\n  messages:\n    # 国际化资源文件路径\n    basename: i18n/messages\n  servlet:\n    multipart:\n      # 整个请求大小限制\n      max-request-size: 20MB\n      # 上传单个文件大小限制\n      max-file-size: 10MB\n  mvc:\n    format:\n      date-time: yyyy-MM-dd HH:mm:ss\n  #jackson配置\n  jackson:\n    # 日期格式化\n    date-format: yyyy-MM-dd HH:mm:ss\n    serialization:\n      # 格式化输出\n      INDENT_OUTPUT: false\n      # 忽略无法转换的对象\n      fail_on_empty_beans: false\n    deserialization:\n      # 允许对象忽略json中不存在的属性\n      fail_on_unknown_properties: false\n  cloud:\n    # sentinel 配置\n    sentinel:\n      # sentinel 开关\n      enabled: true\n      # 取消控制台懒加载\n      eager: true\n      transport:\n        # dashboard控制台服务名 用于服务发现\n        # 如无此配置将默认使用下方 dashboard 配置直接注册\n        server-name: aiera-sentinel-dashboard\n        # 客户端指定注册的ip 用于多网卡ip不稳点使用\n        # client-ip:\n        # 控制台地址 从1.3.0开始使用 server-name 注册\n        # dashboard: localhost:8718\n\n  # redis通用配置 子服务可以自行配置进行覆盖\n  data:\n    redis:\n      host: localhost\n      port: 6379\n      # 密码(如没有密码请注释掉)\n      password: 123456\n      database: 0\n      timeout: 10s\n      ssl.enabled: false\n\n# redisson 配置\nredisson:\n  # redis key前缀\n  keyPrefix:\n  # 线程池数量\n  threads: 4\n  # Netty线程池数量\n  nettyThreads: 8\n  # 单节点配置\n  singleServerConfig:\n    # 客户端名称\n    clientName: ${spring.application.name}\n    # 最小空闲连接数\n    connectionMinimumIdleSize: 8\n    # 连接池大小\n    connectionPoolSize: 32\n    # 连接空闲超时，单位：毫秒\n    idleConnectionTimeout: 10000\n    # 命令等待超时，单位：毫秒\n    timeout: 3000\n    # 发布和订阅连接池大小\n    subscriptionConnectionPoolSize: 50\n\n# 分布式锁 lock4j 全局配置\nlock4j:\n  # 获取分布式锁超时时间，默认为 3000 毫秒\n  acquire-timeout: 3000\n  # 分布式锁的超时时间，默认为 30 秒\n  expire: 30000\n\n# 暴露监控端点\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include:  \'*\'\n  endpoint:\n    health:\n      show-details: ALWAYS\n    logfile:\n      external-file: ./logs/${spring.application.name}/console.log\n\n# 日志配置\nlogging:\n  level:\n    org.springframework: warn\n    org.apache.dubbo: warn\n    com.alibaba.nacos: warn\n  config: classpath:logback-plus.xml\n\n# Sa-Token配置\nsa-token:\n  # token名称 (同时也是cookie名称)\n  token-name: Authorization\n  # token 有效期（单位：秒） 默认30天2592000秒，-1 代表永久有效\n  timeout: 86400\n  # token 最低活跃频率（单位：秒），如果 token 超过此时间没有访问系统就会被冻结，默认-1 代表不限制，永不冻结\n  active-timeout: -1\n  # 开启内网服务调用鉴权(不允许越过gateway访问内网服务 保障服务安全)\n  check-same-token: true\n  # 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)\n  is-concurrent: true\n  # 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)\n  is-share: false\n  # jwt秘钥\n  jwt-secret-key: abcdefghijklmnopqrstuvwxyz\n\n# api接口加密\napi-decrypt:\n  # 是否开启全局接口加密\n  enabled: false\n  # AES 加密头标识\n  headerFlag: encrypt-key\n  # 响应加密公钥 非对称算法的公私钥 如：SM2，RSA 使用者请自行更换\n  # 对应前端解密私钥 MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAmc3CuPiGL/LcIIm7zryCEIbl1SPzBkr75E2VMtxegyZ1lYRD+7TZGAPkvIsBcaMs6Nsy0L78n2qh+lIZMpLH8wIDAQABAkEAk82Mhz0tlv6IVCyIcw/s3f0E+WLmtPFyR9/WtV3Y5aaejUkU60JpX4m5xNR2VaqOLTZAYjW8Wy0aXr3zYIhhQQIhAMfqR9oFdYw1J9SsNc+CrhugAvKTi0+BF6VoL6psWhvbAiEAxPPNTmrkmrXwdm/pQQu3UOQmc2vCZ5tiKpW10CgJi8kCIFGkL6utxw93Ncj4exE/gPLvKcT+1Emnoox+O9kRXss5AiAMtYLJDaLEzPrAWcZeeSgSIzbL+ecokmFKSDDcRske6QIgSMkHedwND1olF8vlKsJUGK3BcdtM8w4Xq7BpSBwsloE=\n  publicKey: MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJnNwrj4hi/y3CCJu868ghCG5dUj8wZK++RNlTLcXoMmdZWEQ/u02RgD5LyLAXGjLOjbMtC+/J9qofpSGTKSx/MCAwEAAQ==\n  # 请求解密私钥 非对称算法的公私钥 如：SM2，RSA 使用者请自行更换\n  # 对应前端加密公钥 MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKoR8mX0rGKLqzcWmOzbfj64K8ZIgOdHnzkXSOVOZbFu/TJhZ7rFAN+eaGkl3C4buccQd/EjEsj9ir7ijT7h96MCAwEAAQ==\n  privateKey: MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAqhHyZfSsYourNxaY7Nt+PrgrxkiA50efORdI5U5lsW79MmFnusUA355oaSXcLhu5xxB38SMSyP2KvuKNPuH3owIDAQABAkAfoiLyL+Z4lf4Myxk6xUDgLaWGximj20CUf+5BKKnlrK+Ed8gAkM0HqoTt2UZwA5E2MzS4EI2gjfQhz5X28uqxAiEA3wNFxfrCZlSZHb0gn2zDpWowcSxQAgiCstxGUoOqlW8CIQDDOerGKH5OmCJ4Z21v+F25WaHYPxCFMvwxpcw99EcvDQIgIdhDTIqD2jfYjPTY8Jj3EDGPbH2HHuffvflECt3Ek60CIQCFRlCkHpi7hthhYhovyloRYsM+IS9h/0BzlEAuO0ktMQIgSPT3aFAgJYwKpqRYKlLDVcflZFCKY7u3UP8iWi1Qw0Y=\n\n\n# 接口文档配置\nspringdoc:\n  api-docs:\n    # 是否开启接口文档\n    enabled: true\n  swagger-ui:\n    version: 5.2.0\n#    # 持久化认证数据\n#    persistAuthorization: true\n  info:\n    # 标题\n    title: \'标题：AI ERA 微服务权限管理系统_接口文档\'\n    # 描述\n    description: \'\'\n    # 版本\n    version: \'版本号：系统版本...\'\n    # 作者信息\n    contact:\n      name: hmy\n      email: hmy_hammer@163.com\n      url: https://github.com/HfRainstorm/aiera\n  components:\n    # 鉴权方式配置\n    security-schemes:\n      apiKey:\n        type: APIKEY\n        in: HEADER\n        name: ${sa-token.token-name}\n\n# seata配置\nseata:\n  # 是否启用\n  enabled: false\n  # Seata 应用编号，默认为应用名\n  application-id: ${spring.application.name}\n  # Seata 事务组编号，用于 TC 集群名\n  tx-service-group: ${spring.application.name}-group\n', '516fd63439dd6b3540d68683aa18bfbb', '2024-05-10 07:08:30', '2024-05-10 07:08:30', NULL, '0:0:0:0:0:0:0:1', '', 'prod', '通用配置基础配置', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (164, 'datasource.yml', 'DEFAULT_GROUP', 'datasource:\n  system-master:\n    # jdbc 所有参数配置参考 https://lionli.blog.csdn.net/article/details/122018562\n    # rewriteBatchedStatements=true 批处理优化 大幅提升批量插入更新删除性能\n    url: jdbc:mysql://localhost:3306/aiera?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true\n    username: root\n    password: root\n  job:\n    url: jdbc:mysql://localhost:3306/xxl-job?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true\n    username: root\n    password: root\n#  system-oracle:\n#    url: jdbc:oracle:thin:@//localhost:1521/XE\n#    username: ROOT\n#    password: password\n#  system-postgres:\n#    url: jdbc:postgresql://localhost:5432/postgres?useUnicode=true&characterEncoding=utf8&useSSL=true&autoReconnect=true&reWriteBatchedInserts=true\n#    username: root\n#    password: password\n\nspring:\n  datasource:\n    type: com.zaxxer.hikari.HikariDataSource\n    # 动态数据源文档 https://www.kancloud.cn/tracy5546/dynamic-datasource/content\n    dynamic:\n      # 性能分析插件(有性能损耗 不建议生产环境使用)\n      p6spy: true\n      # 开启seata代理，开启后默认每个数据源都代理，如果某个不需要代理可单独关闭\n      seata: ${seata.enabled}\n      # 严格模式 匹配不到数据源则报错\n      strict: true\n      hikari:\n        # 最大连接池数量\n        maxPoolSize: 20\n        # 最小空闲线程数量\n        minIdle: 10\n        # 配置获取连接等待超时的时间\n        connectionTimeout: 30000\n        # 校验超时时间\n        validationTimeout: 5000\n        # 空闲连接存活最大时间，默认10分钟\n        idleTimeout: 600000\n        # 此属性控制池中连接的最长生命周期，值0表示无限生命周期，默认30分钟\n        maxLifetime: 1800000\n        # 多久检查一次连接的活性\n        keepaliveTime: 30000\n\n\n# MyBatisPlus配置\n# https://baomidou.com/config/\nmybatis-plus:\n  # 不支持多包, 如有需要可在注解配置 或 提升扫包等级\n  # 例如 com.**.**.mapper\n  mapperPackage: cn.hfstorm.aiera.**.mapper\n  # 对应的 XML 文件位置\n  mapperLocations: classpath*:mapper/**/*Mapper.xml\n  # 实体扫描，多个package用逗号或者分号分隔\n  typeAliasesPackage: cn.hfstorm.aiera.**.domain\n  global-config:\n    dbConfig:\n      # 主键类型\n      # AUTO 自增 NONE 空 INPUT 用户输入 ASSIGN_ID 雪花 ASSIGN_UUID 唯一 UUID\n      # 如需改为自增 需要将数据库表全部设置为自增\n      idType: ASSIGN_ID', '295ea10158d07af511281c0866aaa776', '2024-05-10 07:08:30', '2024-05-10 07:08:30', NULL, '0:0:0:0:0:0:0:1', '', 'prod', '数据源配置', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (165, 'aiera-gateway.yml', 'DEFAULT_GROUP', '# 安全配置\nsecurity:\n  # 防止XSS攻击\n  xss:\n    enabled: true\n    excludeUrls:\n      - /system/notice\n  # 不校验白名单\n  ignore:\n    whites:\n      - /auth/code\n      - /auth/logout\n      - /auth/login\n      - /auth/binding/*\n      - /auth/register\n      - /resource/sms/code\n      - /*/v3/api-docs\n      - /*/error\n      - /csrf\n\nspring:\n  cloud:\n    # 网关配置\n    gateway:\n      # 打印请求日志(自定义)\n      requestLog: true\n      discovery:\n        locator:\n          lowerCaseServiceId: true\n          enabled: true\n      routes:\n        # 认证中心\n        - id: aiera-auth\n          uri: lb://aiera-auth\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        # 系统模块\n        - id: aiera-system\n          uri: lb://aiera-system\n          predicates:\n            - Path=/system/**,/monitor/**\n          filters:\n            - StripPrefix=1\n        # 资源服务\n        - id: aiera-file\n          uri: lb://aiera-file\n          predicates:\n            - Path=/file/**\n          filters:\n            - StripPrefix=1\n\n\n    # sentinel 配置\n    sentinel:\n      filter:\n        enabled: false\n      # nacos配置持久化\n      datasource:\n        ds1:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            dataId: sentinel-${spring.application.name}.json\n            groupId: ${spring.cloud.nacos.config.group}\n            namespace: ${spring.profiles.active}\n            data-type: json\n            rule-type: gw-flow\n', 'b43b96d002a9f5d12cb745a7699ad764', '2024-05-10 07:08:30', '2024-05-10 07:08:30', NULL, '0:0:0:0:0:0:0:1', '', 'prod', '网关模块', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (166, 'aiera-auth.yml', 'DEFAULT_GROUP', '# 安全配置\nsecurity:\n  # 验证码\n  captcha:\n    # 是否开启验证码\n    enabled: false\n    # 验证码类型 MATH 数组计算 CHAR 字符验证\n    type: MATH\n    # line 线段干扰 circle 圆圈干扰 shear 扭曲干扰\n    category: CIRCLE\n    # 数字验证码位数\n    numberLength: 1\n    # 字符验证码长度\n    charLength: 4\n\n# 用户配置\nuser:\n  password:\n    # 密码最大错误次数\n    maxRetryCount: 500\n    # 密码锁定时间（默认10分钟）\n    lockTime: 10\n', '493cac677549e7afacd8da0cf03e81c0', '2024-05-10 07:08:30', '2024-05-10 07:08:30', NULL, '0:0:0:0:0:0:0:1', '', 'prod', '认证中心', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (167, 'aiera-monitor.yml', 'DEFAULT_GROUP', '# 监控中心配置\nspring:\n  security:\n    user:\n      name: aiera\n      password: 123456\n  boot:\n    admin:\n      ui:\n        title: aiera-Cloud服务监控中心\n      discovery:\n        # seata 不具有健康检查的能力 防止报错排除掉\n        ignored-services: \n', '6840d6585e13553b8c60ffe3471c31f0', '2024-05-10 07:08:30', '2024-05-10 07:08:30', NULL, '0:0:0:0:0:0:0:1', '', 'prod', '监控中心', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (168, 'aiera-system.yml', 'DEFAULT_GROUP', 'spring:\n  datasource:\n    dynamic:\n      # 设置默认的数据源或者数据源组,默认值即为 master\n      primary: master\n      datasource:\n        # 主库数据源\n        master:\n          type: ${spring.datasource.type}\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: ${datasource.system-master.url}\n          username: ${datasource.system-master.username}\n          password: ${datasource.system-master.password}\n#        oracle:\n#          type: ${spring.datasource.type}\n#          driverClassName: oracle.jdbc.OracleDriver\n#          url: ${datasource.system-oracle.url}\n#          username: ${datasource.system-oracle.username}\n#          password: ${datasource.system-oracle.password}\n#        postgres:\n#          type: ${spring.datasource.type}\n#          driverClassName: org.postgresql.Driver\n#          url: ${datasource.system-postgres.url}\n#          username: ${datasource.system-postgres.username}\n#          password: ${datasource.system-postgres.password}\n\n\n# elasticsearch 功能配置\n# 文档地址: https://www.easy-es.cn/\n# 更改包名需要去 EasyEsConfiguration 修改包扫描(后续版本支持配置文件读取)\neasy-es:\n  # 是否开启EE自动配置\n  enable: false\n  # es连接地址+端口 格式必须为ip:port,如果是集群则可用逗号隔开\n  address : localhost:9200\n  # 默认为http\n  schema: http\n  # 注意ES建议使用账号认证 不使用会报警告日志\n  #如果无账号密码则可不配置此行\n  #username:\n  #如果无账号密码则可不配置此行\n  #password:\n  # 心跳策略时间 单位:ms\n  keep-alive-millis: 18000\n  # 连接超时时间 单位:ms\n  connectTimeout: 5000\n  # 通信超时时间 单位:ms\n  socketTimeout: 5000\n  # 连接请求超时时间 单位:ms\n  connectionRequestTimeout: 5000\n  # 最大连接数 单位:个\n  maxConnTotal: 100\n  # 最大连接路由数 单位:个\n  maxConnPerRoute: 100\n  global-config:\n    # 开启控制台打印通过本框架生成的DSL语句,默认为开启,测试稳定后的生产环境建议关闭,以提升少量性能\n    print-dsl: true\n    # 异步处理索引是否阻塞主线程 默认阻塞 数据量过大时调整为非阻塞异步进行 项目启动更快\n    asyncProcessIndexBlocking: true\n    db-config:\n      # 是否开启下划线转驼峰 默认为false\n      map-underscore-to-camel-case: true\n      # id生成策略 customize为自定义,id值由用户生成,比如取MySQL中的数据id,如缺省此项配置,则id默认策略为es自动生成\n      id-type: customize\n      # 字段更新策略 默认为not_null\n      field-strategy: not_null\n      # 默认开启,查询若指定了size超过1w条时也会自动开启,开启后查询所有匹配数据,若不开启,会导致无法获取数据总条数,其它功能不受影响.\n      enable-track-total-hits: true\n      # 数据刷新策略,默认为不刷新\n      refresh-policy: immediate\n\n\nxxl:\n  job:\n    # 执行器开关\n    enabled: false\n    # 调度中心地址：如调度中心集群部署存在多个地址则用逗号分隔。\n    # admin-addresses: http://localhost:9900\n    # 调度中心应用名 通过服务名连接调度中心(启用admin-appname会导致admin-addresses不生效)\n    admin-appname: xxl-job-admin\n    # 执行器通讯TOKEN：非空时启用\n    access-token: xxl-job\n    # 执行器配置\n    executor:\n      # 执行器AppName：执行器心跳注册分组依据；为空则关闭自动注册\n      appname: ${spring.application.name}-executor\n      # 29203 端口 随着主应用端口飘逸 避免集群冲突\n      port: 9901\n      # 执行器注册：默认IP:PORT\n      address:\n      # 执行器IP：默认自动获取IP\n      ip:\n      # 执行器运行日志文件存储磁盘路径\n      logpath: ./logs/${spring.application.name}/xxl-job\n      # 执行器日志文件保存天数：大于3生效\n      logretentiondays: 30\n', '51a28997df0ce414b84d6d9fbd7ec3de', '2024-05-10 07:08:30', '2024-05-10 07:08:30', NULL, '0:0:0:0:0:0:0:1', '', 'prod', '系统模块', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (169, 'xxl-job-admin.yml', 'DEFAULT_GROUP', '# server 配置\r\nspring:\r\n  datasource:\r\n    type: com.zaxxer.hikari.HikariDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: ${datasource.job.url}\r\n    username: ${datasource.job.username}\r\n    password: ${datasource.job.password}\r\n    hikari:\r\n      auto-commit: true\r\n      connection-test-query: SELECT 1\r\n      connection-timeout: 10000\r\n      idle-timeout: 30000\r\n      max-lifetime: 900000\r\n      maximum-pool-size: 30\r\n      minimum-idle: 10\r\n      pool-name: HikariCP\r\n      validation-timeout: 1000\r\n  mail:\r\n    from: xxx@qq.com\r\n    host: smtp.qq.com\r\n    username: xxx@qq.com\r\n    password: xxx\r\n    port: 25\r\n    properties:\r\n      mail:\r\n        smtp:\r\n          auth: true\r\n          socketFactory:\r\n            class: javax.net.ssl.SSLSocketFactory\r\n          starttls:\r\n            enable: true\r\n            required: true\r\n\r\n# mybatis 配置\r\nmybatis:\r\n  mapper-locations: classpath:/mybatis-mapper/*Mapper.xml\r\n\r\n# Actuator 监控端点的配置项\r\nmanagement:\r\n  health:\r\n    mail:\r\n      enabled: false\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'\r\n  endpoint:\r\n    health:\r\n      show-details: ALWAYS\r\n    logfile:\r\n      external-file: ./logs/${spring.application.name}/console.log\r\n\r\n# xxljob系统配置\r\nxxl:\r\n  job:\r\n    # 鉴权token\r\n    accessToken: xxl-job\r\n    # 国际化\r\n    i18n: zh_CN\r\n    # 日志清理\r\n    logretentiondays: 30\r\n    triggerpool:\r\n      fast:\r\n        max: 200\r\n      slow:\r\n        max: 100\r\n', '553e2a8c91fa07f9015c823761fe34ea', '2024-05-10 07:08:30', '2024-05-10 07:08:30', NULL, '0:0:0:0:0:0:0:1', '', 'prod', NULL, NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (170, 'aiera-file.yml', 'DEFAULT_GROUP', '# 本地文件上传    \nlocal:\n    domain: http://127.0.0.1:19330\n    path: D:/aiera/uploadPath\n    prefix: /statics\n\n# FastDFS配置\nfdfs:\n  domain: http://8.129.231.12\n  soTimeout: 3000\n  connectTimeout: 2000\n  trackerList: 8.129.231.12:22122\n\n# Minio配置\nminio:\n  inUrl: http://10.2.12.201:9000\n  outUrl: http://61.160.52.204:49000\n  accessKey: minioadmin\n  secretKey: minioadmin\n  bucketName: aiera', '84251959d2b1cf150f5b514ac7acaa70', '2024-05-10 07:08:30', '2024-05-10 07:08:30', NULL, '0:0:0:0:0:0:0:1', '', 'prod', '', NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (171, 'aiera-ai.yml', 'DEFAULT_GROUP', 'spring:\n  ai:\n    vectorstore:\n      milvus:\n        client:\n          host: \"localhost\"\n          port: 19530\n          username: \"root\"\n          password: \"milvus\"\n        databaseName: \"aiera\"\n        collectionName: \"vector_store\"\n        embeddingDimension: 1536', '279213ce335b3533313a07ea71228921', '2025-02-16 17:41:33', '2025-02-16 17:43:29', NULL, '0:0:0:0:0:0:0:1', '', 'prod', 'Spring AI配置', NULL, NULL, 'yaml', NULL, '');


/******************************************/
/*   表名称 = config_info  since 2.5.0                */
/******************************************/
CREATE TABLE `config_info_gray`
(
    `id`                 bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`            varchar(255)    NOT NULL COMMENT 'data_id',
    `group_id`           varchar(128)    NOT NULL COMMENT 'group_id',
    `content`            longtext        NOT NULL COMMENT 'content',
    `md5`                varchar(32)              DEFAULT NULL COMMENT 'md5',
    `src_user`           text COMMENT 'src_user',
    `src_ip`             varchar(100)             DEFAULT NULL COMMENT 'src_ip',
    `gmt_create`         datetime(3)     NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'gmt_create',
    `gmt_modified`       datetime(3)     NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'gmt_modified',
    `app_name`           varchar(128)             DEFAULT NULL COMMENT 'app_name',
    `tenant_id`          varchar(128)             DEFAULT '' COMMENT 'tenant_id',
    `gray_name`          varchar(128)    NOT NULL COMMENT 'gray_name',
    `gray_rule`          text            NOT NULL COMMENT 'gray_rule',
    `encrypted_data_key` varchar(256)    NOT NULL DEFAULT '' COMMENT 'encrypted_data_key',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_configinfogray_datagrouptenantgray` (`data_id`, `group_id`, `tenant_id`, `gray_name`),
    KEY `idx_dataid_gmt_modified` (`data_id`, `gmt_modified`),
    KEY `idx_gmt_modified` (`gmt_modified`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='config_info_gray';

/******************************************/
/*   表名称 = config_info_beta             */
/******************************************/
CREATE TABLE `config_info_beta`
(
    `id`                 bigint(20)    NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`            varchar(255)  NOT NULL COMMENT 'data_id',
    `group_id`           varchar(128)  NOT NULL COMMENT 'group_id',
    `app_name`           varchar(128)           DEFAULT NULL COMMENT 'app_name',
    `content`            longtext      NOT NULL COMMENT 'content',
    `beta_ips`           varchar(1024)          DEFAULT NULL COMMENT 'betaIps',
    `md5`                varchar(32)            DEFAULT NULL COMMENT 'md5',
    `gmt_create`         datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`       datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`           text COMMENT 'source user',
    `src_ip`             varchar(50)            DEFAULT NULL COMMENT 'source ip',
    `tenant_id`          varchar(128)           DEFAULT '' COMMENT '租户字段',
    `encrypted_data_key` varchar(1024) NOT NULL DEFAULT '' COMMENT '密钥',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`, `group_id`, `tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='config_info_beta';

/******************************************/
/*   表名称 = config_info_tag              */
/******************************************/
CREATE TABLE `config_info_tag`
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) NOT NULL COMMENT 'data_id',
    `group_id`     varchar(128) NOT NULL COMMENT 'group_id',
    `tenant_id`    varchar(128)          DEFAULT '' COMMENT 'tenant_id',
    `tag_id`       varchar(128) NOT NULL COMMENT 'tag_id',
    `app_name`     varchar(128)          DEFAULT NULL COMMENT 'app_name',
    `content`      longtext     NOT NULL COMMENT 'content',
    `md5`          varchar(32)           DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`     text COMMENT 'source user',
    `src_ip`       varchar(50)           DEFAULT NULL COMMENT 'source ip',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`, `group_id`, `tenant_id`, `tag_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='config_info_tag';

/******************************************/
/*   表名称 = config_tags_relation         */
/******************************************/
CREATE TABLE `config_tags_relation`
(
    `id`        bigint(20)   NOT NULL COMMENT 'id',
    `tag_name`  varchar(128) NOT NULL COMMENT 'tag_name',
    `tag_type`  varchar(64)  DEFAULT NULL COMMENT 'tag_type',
    `data_id`   varchar(255) NOT NULL COMMENT 'data_id',
    `group_id`  varchar(128) NOT NULL COMMENT 'group_id',
    `tenant_id` varchar(128) DEFAULT '' COMMENT 'tenant_id',
    `nid`       bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'nid, 自增长标识',
    PRIMARY KEY (`nid`),
    UNIQUE KEY `uk_configtagrelation_configidtag` (`id`, `tag_name`, `tag_type`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='config_tag_relation';

/******************************************/
/*   表名称 = group_capacity               */
/******************************************/
CREATE TABLE `group_capacity`
(
    `id`                bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `group_id`          varchar(128)        NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
    `quota`             int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
    `usage`             int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '使用量',
    `max_size`          int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
    `max_aggr_size`     int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
    `gmt_create`        datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`      datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='集群、各Group容量信息表';

/******************************************/
/*   表名称 = his_config_info              */
/******************************************/
CREATE TABLE `his_config_info`
(
    `id`                 bigint(20) unsigned NOT NULL COMMENT 'id',
    `nid`                bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'nid, 自增标识',
    `data_id`            varchar(255)        NOT NULL COMMENT 'data_id',
    `group_id`           varchar(128)        NOT NULL COMMENT 'group_id',
    `app_name`           varchar(128)                 DEFAULT NULL COMMENT 'app_name',
    `content`            longtext            NOT NULL COMMENT 'content',
    `md5`                varchar(32)                  DEFAULT NULL COMMENT 'md5',
    `gmt_create`         datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`       datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`           text COMMENT 'source user',
    `src_ip`             varchar(50)                  DEFAULT NULL COMMENT 'source ip',
    `op_type`            char(10)                     DEFAULT NULL COMMENT 'operation type',
    `tenant_id`          varchar(128)                 DEFAULT '' COMMENT '租户字段',
    `encrypted_data_key` varchar(1024)       NOT NULL DEFAULT '' COMMENT '密钥',
    `publish_type`       varchar(50)                  DEFAULT 'formal' COMMENT 'publish type gray or formal',
    `gray_name`          varchar(50)                  DEFAULT NULL COMMENT 'gray name',
    `ext_info`           longtext                     DEFAULT NULL COMMENT 'ext info',
    PRIMARY KEY (`nid`),
    KEY `idx_gmt_create` (`gmt_create`),
    KEY `idx_gmt_modified` (`gmt_modified`),
    KEY `idx_did` (`data_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='多租户改造';


/******************************************/
/*   表名称 = tenant_capacity              */
/******************************************/
CREATE TABLE `tenant_capacity`
(
    `id`                bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`         varchar(128)        NOT NULL DEFAULT '' COMMENT 'Tenant ID',
    `quota`             int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
    `usage`             int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '使用量',
    `max_size`          int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
    `max_aggr_size`     int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
    `gmt_create`        datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`      datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='租户容量信息表';


CREATE TABLE `tenant_info`
(
    `id`            bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `kp`            varchar(128) NOT NULL COMMENT 'kp',
    `tenant_id`     varchar(128) default '' COMMENT 'tenant_id',
    `tenant_name`   varchar(128) default '' COMMENT 'tenant_name',
    `tenant_desc`   varchar(256) DEFAULT NULL COMMENT 'tenant_desc',
    `create_source` varchar(32)  DEFAULT NULL COMMENT 'create_source',
    `gmt_create`    bigint(20)   NOT NULL COMMENT '创建时间',
    `gmt_modified`  bigint(20)   NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`, `tenant_id`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='tenant_info';

INSERT INTO `tenant_info` VALUES (1, '1', 'dev', 'dev', '开发环境', NULL, 1641741261189, 1641741261189);
INSERT INTO `tenant_info` VALUES (2, '1', 'prod', 'prod', '生产环境', NULL, 1641741270448, 1641741287236);

CREATE TABLE `users`
(
    `username` varchar(50)  NOT NULL PRIMARY KEY COMMENT 'username',
    `password` varchar(500) NOT NULL COMMENT 'password',
    `enabled`  boolean      NOT NULL COMMENT 'enabled'
);

CREATE TABLE `roles`
(
    `username` varchar(50) NOT NULL COMMENT 'username',
    `role`     varchar(50) NOT NULL COMMENT 'role',
    UNIQUE INDEX `idx_user_role` (`username` ASC, `role` ASC) USING BTREE
);

CREATE TABLE `permissions`
(
    `role`     varchar(50)  NOT NULL COMMENT 'role',
    `resource` varchar(128) NOT NULL COMMENT 'resource',
    `action`   varchar(8)   NOT NULL COMMENT 'action',
    UNIQUE INDEX `uk_role_permission` (`role`, `resource`, `action`) USING BTREE
);

INSERT INTO users (username, password, enabled) VALUES ('nacos', '$2a$10$4P/F5XsSHsoj3YeiaD57genSUf3i5Lu9ptO4msHFHgGIzHCiG/4ZW', TRUE);

INSERT INTO roles (username, role) VALUES ('nacos', 'ROLE_ADMIN');

commit;
