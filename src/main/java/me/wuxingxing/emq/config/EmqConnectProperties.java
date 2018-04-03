package me.wuxingxing.emq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xingxing.wu
 * @date 2018/3/30
 */
@ConfigurationProperties(prefix = "emq.connect")
public class EmqConnectProperties {

    /**
     * 用户名
     */
    private String user;
    /**
     * 密码
     */
    private String password;
    /**
     * 主机
     */
    private String host;
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 消息QoS
     */
    private Integer qos;

    /**
     * 服务器集群 域名或IP列表
     */
    private String hosts[];

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getQos() {
        return qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }

    public String[] getHosts() {
        return hosts;
    }

    public void setHosts(String[] hosts) {
        this.hosts = hosts;
    }
}
