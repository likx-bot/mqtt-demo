package org.example.subscriber;

import com.influxdb.client.write.Point;
import lombok.extern.slf4j.Slf4j;
import org.example.kafka.KafkaProducerService;
import org.example.mqtt.MqttClientUtil;
import org.example.mqtt.MqttConfig;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.service.SensorDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageSubscriber {

//    private static final Logger log = LoggerFactory.getLogger(MessageSubscriber.class);

    @Autowired
    private MqttConfig mqttConfig;
    @Autowired
    private SensorDataService sensorDataService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        new Thread(() -> {
            try {
                MqttClientUtil client = new MqttClientUtil(mqttConfig.getBrokerUrl(), "subscriber-001");
                client.connect();

                client.subscribe(mqttConfig.getTopic(), 1, (topic, message) -> {
//                    sensorDataService.writeData123(message);
                    kafkaProducerService.send("robot-data", message);
                });

                // 保持线程运行，持续监听消息
                while (true) {
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                log.error("MQTT 订阅失败：{}", e.getMessage());
            }
        }).start();
    }
}
