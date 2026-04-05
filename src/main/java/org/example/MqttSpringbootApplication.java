package org.example;

import org.example.mqtt.MqttConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring Boot 启动类
 */
@SpringBootApplication
@MapperScan("org.example.mapper")
@EnableScheduling
public class MqttSpringbootApplication {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║   MQTT Demo - Spring Boot 应用     ║");
        System.out.println("╚════════════════════════════════════╝\n");

        ConfigurableApplicationContext context = SpringApplication.run(MqttSpringbootApplication.class, args);
        // 手动检查 MqttConfig 是否存在
        try {
            MqttConfig config = context.getBean(MqttConfig.class);
            System.out.println("✓✓✓ MqttConfig Bean 存在！ ✓✓✓");
            System.out.println("brokerUrl: " + config.getBrokerUrl());
        } catch (Exception e) {
            System.out.println("✗✗✗ MqttConfig Bean 不存在！ ✗✗✗");
            System.out.println("错误: " + e.getMessage());
        }
        System.out.println("===========================================");
        System.out.println("✓ Spring Boot 应用已启动！");
        System.out.println("===========================================\n");
    }
}
