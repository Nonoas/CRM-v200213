
# 店铺管理系统

---

[![star](https://gitee.com/nonoas/CRM-v200213/badge/star.svg?theme=dark)](https://gitee.com/nonoas/CRM-v200213/stargazers)
[![fork](https://gitee.com/nonoas/CRM-v200213/badge/fork.svg?theme=dark)](https://gitee.com/nonoas/CRM-v200213/members)

## 软件介绍

本软件是基于 JavaFX8 的一款单机的小型店铺管理系统，数据库使用内嵌式的 [H2数据库](https://baike.baidu.com/item/H2%E6%95%B0%E6%8D%AE%E5%BA%93/23316077?fr=aladdin),
无需用户安装数据库软件，也无需软件方提供远程数据库。

功能包括 商品管理、用户管理、员工管理、套餐管理。报表统计等功能。

本软件将持续更新维护。

## 软件首页

![软件首页](https://img-blog.csdnimg.cn/20210610175826650.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDE1NTExNQ==,size_16,color_FFFFFF,t_70)

## 会员管理

![会员管理](https://img-blog.csdnimg.cn/20210610175957872.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDE1NTExNQ==,size_16,color_FFFFFF,t_70)

## 商品管理

![商品管理](https://img-blog.csdnimg.cn/20210610180046752.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDE1NTExNQ==,size_16,color_FFFFFF,t_70)

## 提示

软件需使用maven打包，打包插件如下

```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>2.3.1.RELEASE</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

```
打包后软件根目录需包含以下文件：
```
根目录
|
│  CRM-v200324.jar (主程序)
│  
├─config (配置文件)
│  │  userConfig.properties
│  │  
│  └─splash (启动界面图片)
│          Splash.png
│          
├─data (数据库)
│      mycrm.db
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