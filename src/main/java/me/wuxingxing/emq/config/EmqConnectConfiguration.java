package me.wuxingxing.emq.config;

import me.wuxingxing.emq.client.PahoClient;
import me.wuxingxing.emq.service.EmqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xingxing.wu
 * @date 2018/3/30
 */
@Configuration
@EnableConfigurationProperties(EmqConnectProperties.class)
@ConditionalOnProperty(prefix = "emq.connect", name = { "user", "password", "host", "clientId","qos","hosts"})
public class EmqConnectConfiguration {

    @Autowired
    private EmqConnectProperties properties;

    @Bean
    public EmqService emqService() {
        PahoClient pahoClient = new PahoClient(properties);
        pahoClient.start();
        EmqService emqService = new EmqService(properties);
        emqService.setPahoClient(pahoClient);
        return emqService;
    }
}
