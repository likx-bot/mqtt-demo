package org.example.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDBConfig {

    @Value("${influx.url}")
    private String url;

    @Value("${influx.token}")
    private String token;

    @Value("${influx.org}")
    private String organization;  // 重命名为 organization 避免与可能的关键字冲突

    @Value("${influx.bucket}")
    private String bucket;

    /**
     * InfluxDB 2.x 客户端（用于写入操作）
     */
    @Bean
    public InfluxDBClient influxDBClient() {
        return InfluxDBClientFactory.create(url, token.toCharArray(), organization, bucket);
    }

//    /**
//     * InfluxDB 3.x 客户端（用于 SQL 查询）
//     */
//    @Bean
//    public com.influxdb.v3.client.InfluxDBClient influxDBClient3x() {
//        return com.influxdb.v3.client.InfluxDBClient.getInstance(
//                url,
//                token.toCharArray(),
//                bucket
//        );
//    }
}