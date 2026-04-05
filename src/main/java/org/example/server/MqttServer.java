//package org.example.server;
//
//import io.moquette.broker.Server;
//import io.moquette.broker.config.IConfig;
//import io.moquette.broker.config.MemoryConfig;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import jakarta.annotation.PreDestroy;
//import java.util.Properties;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//// 使用外部 EMQX Broker，注释掉本地 Moquette 服务器
//@Component
//@Order(1)
//public class MqttServer implements CommandLineRunner {
//
//    private Server server;
//    private int port = 1883;
//
//    public static final AtomicBoolean serverReady = new AtomicBoolean(false);
//
//    @Override
//    public void run(String... args) throws Exception {
//        try {
//            System.out.println("╔════════════════════════════════════╗");
//            System.out.println("║   正在启动 MQTT 服务器...          ║");
//            System.out.println("╚════════════════════════════════════╝\n");
//
//            Server server = new Server();
//
//            Properties props = new Properties();
//            props.setProperty(IConfig.PORT_PROPERTY_NAME, String.valueOf(port));
//            props.setProperty(IConfig.HOST_PROPERTY_NAME, "0.0.0.0");
//            props.setProperty(IConfig.ALLOW_ANONYMOUS_PROPERTY_NAME, "true");
//
//            IConfig config = new MemoryConfig(props);
//
//            server.startServer(config);
//
//            this.server = server;
//
//            System.out.println("===========================================");
//            System.out.println("✓ MQTT 服务器已启动！");
//            System.out.println("✓ 监听端口：" + port);
//            System.out.println("✓ 服务器地址：tcp://localhost:" + port);
//            System.out.println("===========================================\n");
//
//            serverReady.set(true);
//            System.out.println("🚀 服务器已就绪，可以连接了！\n");
//
//        } catch (Exception e) {
//            System.err.println("❌ MQTT 服务器启动失败：" + e.getMessage());
//            e.printStackTrace();
//            serverReady.set(false);
//            throw e;
//        }
//    }
//
//    @PreDestroy
//    public void stop() {
//        if (server != null) {
//            server.stopServer();
//            System.out.println("MQTT 服务器已停止");
//        }
//    }
//}
