package org.example.mqtt;

import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Configuration
public class MqttConfig {

    @Value("${mqtt.broker-url}")
    private String brokerUrl;

    @Value("${mqtt.topic}")
    private String topic;

    @Value("${mqtt.username:}")
    private String username;

    @Value("${mqtt.password:}")
    private String password;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        System.out.println("========================================");
        System.out.println("MqttConfig Bean 已加载！");
        System.out.println("brokerUrl: " + brokerUrl);
        System.out.println("topic: " + topic);
        System.out.println("========================================");
    }

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public String getTopic() {
        return topic;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean hasAuth() {
        return username != null && !username.isEmpty();
    }

    @Bean("mqttClient")
    public MqttClientUtil mqttClient() {
        return new MqttClientUtil(brokerUrl, "publisher-001");
    }
}
