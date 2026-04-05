package org.example.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class ParallelScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ParallelScheduledTasks.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    @Qualifier("taskScheduler")
    private ThreadPoolTaskScheduler taskScheduler;

    /**
     * 方式 1：直接在主线程中并行提交所有任务
     */
    @Scheduled(cron = "0 0 10 * * ?")
    public void executeAllTasks() {
        logger.info("开始执行 10 个定时任务，时间：{}", LocalDateTime.now().format(formatter));

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // 并行提交所有任务
        futures.add(CompletableFuture.runAsync(this::task1, taskScheduler));
        futures.add(CompletableFuture.runAsync(this::task2, taskScheduler));
        futures.add(CompletableFuture.runAsync(this::task3, taskScheduler));
//        futures.add(CompletableFuture.runAsync(this::task4, taskScheduler));
//        futures.add(CompletableFuture.runAsync(this::task5, taskScheduler));
//        futures.add(CompletableFuture.runAsync(this::task6, taskScheduler));
//        futures.add(CompletableFuture.runAsync(this::task7, taskScheduler));
//        futures.add(CompletableFuture.runAsync(this::task8, taskScheduler));
//        futures.add(CompletableFuture.runAsync(this::task9, taskScheduler));
//        futures.add(CompletableFuture.runAsync(this::task10, taskScheduler));

        // 等待所有任务完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenRun(() -> logger.info("所有任务执行完成，时间：{}", LocalDateTime.now().format(formatter)))
                .join();
    }

    private void task1() {
        try {
            logger.info("任务 1 开始执行 - 线程：{}", Thread.currentThread().getName());
            // 任务逻辑
            Thread.sleep(1000); // 模拟耗时操作
            logger.info("任务 1 执行完成");
        } catch (Exception e) {
            logger.error("任务 1 执行失败", e);
        }
    }

    private void task2() {
        try {
            logger.info("任务 2 开始执行 - 线程：{}", Thread.currentThread().getName());
            Thread.sleep(1500);
            logger.info("任务 2 执行完成");
        } catch (Exception e) {
            logger.error("任务 2 执行失败", e);
        }
    }

    private void task3() {
        try {
            logger.info("任务 3 开始执行 - 线程：{}", Thread.currentThread().getName());
            Thread.sleep(800);
            logger.info("任务 3 执行完成");
        } catch (Exception e) {
            logger.error("任务 3 执行失败", e);
        }
    }
}
