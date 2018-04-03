package me.wuxingxing.emq.service;

import me.wuxingxing.emq.client.PahoClient;
import me.wuxingxing.emq.config.EmqConnectProperties;
import me.wuxingxing.emq.utils.JsonUtil;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xingxing.wu
 * @date 2018/3/30
 */
public class EmqService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmqService.class);

    private EmqConnectProperties properties;

    private PahoClient pahoClient;

    public EmqService(EmqConnectProperties properties) {
        this.properties = properties;
    }

    public void setPahoClient(PahoClient pahoClient) {
        this.pahoClient = pahoClient;
    }

    public Boolean sendMsg(String sendTopic, String content) {
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(properties.getQos());
        message.setRetained(true);
        try {
            MqttClient mqttClient = pahoClient.getClient();
            if (!mqttClient.isConnected()) {
                pahoClient.startReconnect();
            }
            mqttClient.publish(sendTopic, message);
            LOGGER.info("emq信息已发topic: {} - 信息: {}", sendTopic, message);
        } catch (MqttException me) {
            me.printStackTrace();
            return false;
        } finally {
            //  pahoClient.disconnect();
        }
        return true;
    }

    public Boolean sendMsg(String sendTopic, Object content) {
        return sendMsg(sendTopic, JsonUtil.toJson(content));
    }

}