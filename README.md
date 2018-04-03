# mqtt消息服务emq与spring-boot集成（消息发送者）

## 参数配置


| 参数名称 | 参数值 |
| ------- | --------- |
| emq.connect.user | 用户名 |
| emq.connect.password | 密码 |
| emq.connect.host | emq服务器地址 |
| emq.connect.clientId | 客户端id（自己定义） |
| emq.connect.qos | 消息级别（0-至多一次；1-至少一次；2-仅一次） |
| emq.connect.hosts | emq服务器集群 |

## jar引用

```
compile group: 'me.wuxingxing', name: 'emq-spring-boot-starter', version: '1.0.0-SNAPSHOT'

```
or

```
<dependency>  
    <groupId>me.wuxingxing</groupId>  
    <artifactId>emq-spring-boot-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version> 
</dependency>  

```

## 使用方法

```
    @Autowired
    private EmqService emqService;
    
    emqService.sendMsg("topic", "message");
```