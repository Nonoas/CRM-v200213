
# ���̹���ϵͳ

---

[![star](https://gitee.com/nonoas/CRM-v200213/badge/star.svg?theme=dark)](https://gitee.com/nonoas/CRM-v200213/stargazers)
[![fork](https://gitee.com/nonoas/CRM-v200213/badge/fork.svg?theme=dark)](https://gitee.com/nonoas/CRM-v200213/members)

## �������

������ǻ��� JavaFX8 ��һ�����С�͵��̹���ϵͳ�����ݿ�ʹ��sqlite3��
�����û���װ���ݿ������Ҳ����������ṩԶ�����ݿ⡣���ܰ��� ��Ʒ�����û�����Ա�������ײ͹�������ͳ�Ƶȹ��ܡ�
���������������ά����

## �����ҳ

![�����ҳ](https://img-blog.csdnimg.cn/20210610175826650.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDE1NTExNQ==,size_16,color_FFFFFF,t_70)

## ��Ա����

![��Ա����](https://img-blog.csdnimg.cn/20210610175957872.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDE1NTExNQ==,size_16,color_FFFFFF,t_70)

## ��Ʒ����

![��Ʒ����](https://img-blog.csdnimg.cn/20210610180046752.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDE1NTExNQ==,size_16,color_FFFFFF,t_70)

## ��ʾ

�����ʹ��maven���������������

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
����������Ŀ¼����������ļ���
```
��Ŀ¼
|
��  CRM-v200324.jar (������)
��  
����config (�����ļ�)
��  ��  userConfig.properties
��  ��  
��  ����splash (��������ͼƬ)
��          Splash.png
��          
����data (���ݿ�)
��      mycrm.db
��      
����photo (��Ƭ)
    ����goods
    ����user
```