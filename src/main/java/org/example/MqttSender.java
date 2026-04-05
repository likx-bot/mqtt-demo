package org.example;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttSender {

    public static void main(String[] args) {
        try {
            MqttClient client = new MqttClient("tcp://localhost:1884", "publisher-001");
            client.connect();
            System.out.println("发送端连接成功！");

            String message = "{\"temperature\":23.5}";
            client.publish("sensor/device_01", new MqttMessage(message.getBytes()));
            System.out.println("消息已发送: " + message);

            Thread.sleep(1000);
            client.disconnect();
            System.out.println("发送端断开");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}