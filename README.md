
[![star](https://gitee.com/nonoas/CRM-v200213/badge/star.svg?theme=dark)](https://gitee.com/nonoas/CRM-v200213/stargazers)

# 软件介绍

本软件是基于 JavaFX8 的一款单机的小型店铺管理系统，数据库使用sqlite3，
无需用户安装数据库软件，也无需软件方提供远程数据库。功能包括 商品管理、用户管理、员工管理、套餐管理。报表统计等功能。
本软件将持续更新维护。

# 软件首页

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200818150254854.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDE1NTExNQ==,size_16,color_FFFFFF,t_70#pic_center)

# 提示

软件需使用maven打包，打包插件如下

``` xml
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
- 可执行的jar
- data
- config
- photo