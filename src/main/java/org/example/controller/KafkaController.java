package org.example.controller;

import io.swagger.annotations.Api;
import org.example.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
@Api(tags = "Kafka消息管理")
public class KafkaController {

    @Autowired
    private KafkaProducerService producerService;

    @GetMapping("/produce")
    public String produce() {
        String message = "{\"device_id\":\"test\",\"temperature\":23.5}";
        producerService.send("sensor-data", message);
        return "消息已发送";
    }
}