package me.wuxingxing.emq.client;

import me.wuxingxing.emq.config.EmqConnectProperties;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author xingxing.wu
 * @date 2018/3/30
 */
public class PahoClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(PahoClient.class);

    private MqttClient client;

    private MqttConnectOptions options;

    private EmqConnectProperties properties;

    public PahoClient(EmqConnectProperties properties){
        this.properties = properties;
    }

    //重新链接
    public void startReconnect() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            if (!client.isConnected()) {
                try {
                    client.connect(options);
                } catch (MqttException e) {
                    LOGGER.warn("emq连接异常", e);
                }
            }
        }, 0, 10 * 1000, TimeUnit.MILLISECONDS);
    }

    public void start() {
        try {
            // host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(properties.getHost(), properties.getClientId()+System.currentTimeMillis(), new MemoryPersistence());
            // MQTT的连接设置
            options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            // 设置连接的用户名
            options.setUserName(properties.getUser());
            // 设置连接的密码
            options.setPassword(properties.getPassword().toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);
            //配置多个服务器列表，一个挂掉会自动切换到其他正常的服务器，挂掉的服务器正常后，客户端会再次切换回来
            options.setServerURIs(properties.getHosts());
            // 设置回调
//            client.setCallback(new PushCallback());
//            MqttTopic mqttTopic = client.getTopic(topic);
//            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
//            options.setWill(mqttTopic, "close".getBytes(), 0, true);
            client.connect(options);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public MqttClient getClient(){
        return client;
    }
}
