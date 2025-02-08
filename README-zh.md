# AI Area（AI 时代）

> AI Area。

## 介绍

此仓库中包含许多AI相关的产品对接示例。
更详细的介绍介绍请参阅每个子项目中的 README.md。

## 如何参与

我们欢迎任何形式的贡献，包括但不限于：

- Spring AI 的使用示例；
- AI 项目的最佳实践 等。

此项目仓库正在建设中，请阅读 [Roadmap.md](./Roadmap-zh.md) 了解更多信息。


## 项目结构

```text
|-aiera
|---aiera-common 公共包
|-----aiera-common-bom 所有模块声明的
|-----aiera-common-core  公共文件，对异常、配置、常量进行声明
|-----aiera-common-ai  ai相关公共配置
|---aiera-ai  ai模块
|-----aiera-ai-biz  ai业务逻辑
|-----aiera-ai-core  ai操作核心类，如model调用、prompt生成等
|---aiera-server  ai服务启动


```