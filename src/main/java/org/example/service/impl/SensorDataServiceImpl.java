package org.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import lombok.extern.slf4j.Slf4j;
import org.example.DO.SensorDataDO;
import org.example.mapper.SensorDataMapper;
import org.example.service.SensorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.*;

/**
 * 传感器数据 Service 实现类
 */
@Service
@Slf4j
public class SensorDataServiceImpl extends ServiceImpl<SensorDataMapper,SensorDataDO > implements SensorDataService {

    @Autowired
    private InfluxDBClient influxDBClient;

    @Value("${influx.bucket}")
    private String bucket;

    @Value("${influx.org}")
    private String organization;  // 重命名为 organization 避免与可能的关键字冲突

    // ========== 写入单条 ==========
    @Override
    public void writeData(SensorDataDO data) {
        Point point = Point.measurement("sensor")
                .addTag("device_id", "test_device")
                .addField("temperature", 23.5)
                .addField("humidity", 65.2)
                .time(Instant.now(), WritePrecision.MS);

        try (WriteApi writeApi = influxDBClient.makeWriteApi()) {
            writeApi.writePoint(bucket, organization, point);
        }
    }

    @Override
    public void writeData123(String message) {
        log.info("接收到的数据：{}", message);
        //{"device_id":"device_01","temperature":23.5,"humidity":65.2}
        JSONObject jsonObject = JSONObject.parseObject(message);
        Point point = Point.measurement("robot-data")
                .addTag("device_id", jsonObject.getString("device_id"))
                .addField("temperature", jsonObject.getDouble("temperature"))
                .addField("humidity", jsonObject.getDouble("humidity"))
                .time(Instant.now(), WritePrecision.MS);

        try (WriteApi writeApi = influxDBClient.makeWriteApi()) {
            writeApi.writePoint(bucket, organization, point);
        }
    }

    // ========== 写入多条 ==========
    @Override
    public void writeListData() {
        List<Point> points = new ArrayList<>();
        List<String> devices = List.of("device01", "device02", "device03");

        for (String device : devices) {
            Point point = Point.measurement("listDevice")
                    .addTag("device_id", device)
                    .addField("temperature", 23.5)
                    .addField("humidity", 65.2)
                    .time(Instant.now(), WritePrecision.MS);
            points.add(point);
        }

        try (WriteApi writeApi = influxDBClient.makeWriteApi()) {
            writeApi.writePoints(bucket, organization, points);
        }
    }

    // ========== 写入真实数据 ==========
    @Override
    public void writeRealData() {
        Point point = Point.measurement("sensor")
                .addTag("device_id", "test_device")
                .addField("temperature", 23.5)
                .addField("humidity", 65.2)
                .time(Instant.now(), WritePrecision.MS);

        try (WriteApi writeApi = influxDBClient.makeWriteApi()) {
            writeApi.writePoint(bucket, organization, point);
        }
    }

    // ========== 查询数据（Flux） ==========
    @Override
    public Map<String, Object> readData(String deviceId) {
        String flux = String.format("""
            from(bucket: "%s")
              |> range(start: -24h)
              |> filter(fn: (r) => r._measurement == "listDevice")
              |> filter(fn: (r) => r.device_id == "%s")
              |> pivot(rowKey: ["_time"], columnKey: ["_field"], valueColumn: "_value")
              |> limit(n: 1)
            """, bucket, deviceId);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(flux, organization);

        if (tables.isEmpty() || tables.get(0).getRecords().isEmpty()) {
            return null;
        }

        FluxRecord record = tables.get(0).getRecords().get(0);
        log.info("查询结果：{}", record);
        Map<String, Object> result = new HashMap<>();
        result.put("time", record.getTime());
        result.put("device_id", record.getValueByKey("device_id"));
        result.put("temperature", record.getValueByKey("temperature"));
        result.put("humidity", record.getValueByKey("humidity"));
        log.info("查询结果：{}", result);

        return result;
    }

    @Override
    public List<SensorDataDO> readListData(String deviceId) {
        return List.of();
    }

    @Override
    public void writeBatch(List<SensorDataDO> dataList) {

    }

    @Override
    public List<SensorDataDO> queryRecentData(String deviceId, int minutes) {
        return List.of();
    }

    @Override
    public SensorDataDO queryLatestData(String deviceId) {
        return null;
    }
}
