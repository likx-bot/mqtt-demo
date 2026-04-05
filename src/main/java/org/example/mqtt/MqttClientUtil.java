package org.example.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.function.BiConsumer;

/**
 * MQTT 客户端工具类
 * 支持连接、发布消息和订阅主题
 */
public class MqttClientUtil {

    private MqttClient client;
    private String brokerUrl;
    private String clientId;
    private String username;
    private String password;

    /**
     * 构造函数
     * @param brokerUrl MQTT 服务器地址，例如：tcp://localhost:1884
     * @param clientId 客户端 ID，必须唯一
     */
    public MqttClientUtil(String brokerUrl, String clientId) {
        this(brokerUrl, clientId, null, null);
    }

    // 添加回调变量
    private BiConsumer<String, String> messageCallback;

    /**
     * 设置消息回调函数
     */
    public void setMessageCallback(BiConsumer<String, String> callback) {
        this.messageCallback = callback;
    }

    /**
     * 构造函数（带认证）
     * @param brokerUrl MQTT 服务器地址
     * @param clientId 客户端 ID
     * @param username 用户名
     * @param password 密码
     */
    public MqttClientUtil(String brokerUrl, String clientId, String username, String password) {
        this.brokerUrl = brokerUrl;
        this.clientId = clientId;
        this.username = username;
        this.password = password;
    }
    
    /**
     * 连接到 MQTT 服务器
     */
    public void connect() throws MqttException {
        // 使用内存持久化
        MemoryPersistence persistence = new MemoryPersistence();
        
        // 创建客户端
        client = new MqttClient(brokerUrl, clientId, persistence);
        
        // 配置连接选项
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true); // 清除会话
        connOpts.setConnectionTimeout(10); // 连接超时 10 秒
        connOpts.setKeepAliveInterval(60); // 保持活动间隔 60 秒
        connOpts.setAutomaticReconnect(true); // 自动重连

        // 设置用户名密码
        if (username != null && !username.isEmpty()) {
            connOpts.setUserName(username);
            connOpts.setPassword(password != null ? password.toCharArray() : new char[0]);
            System.out.println("使用认证连接，用户名：" + username);
        }
        
        System.out.println("正在连接到 MQTT 服务器：" + brokerUrl);
        client.connect(connOpts);
        System.out.println("连接成功！客户端 ID: " + clientId);
    }
    
    /**
     * 发布消息
     * @param topic 主题
     * @param message 消息内容
     * @param qos QoS 等级 (0, 1, 2)
     * @param retained 是否保留消息
     */
    public void publish(String topic, String message, int qos, boolean retained) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(retained);
        
        client.publish(topic, mqttMessage);
        System.out.println("消息已发布 - 主题：" + topic + ", 内容：" + message);
    }


    /**
     * 订阅主题
     * @param topic 主题
     * @param qos QoS 等级
     */
    public void subscribe(String topic, int qos, BiConsumer<String, String> callback) throws MqttException {
        this.messageCallback = callback;
        // 设置回调监听器
        client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                System.out.println("✓ 连接完成");
            }

            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("✗ 连接丢失：" + cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                String payload = new String(message.getPayload());
                if (messageCallback != null) {
                    messageCallback.accept(topic, payload);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("✓ 消息发送完成");
            }
        });
        
        // 订阅主题
        client.subscribe(topic, qos);
        System.out.println("✓ 已订阅主题：" + topic + " (QoS: " + qos + ")");
    }
    
    /**
     * 取消订阅
     * @param topic 主题
     */
    public void unsubscribe(String topic) throws MqttException {
        client.unsubscribe(topic);
        System.out.println("已取消订阅主题：" + topic);
    }
    
    /**
     * 断开连接
     */
    public void disconnect() {
        if (client != null && client.isConnected()) {
            try {
                client.disconnect();
                System.out.println("已断开连接");
            } catch (MqttException e) {
                System.err.println("断开连接失败：" + e.getMessage());
            }
        }
    }
    
    /**
     * 检查是否已连接
     */
    public boolean isConnected() {
        return client != null && client.isConnected();
    }
}
