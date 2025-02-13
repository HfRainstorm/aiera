# AI Area (AI Era)

> AI Area.

## Introduction

This repository contains many examples of AI product integrations.
For more detailed information, please refer to the README.md.

## How to Contribute

We welcome any form of contribution, including but not limited to:

- Usage examples of Spring AI;
- Best practices for AI projects.

This project repository is under construction. Please read [Roadmap.md](./Roadmap-zh.md) for more information.


## Project Structure
```text

├─aiera（父POM： 项目依赖、modules组织）  
│   ├─aiera-api（远程api入口）  
│   │   ├─aiera-api-system  
│   ├─aiera-common（通用模块）  
│   │   ├─aiera-common-alibaba-bom   
│   │   ├─aiera-common-bom   
│   │   ├─aiera-common-core  
│   │   └─...  
│   ├─aiera-auth（鉴权模块 端口默认19220）           
│   ├─aiera-gateway（网关模块 端口默认18888）    
│   ├─aiera-modules （业务模块）
│   │   ├─aiera-file （文件业务模块）
│   │   └─aiera-system（系统业务模块：例如各种管理）


```