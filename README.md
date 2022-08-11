# Stock-System( 还没写完......)

## 1. 简单介绍

基于`SpringBoot2.X`、`SpringCloud`、`SpringOAuth2`、开发的纯后端微服务分布式架构系统，不包含前端页面。

> 在功能层面以StockSystem为主题去围绕着实现一些功能，整合多种 Java 常见的微服务技术中间件等

## 2. 基本开发环境

Linux CentOS7 / Idea / Mysql 5.7 / JDK1.8

## 3. 服务模式上的一些小特点

服务为`无状态化` / `可集群部署` / `无缝拓展` / `全链路监控` / `统一日志` / `微服务治理`

> 基于以上，各个服务职责单一，便于拓展维护

### 3.1 项目上使用到的一些技术

1. 交易服务 使用NIO非阻塞式的`Netty`框架，内部以`gRPC + Protobuf`通讯，保证交易功能的稳定性
2. 业务服务 使用`SpringCloud Gateway`作为各个模块统一网关，前置采用`Nginx`反向代理
3. 配置管理 使用`Nacos`作服务发现与统一的配置管理，采用`Sentinel`作熔断组件，同步处理使用`RocketMQ`，适合处理大量的数据
4. 维护管理 使用`ELK`作统一日志管理，`CAT`组件完善的链路监控功能， 完善的服务治理功能， 便于线上维护
5. 数据层采用`Redis`集群作缓存，`Ceph`作分布式文件存储，数据库为PostgreSQL主从模式存储交易数据，MariaDB集群模式存储业务数据，读写分离
6. TCC框架采用`Seata Server`, 分布式事务框架，快速、高效、侵入性低
7. 集群技术整合`MariaDB + Redis + RocketMQ + XXL-JOB + ES`等等，支持并发场景处理数据
8. `SpringBoot + Cloud + Security OAuth2 ` 进行统一认证鉴权与Token增加技术运用
9. 采用`Jenkins + Git + Maven + Docker + Registry`  持续集成技术、便于服务的管理与发布。

## 4. 技术解决方案

| 方案     | 技术                                | 文档  |
|--------|-----------------------------------|-----|
| 通讯     | Boot + gRpc + Protobuf + Netty    |     |
| 统一日志   | ElasticSearch + Logstash + Kibana |     |
| 统一鉴权   | OAuth2 + Redis + TokenEhancer     |     |
| 分布式锁   | Spring Integration Redis          |     |
| 服务管理   | Nacos + MariaDB + Nginx           |     |
| 熔断与降级  | Sentinel + Dashboard              |     |
| 网关     | SpringCoud Gateway                |     |
| 数据源    | JPA / MyBatis                     |     |
| 分布式事务  | Seata Server                      |     |
| 分布式搜索  | ElasticSearch                     |     |
| 异步消息架构 | RocketMQ                          |     |
| 任务调度   | XXL-JOB                           |     |
| 热点缓存   | Redis                             |     |
| 文件存储   | Ceph                              |     |
| 链路监控   | Cat                               |     |

## 5. 程序架构

```
开发中......
```
## 6. 目录结构

```
开发中......
```

## 7. 预览图片

```
开发中
```

## 8. 接口测试

```
开发中
```
