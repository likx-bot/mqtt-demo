package org.example.kafka;

import lombok.extern.slf4j.Slf4j;
import org.example.service.SensorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumerService {

    @Autowired
    private SensorDataService sensorDataService;

    @KafkaListener(topics = "robot-data")
    public void consume(String message) {
        log.info("从 Kafka 收到消息: {}", message);
        
        // 这里写你的业务逻辑，比如存入 InfluxDB
        // 解析 JSON
        // 写入时序库
        sensorDataService.writeData123(message);
    }
}