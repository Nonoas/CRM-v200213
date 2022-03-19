
# 店铺管理系统

---

[![star](https://gitee.com/nonoas/CRM-v200213/badge/star.svg?theme=dark)](https://gitee.com/nonoas/CRM-v200213/stargazers)
[![fork](https://gitee.com/nonoas/CRM-v200213/badge/fork.svg?theme=dark)](https://gitee.com/nonoas/CRM-v200213/members)

## 软件介绍

本软件是基于 JavaFX17 的一款单机的小型店铺管理系统，数据库使用内嵌式的 [H2数据库](https://baike.baidu.com/item/H2%E6%95%B0%E6%8D%AE%E5%BA%93/23316077?fr=aladdin),
无需用户安装数据库软件，也无需软件方提供远程数据库。

功能包括 商品管理、用户管理、员工管理、套餐管理、报表统计等功能。

本软件将持续更新维护。

### 软件预览

![软件首页](https://gitee.com/nonoas/picture-bed/raw/master/crm-client/home.jpg)

### 使用说明

地址：[点击跳转](https://gitee.com/nonoas/CRM-v200213/wikis/pages?sort_id=4913468&doc_id=932745) 至 wiki

## 项目结构

本项目由 Maven 构建，包含以下模块

|模块名|说明|
|---|---|
|crm-client|店铺管理客户端|
|jfx-springboot-maker|用于开发 crm-client 的框架，借鉴于 [springboot-javafx-support](https://github.com/roskenet/springboot-javafx-support) 项目，后续将单独发布|

## 项目开发

### 开发环境

**jdk：** jdk11+

### 打包成品

终端运行以下指令，将在 crm-client 模块的 `target` 目录下生成 zip 文件，解压即可运行

```shell
mvn clean package
```


打包后软件根目录需包含以下文件：
```
根目录
|
|  start.bat (程序启动脚本)
|
│  crm-client-xx.jar (主程序)
│  
├─config (配置文件)
│  │  userConfig.properties
│  │  
│  └─splash (启动界面图片)
│          Splash.png
│          
├─data (数据库)
│      mycrm.mv.db
│      mycrm.trace.db
│
│─log (日志)
│
│      
└─photo (照片)
    ├─goods
    └─user
```

## 感谢

JavaFX样式：[https://github.com/dicolar/jbootx](https://github.com/dicolar/jbootx)

JavaFx-Springboot支持：[https://github.com/roskenet/springboot-javafx-support](https://github.com/roskenet/springboot-javafx-support)