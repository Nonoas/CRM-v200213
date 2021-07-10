
# ���̹���ϵͳ

---

[![star](https://gitee.com/nonoas/CRM-v200213/badge/star.svg?theme=dark)](https://gitee.com/nonoas/CRM-v200213/stargazers)
[![fork](https://gitee.com/nonoas/CRM-v200213/badge/fork.svg?theme=dark)](https://gitee.com/nonoas/CRM-v200213/members)

## �������

������Ǳ�����ǻ��� JavaFX8 ��һ�����С�͵��̹���ϵͳ�����ݿ�ʹ����Ƕʽ�� [H2���ݿ�](https://baike.baidu.com/item/H2%E6%95%B0%E6%8D%AE%E5%BA%93/23316077?fr=aladdin) ��
�����û���װ���ݿ������Ҳ进价��ṩԶ�����ݿ⡣���ܰ��� ��Ʒ�����û�����Ա�������ײ͹�������ͳ�Ƶȹ��ܡ�
进价�������ά����

## �����ҳ

![�����ҳ](https://img-blog.csdnimg.cn/20210610175826650.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDE1NTExNQ==,size_16,color_FFFFFF,t_70)

## ��Ա����

![��Ա����](https://img-blog.csdnimg.cn/20210610175957872.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDE1NTExNQ==,size_16,color_FFFFFF,t_70)

## ��Ʒ����

![��Ʒ����](https://img-blog.csdnimg.cn/20210610180046752.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDE1NTExNQ==,size_16,color_FFFFFF,t_70)

## ��ʾ

�����ʹ��maven进价�������

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
进价��Ŀ¼进价��ļ���
```
��Ŀ¼
|
��  CRM-v200324.jar (������)
��  
����config (�����ļ�)
��  ��  userConfig.properties
��  ��  
��  ����splash (进价ͼƬ)
��          Splash.png
��          
����data (���ݿ�)
��      mycrm.db
��      mycrm.mv.db
��      mycrm.trace.db
��      
����photo (��Ƭ)
    ����goods
    ����user
```

## ��л
JavaFX��ʽ��[https://github.com/dicolar/jbootx](https://github.com/dicolar/jbootx)