package org.example.publisher;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.kafka.KafkaProducerService;
import org.example.mqtt.MqttClientUtil;
import org.example.mqtt.MqttConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {

    private static final Logger log = LoggerFactory.getLogger(MessagePublisher.class);

    @Autowired
    private MqttConfig mqttConfig;
    @Autowired
    private MqttClientUtil mqttClient;


    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        new Thread(() -> {
            try {
                MqttClientUtil client = new MqttClientUtil(mqttConfig.getBrokerUrl(), "publisher-002");
                client.connect();
                String message = "{\"device_id\":\"device_01\",\"temperature\":23.5,\"humidity\":65.2}";
//                client.publish(mqttConfig.getTopic(), message, 1, false);

                Thread.sleep(1000);
                client.disconnect();
            } catch (Exception e) {
                log.error("MQTT 发布失败：{}", e.getMessage());
            }
        }).start();
    }

    @Scheduled(cron = "0/2 * * * * ?")
    public void publishMessage() throws MqttException {
        log.info("发布消息：{}", "Hello, MQTT!");
        mqttClient.connect();
        String deviceId = "device_"+ Math.random() * 100;
        String message = "{\"device_id\":\""+deviceId+"\",\"temperature\":23.5,\"humidity\":65.2}";
        mqttClient.publish(mqttConfig.getTopic(), message, 1, false);

    }


}
